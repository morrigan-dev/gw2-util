package de.morrigan.dev.gw2.business.logic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.utils.UserGroupRightCache;
import de.morrigan.dev.gw2.dao.interfaces.IClientDataDAO;
import de.morrigan.dev.gw2.dao.interfaces.ISessionDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO.ACTION;
import de.morrigan.dev.gw2.dto.common.Protocol;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.gw2.entity.ClientData;
import de.morrigan.dev.gw2.entity.Session;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.UserGroup;
import de.morrigan.dev.gw2.exception.NoResultException;
import de.morrigan.dev.gw2.factory.DBDAOFactory;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;
import de.morrigan.dev.utils.resources.LanguageManager;

@Stateless
@Local(IAuthenticationService.class)
@Remote(IRemoteAuthenticationService.class)
public class AuthenticationService implements IAuthenticationService {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  private static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  @Override
  public String echo(String msg) {
    return "Response: " + msg;
  }

  @Override
  public AuthenticateDTO authenticate(final AuthenticateDTO auth) throws ServiceException {
    Validate.notNull(auth, "Der Parameter (auth) darf nicht null sein!");
    LOG.debug("auth: {}", auth);

    try {
      if (auth.getAction() == ACTION.LOGIN) {
        final String userName = auth.getUserName();
        final String password = auth.getPassword();
        final String mac = auth.getMac();
        final String ip = auth.getIp();

        final Session sessionOfUser = authenticate(userName, password, mac, ip);

        // Baue Antwort für den Client
        final Protocol protocol = new Protocol();
        final AuthenticateDTO result = new AuthenticateDTO(protocol);
        result.setAction(ACTION.SEND_SESSION);
        result.setSessionKey(sessionOfUser.getSessionKey());

        if (LOG.isInfoEnabled()) {
          LOG.info("{} hat sich angemeldet.", sessionOfUser.getUser().getUserName());
        }
        return result;
      }

      // Hier wird nur auf Logins reagiert. Alle anderen Anfragen werden verweigert.
      else {
        throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED,
            LANGUAGES.getMessage("actionMustBeLogin"));
      }
    } catch (final PersistenceException e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public User checkRight(final AuthenticateDTO authDTO) throws ServiceException {
    LOG.debug("authDTO: {}", authDTO);

    if ((authDTO == null) || (authDTO.getSessionKey() == null) || authDTO.getSessionKey().isEmpty()) {
      throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED,
          LANGUAGES.getMessage("noAuthorisation"));
    }

    final ISessionDAO sessionDAO = DBDAOFactory.getInstance().getSessionDAO();
    final String sessionKey = authDTO.getSessionKey();
    Session session = null;
    try {
      session = sessionDAO.findSessionBySessionKey(sessionKey);
    } catch (final PersistenceException e) {
      throw new ServiceException(e);
    }

    // Wenn zu dem Sessionschlüssel keine Session existiert, dann muss der Benutzer sich neu einloggen und erhält
    // eine entsprechende Fehlermeldung.
    if (session == null) {
      throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED,
          LANGUAGES.getMessage("pleaseLogin"));
    }

    // Wenn die Session des Benutzers abgelaufen ist, dann muss er sich ebenfalls neu einloggen.
    if (!isSessionValid(session)) {
      throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED,
          LANGUAGES.getMessage("sessionExpiredOrInvalidUser"));
    }

    StackTraceElement[] stackStraceElements = Thread.currentThread().getStackTrace();
    RightCheck callerRightAnnotation = getCallingRightCheckAnnotation(stackStraceElements, "sd");

    if (callerRightAnnotation == null) {
      throw new ServiceException(AbstractException.SERVER_THROWS_EXCEPTION,
          LANGUAGES.getMessage("cantCheckRightsPleaseTryAgain"));
    } else {
      checkRight(session.getUser(), callerRightAnnotation.rightKeys());
    }
    return session.getUser();
  }

  @Override
  public void checkRight(final User user, final String... rightsToCheck) throws ServiceException {
    Validate.notNull(user, "Der Parameter (user) darf nicht null sein!");
    Validate.notNull(rightsToCheck, "Der Parameter (rightsToCheck) darf nicht null sein!");
    LOG.debug("user: {}, rightsToCheck: {}", user, rightsToCheck);

    final boolean hasAllRights = hasRight(user, rightsToCheck);

    // Falls der Benutzer nicht alle angegebenen Rechte besitzt, wird der Zugriff verweigert.
    if (!hasAllRights) {
      throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED,
          LANGUAGES.getMessage("noAuthorisation"));
    }
  }

  @Override
  public Vector<String> getRightsOfUser(final AuthenticateDTO authDTO) throws ServiceException {
    LOG.debug("authDTO: {}", authDTO);

    final ISessionDAO sessionDAO = DBDAOFactory.getInstance().getSessionDAO();
    final String sessionKey = authDTO.getSessionKey();
    Session session = null;
    try {
      session = sessionDAO.findSessionBySessionKey(sessionKey);
    } catch (final PersistenceException e) {
      throw new ServiceException(e);
    }

    Vector<String> result = null;
    if ((session != null) && isSessionValid(session)) {
      UserGroupRightCache usergroupRightCache = UserGroupRightCache.getInstance();
      LOG.info("usergroupRightCache: {}", usergroupRightCache);
      result = usergroupRightCache.getRight(session.getUser().getUserGroup());
      LOG.info("result: {}", result);
    }
    if (result == null) {
      result = new Vector<>();
    }

    return result;
  }

  @Override
  public List<UserGroup> getUserGroupsForCache() throws ServiceException {
    final IUserGroupDAO userGroupDAO = DBDAOFactory.getInstance().getUserGroupDAO();

    try {
      return userGroupDAO.getUserGroupsForCache(ActiveState.ACTIVE, ActiveState.NOT_ADMIN);
    } catch (final PersistenceException e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public BigDecimal getVersion() {
    return BigDecimal.valueOf(0.6);
  }

  @Override
  public boolean hasRight(final User user, final String... rightsToCheck) {
    Validate.notNull(user, "Der Parameter (user) darf nicht null sein!");
    Validate.notNull(rightsToCheck, "Der Parameter (rightsToCheck) darf nicht null sein!");
    LOG.debug("user: {}, rightsToCheck: {}", user, rightsToCheck);

    final UserGroupRightCache cache = UserGroupRightCache.getInstance();
    final UserGroup groupOfUser = user.getUserGroup();
    final Vector<String> rightKeys = cache.getRight(groupOfUser);

    boolean hasAllRights = true;
    if (rightKeys == null) {
      hasAllRights = false;
    } else {
      for (final String rightKey : rightsToCheck) {
        if (!rightKeys.contains(rightKey)) {
          hasAllRights = false;
          break;
        }
      }
    }

    return hasAllRights;
  }

  @Override
  public boolean isSessionValid(final Session session) {
    LOG.debug("session: {}", session);
    // TODO In Server Config auslagern (morrigan, 29.10.2013)
    final int HOURS_OF_SESSION = 24 * 7;

    boolean sessionInTime = false;
    boolean userOfSessionActiveOrAdmin = false;

    // Prüfe, ob die Session den Ablaufzeitpunkt überschreitet.
    final Calendar lastAuth = Calendar.getInstance();
    lastAuth.setTime(session.getTimeOfLastAuth());

    final Calendar dateOfExpiry = Calendar.getInstance();
    dateOfExpiry.setTime(session.getTimeOfLastAuth());
    dateOfExpiry.add(Calendar.HOUR_OF_DAY, HOURS_OF_SESSION);

    if (lastAuth.before(dateOfExpiry)) {
      sessionInTime = true;
    }

    // Prüfe, ob der Benutzer der Session noch ACTIVE oder NOT_ADMIN ist
    final User userOfSession = session.getUser();
    final ActiveState activeState = userOfSession.getActiveState();
    if ((activeState == ActiveState.ACTIVE) || (activeState == ActiveState.NOT_ADMIN)) {
      userOfSessionActiveOrAdmin = true;
    }

    return sessionInTime && userOfSessionActiveOrAdmin;
  }

  @Override
  public void logout(final AuthenticateDTO auth) throws ServiceException {
    Validate.notNull(auth, "Der Parameter (auth) darf nicht null sein!");
    LOG.debug("auth: {}", auth);

    DBDAOFactory dbDaoFactory = DBDAOFactory.getInstance();
    final ISessionDAO sessionDAO = dbDaoFactory.getSessionDAO();

    try {
      Session activeSession = sessionDAO.findSessionBySessionKey(auth.getSessionKey());
      if (activeSession != null) {
        if (LOG.isInfoEnabled()) {
          LOG.info("{} meldet sich ab.", activeSession.getUser().getUserName());
        }
        sessionDAO.delete(activeSession);
      }
    } catch (PersistenceException e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public void registerNewUser(AuthenticateDTO authDTO, String username, String password) throws ServiceException {
    Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
    LOG.debug("username: {}, authDTO: {}", username, authDTO);

    final DBDAOFactory dbDaoFactory = DBDAOFactory.getInstance();
    final IUserDAO userDAO = dbDaoFactory.getUserDAO();
    final IUserGroupDAO userGroupDAO = dbDaoFactory.getUserGroupDAO();

    // Authentifiziere Benutzerdaten
    try {
      boolean isAvailable = userDAO.getIsUsernameAvailable(username);
      if (!isAvailable) {
        throw new ServiceException(AbstractException.EXECUTION_STOPPED,
            LANGUAGES.getMessage("usernameAlreadyExist", username));
      }

      String salt = UUID.randomUUID().toString();
      String passwordHash = Hex.encodeHexString(DigestUtils.sha256(password + salt));
      Date date = new Date();
      UserGroup defaultUserGroup = userGroupDAO.findById(UserGroup.DEFAULT_USER_GROUP_ID);

      User newUser = new User();
      newUser.setActiveState(ActiveState.INACTIVE);
      newUser.setUserName(username);
      newUser.setSaltHash(salt);
      newUser.setPassword(passwordHash);
      newUser.setCreateDate(date);
      newUser.setUpdateDate(date);
      newUser.setUserGroup(defaultUserGroup);

      userDAO.save(newUser);

    } catch (PersistenceException e) {
      throw new ServiceException(AbstractException.DATABASE_UNEXPECTED_EXCEPTION, "", e);
    }
  }

  private Session authenticate(final String userName, final String password, final String mac, String ip)
      throws PersistenceException, ServiceException {
    LOG.debug("userName: {}, mac: {}, ip: {}", userName, mac, ip);

    final DBDAOFactory dbDaoFactory = DBDAOFactory.getInstance();
    final IUserDAO userDAO = dbDaoFactory.getUserDAO();
    final ISessionDAO sessionDAO = dbDaoFactory.getSessionDAO();
    IClientDataDAO clientDataDAO = dbDaoFactory.getClientDataDAO();

    User authUser = null;
    // Authentifiziere Benutzerdaten
    try {
      authUser = userDAO.getUserByUsername(userName);
      String saltHash = authUser.getSaltHash();
      String passwordHash = Hex.encodeHexString(DigestUtils.sha256(password + saltHash));
      authUser = userDAO.authenticate(userName, passwordHash);
    } catch (final NoResultException e) {
      LOG.error(e.getMessage(), e);
      throw new ServiceException(AbstractException.SERVER_ACCESS_DENIED, LANGUAGES.getMessage("badLogin"));
    }

    // Aktualisiere ClientData
    Set<ClientData> clientDataSet = authUser.getClientDataSet();
    boolean clientDataFound = false;
    for (ClientData clientData : clientDataSet) {
      boolean macFound = clientData.getMac().equals(mac);
      boolean ipFound = clientData.getIp().equals(ip);
      if (macFound && ipFound) {
        clientDataFound = true;
        break;
      }
    }
    if (!clientDataFound) {
      Date currentdate = new Date();
      ClientData data = new ClientData(ActiveState.ACTIVE, currentdate, authUser, currentdate, authUser, mac, ip,
          authUser);
      clientDataDAO.save(data);
    }

    // Prüfe, ob es eine Session gibt
    Session sessionOfUser;
    sessionOfUser = sessionDAO.findSessionByUser(authUser);

    final Date currentDate = new Date();
    // Falls es noch keine Session gibt, dann erstelle eine neue Session
    if (sessionOfUser == null) {
      final UUID newSessionKey = UUID.randomUUID();
      sessionOfUser = new Session(newSessionKey.toString(), currentDate, currentDate, authUser);
    }
    // Ansonsten wird die vorhandene Session aktualisiert
    else {
      sessionOfUser.setTimeOfLastAccess(currentDate);
      sessionOfUser.setTimeOfLastAuth(currentDate);
    }
    sessionOfUser = sessionDAO.save(sessionOfUser);

    assert sessionOfUser != null : "sessionOfUser darf nicht null sein!";
    return sessionOfUser;
  }

  private RightCheck getCallingRightCheckAnnotation(StackTraceElement[] stes, String packageName) {
    try {
      for (int i = 0; i < stes.length; i++) {
        StackTraceElement ste = stes[i];
        final String className = ste.getClassName();
        final String methodName = ste.getMethodName();
        if (className.startsWith(packageName)) {
          Class<?> kls = Class.forName(className);
          while (kls != null) {
            for (final Method candidate : kls.getDeclaredMethods()) {
              if (candidate.getName().equals(methodName)) {
                for (Annotation anno : candidate.getDeclaredAnnotations()) {
                  if (anno.annotationType().equals(RightCheck.class)) {
                    return (RightCheck) anno;
                  }
                }
              }
            }
            kls = kls.getSuperclass();
          }
        }
      }
    } catch (ClassNotFoundException e) {
      LOG.error(e.getMessage(), e);
    }
    return null;
  }
}
