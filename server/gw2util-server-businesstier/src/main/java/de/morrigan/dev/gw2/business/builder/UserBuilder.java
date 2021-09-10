package de.morrigan.dev.gw2.business.builder;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.builder.interfaces.IUserBuilder;
import de.morrigan.dev.gw2.dto.admin.ClientDataDTO;
import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.admin.UserDetailDTO;
import de.morrigan.dev.gw2.dto.admin.UserGroupDTO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

public class UserBuilder implements IUserBuilder {
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(UserBuilder.class);

	public UserDetailDTO buildUserDetailDTO(User user) {
		Validate.notNull(user, "Der Parameter (user) darf nicht null sein!");
		LOG.debug("user: {}", user);

		long id = user.getId();
		ActiveState activeState = user.getActiveState();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String username = user.getUserName();
		Date createDate = user.getCreateDate();
		Date updateDate = user.getUpdateDate();
		UserGroupDTO userGroup = null; // wird von der aufrufenden Methode gesetzt!
		List<ClientDataDTO> clientData = null; // wird von der aufrufenden Methode gesetzt!
		UserDetailDTO userDetailDTO = new UserDetailDTO(id, activeState, firstName, lastName, username, createDate,
				updateDate, userGroup, clientData);
		LOG.debug("userDetailDTO: {}", userDetailDTO);
		assert userDetailDTO != null : "userDetailDTO darf nicht null sein!";
		return userDetailDTO;
	}

	@Override
	public UserDTO buildUserDTO(User user) {
		Validate.notNull(user, "Der Parameter (user) darf nicht null sein!");
		LOG.debug("user: {}", user);

		long id = user.getId();
		ActiveState activeState = user.getActiveState();
		String username = user.getUserName();
		UserDTO userDTO = new UserDTO(id, activeState, username);
		LOG.debug("userDTO: {}", userDTO);
		assert userDTO != null : "userDTO darf nicht null sein!";
		return userDTO;
	}

}
