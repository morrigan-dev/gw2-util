package de.morrigan.dev.gw2.client.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.JNDIServiceFactory;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.swing.models.AbstractModel;

public class RegisterModel extends AbstractModel {
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(RegisterModel.class);

	private IRemoteAuthenticationService authService;

	public RegisterModel() {
		super();

		try {
			this.authService = JNDIServiceFactory.getInstance().getRemoteAuthenticationService();
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public void register(String username, String password) throws ServiceException {
		LOG.debug("username: {}", username);

		AuthenticateDTO authDTO = AuthenticationModel.getInstance().getAuthDTO();
		this.authService.registerNewUser(authDTO, username, password);
	}
}
