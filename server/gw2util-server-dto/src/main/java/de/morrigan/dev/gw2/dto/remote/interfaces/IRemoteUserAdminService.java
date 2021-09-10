package de.morrigan.dev.gw2.dto.remote.interfaces;

import java.util.List;

import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;

public interface IRemoteUserAdminService {

	List<UserDTO> getUserForAdministration(AuthenticateDTO authDTO) throws ServiceException;

	void updateItems(AuthenticateDTO authDTO) throws ServiceException;
}
