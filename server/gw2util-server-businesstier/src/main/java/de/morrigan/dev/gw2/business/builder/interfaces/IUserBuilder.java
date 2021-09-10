package de.morrigan.dev.gw2.business.builder.interfaces;

import de.morrigan.dev.gw2.dto.admin.UserDTO;
import de.morrigan.dev.gw2.dto.admin.UserDetailDTO;
import de.morrigan.dev.gw2.entity.User;

public interface IUserBuilder {

	/**
	 * Erzeugt ein {@link UserDetailDTO} und befüllt dieses komplett mit den Werten aus der übergebenen Entity.
	 * 
	 * @param user eine Entity mit Benutzer-Informationen. (not null)
	 * @return ein DTO mit Benutzer-Informationen. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	UserDetailDTO buildUserDetailDTO(User user);

	/**
	 * Erzeugt ein {@link UserDTO} und befüllt dieses komplett mit den Werten aus der übergebenen Entity.
	 * 
	 * @param user eine Entity mit Benutzer-Informationen. (not null)
	 * @return ein DTO mit Benutzer-Informationen. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	UserDTO buildUserDTO(User user);
}
