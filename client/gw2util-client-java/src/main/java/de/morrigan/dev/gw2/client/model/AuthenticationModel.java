package de.morrigan.dev.gw2.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.PreferencesModel;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO.ACTION;
import de.morrigan.dev.gw2.dto.common.Protocol;
import de.morrigan.dev.gw2.dto.common.Protocol.SEVERITY;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.swing.factories.MessageDialogFactory;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;
import de.morrigan.dev.utils.resources.LanguageManager;

public class AuthenticationModel extends AbstractModel {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationModel.class);

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  private static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  public static final long SESSION_KEY_CHANGED = BitUtil.setLongBit(0);
  public static final long USERNAME_KEY_CHANGED = BitUtil.setLongBit(1);

  private static final AuthenticationModel INSTANCE = new AuthenticationModel();

  public static AuthenticationModel getInstance() {
    return INSTANCE;
  }

  private String sessionKey = "";
  private String mac = "";
  private String ip = "";
  private String username = "";

  private IRemoteAuthenticationService authService;

  private RightsModel rightsModel = RightsModel.getInstance();
  private PreferencesModel prefModel = PreferencesModel.getInstance();

  private AuthenticationModel() {
    super();

    try {
      this.mac = searchForMac();
      this.ip = getExternalIp();

      //      this.authService = JNDIServiceFactory.getInstance().getRemoteAuthenticationService();
    } catch (SocketException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  public String searchForMac() throws SocketException {
    String firstInterface = null;
    Map<String, String> addressByNetwork = new HashMap<>();
    Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface network = networkInterfaces.nextElement();

      byte[] bmac = network.getHardwareAddress();
      if (bmac != null) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bmac.length; i++) {
          sb.append(String.format("%02X%s", bmac[i], (i < bmac.length - 1) ? "-" : ""));
        }

        if (!sb.toString().isEmpty()) {
          addressByNetwork.put(network.getName(), sb.toString());
        }

        if (!sb.toString().isEmpty() && firstInterface == null) {
          firstInterface = network.getName();
        }
      }
    }

    if (firstInterface != null) {
      return addressByNetwork.get(firstInterface);
    }

    return null;
  }

  public void authenticate(String username, String password) throws ServiceException {
    LOG.debug("username: {}", username);

    AuthenticateDTO authDTO = getAuthDTO();
    authDTO.setAction(ACTION.LOGIN);
    authDTO.setPassword(password);
    authDTO.setUserName(username);
    AuthenticateDTO answer = this.authService.authenticate(authDTO);
    Protocol protocol = answer.getProtocol();
    if (protocol.getSeverity() == SEVERITY.INFO) {
      this.sessionKey = answer.getSessionKey();
      this.username = username;
      Vector<String> rights = this.authService.getRightsOfUser(getAuthDTO());
      this.rightsModel.setRights(rights);
    } else {
      String messageHeader = LANGUAGES.getLabel("error");
      String message = LANGUAGES.getError(Integer.toString(protocol.getErrorCode()));
      String messageDetail = protocol.getErrorMsg();
      MessageDialogFactory.showInformationDialog(Main.getInstance().getMainFrame(), null, messageHeader, message,
          messageDetail);
      this.sessionKey = "";
      this.username = "";
    }
    long syncFlag = SESSION_KEY_CHANGED;
    syncFlag |= USERNAME_KEY_CHANGED;
    if (!isChanging()) {
      syncViews(syncFlag);
    }
  }

  public void doLogout() throws ServiceException {
    try {
      this.authService.logout(getAuthDTO());
      this.rightsModel.resetRights();
      this.prefModel.setStayLoggedOn(false);
    } finally {
      long syncFlag = 0;
      this.sessionKey = "";
      syncFlag |= SESSION_KEY_CHANGED;
      this.username = "";
      syncFlag |= USERNAME_KEY_CHANGED;
      if (!isChanging()) {
        syncViews(syncFlag);
      }
    }
  }

  public AuthenticateDTO getAuthDTO() {
    AuthenticateDTO authDTO = new AuthenticateDTO(new Protocol());
    authDTO.setAction(ACTION.SEND_SESSION);
    authDTO.setIp(this.ip);
    authDTO.setMac(this.mac);
    authDTO.setSessionKey(this.sessionKey);
    authDTO.setUserName(this.username);
    return authDTO;
  }

  public String getIp() {
    return this.ip;
  }

  public String getMac() {
    return this.mac;
  }

  public String getSessionKey() {
    return this.sessionKey;
  }

  public String getUsername() {
    return this.username;
  }

  public boolean isAuthenticated() {
    return (this.sessionKey != null) && !this.sessionKey.isEmpty();
  }

  public void loadDataFromPreferences() throws ServiceException {
    long syncFlag = 0;
    this.sessionKey = this.prefModel.getSessionKey();
    syncFlag |= SESSION_KEY_CHANGED;

    this.username = this.prefModel.getUsername();
    syncFlag |= USERNAME_KEY_CHANGED;

    Vector<String> rights = this.authService.getRightsOfUser(getAuthDTO());
    this.rightsModel.setRights(rights);

    if (!isChanging()) {
      syncViews(syncFlag);
    }
  }

  private String getExternalIp() {
    BufferedReader in = null;
    try {
      URL whatismyip = new URL("http://checkip.amazonaws.com");
      in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
      return in.readLine();
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      return "";
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LOG.error(e.getMessage(), e);
        }
      }
    }
  }
}
