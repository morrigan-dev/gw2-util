package de.morrigan.dev.gw2.business.logic.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.builder.interfaces.IUserBuilder;
import de.morrigan.dev.gw2.business.factories.BuilderFactory;
import de.morrigan.dev.gw2.business.factories.ServiceFactory;
import de.morrigan.dev.gw2.business.local.interfaces.IAuthenticationService;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.business.local.interfaces.IUserAdminService;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.admin.UserDetailDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteUserAdminService;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.factory.DBDAOFactory;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.gw2.utils.annotations.RightCheck.Type;

@Stateless
@Local(IUserAdminService.class)
@Remote(IRemoteUserAdminService.class)
public class UserAdminService implements IUserAdminService {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserAdminService.class);

	@EJB
	private IJobService jobService;

	@RightCheck(rightKeys = { "administrate" }, type = Type.METHOD)
	public UserDetailDTO getUserDetails(AuthenticateDTO authDTO, long userId) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}, userId: {}", authDTO, userId);

		try {
			IUserDAO userDAO = DBDAOFactory.getInstance().getUserDAO();
			IUserBuilder userBuilder = BuilderFactory.getInstance().getUserBuilder();
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			UserDetailDTO result = null;
			User user = userDAO.findById(userId);
			if (user != null) {
				result = userBuilder.buildUserDetailDTO(user);
			}

			if (LOG.isInfoEnabled()) {
				LOG.info("{} Benutzer Details von {} abgerufen.", result, executingUser.getUserName());
			}
			return result;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@RightCheck(rightKeys = { "administrate" }, type = Type.METHOD)
	public List<UserDTO> getUserForAdministration(AuthenticateDTO authDTO) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}", authDTO);

		try {
			IUserDAO userDAO = DBDAOFactory.getInstance().getUserDAO();
			IUserBuilder userBuilder = BuilderFactory.getInstance().getUserBuilder();
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			User executingUser = authService.checkRight(authDTO);

			List<UserDTO> resultList = new ArrayList<>();
			List<User> userList = userDAO.findAll();
			for (User user : userList) {
				resultList.add(userBuilder.buildUserDTO(user));
			}

			if (LOG.isInfoEnabled()) {
				LOG.info("{} Benutzer von {} abgerufen.", resultList.size(), executingUser.getUserName());
			}
			return resultList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@RightCheck(rightKeys = { "administrate" }, type = Type.METHOD)
	public void updateItems(AuthenticateDTO authDTO) throws ServiceException {
		Validate.notNull(authDTO, "Der Parameter (authDTO) darf nicht null sein!");
		LOG.debug("authDTO: {}", authDTO);

		try {
			IAuthenticationService authService = ServiceFactory.getInstance().getAuthenticationService();
			authService.checkRight(authDTO);

			this.jobService.updateItems();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
