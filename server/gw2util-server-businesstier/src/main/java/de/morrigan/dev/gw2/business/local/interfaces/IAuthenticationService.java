package de.morrigan.dev.gw2.business.local.interfaces;

import java.util.List;

import de.morrigan.dev.gw2.dao.interfaces.IUserGroupDAO;
import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.remote.interfaces.IRemoteAuthenticationService;
import de.morrigan.dev.gw2.entity.Session;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.UserGroup;
import de.morrigan.dev.gw2.utils.annotations.RightCheck;

public interface IAuthenticationService extends IRemoteAuthenticationService {
	
	/**
	 * Ermittelt anhand des Sessionschlüssels eine Session, sofern eine vorhanden ist. Ist keine vorhanden oder ist die
	 * Session ungültig, so wird eine entsprechende Exception geworfen und der Zugriff verweigert.
	 * <p>
	 * Ist die Session gültig und über die Session ein Benutzer ermittelt worden, so wird die weitere Prüfung an die
	 * Methode {@link IAuthenticationService#checkRight(User, String...)} delegiert. Die Rechte, die geprüft werden
	 * sollen, werden per Reflection über die Annotation {@link RightCheck} ermittelt.
	 * 
	 * @param authDTO ein DTO mit Session Informationen. (nullable)
	 * @return ein Benutzer, der Rechte zur Ausführung besitzt. (not null)
	 * @throws ServiceException falls es bei der Überprüfung der Session ein Fehler auftritt.
	 */
	User checkRight(AuthenticateDTO authDTO) throws ServiceException;

	/**
	 * Prüft, ob der übergebene Benutzer die angegebenen Rechte alle besitzt. Ist dies nicht der Fall wird eine
	 * entsprechende Exception geworfen und der Zugriff verweigert.
	 * 
	 * @param user Der zu prüfende Benutzer. (not null)
	 * @param rightsToCheck Ein Array mit Rechten, die der Benutzer haben muss. (not null)
	 * @throws ServiceException falls es bei der Überprüfung der Rechte ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	void checkRight(User user, String... rightsToCheck) throws ServiceException;

	/**
	 * Delegiert den Aufruf weiter an {@link IUserGroupDAO#getUserGroupByActiveState(de.aje.enums.ActiveState...)}, mit
	 * den Parametern ACTIVE, NOT_ADMIN.
	 * 
	 * @return eine Liste mit Benutzergruppen. (not null)
	 * @throws ServiceException falls beim Laden der Benutzergruppen ein Fehler auftritt.
	 */
	List<UserGroup> getUserGroupsForCache() throws ServiceException;

	/**
	 * Prüft, ob der übergebene Benutzer die angegebenen Rechte alle besitzt.
	 * 
	 * @param user Der zu prüfende Benutzer. (not null)
	 * @param rightsToCheck Die zu prüfenden Rechte. (not null)
	 * @return true, falls der Benutzer alle Rechte besitzt, ansonsten false.
	 * @throws IllegalArgumentException falls einer der parameter ungültig ist.
	 */
	boolean hasRight(final User user, final String... rightsToCheck);

	/**
	 * Prüft, ob die übergebene Session noch gültig ist. Eine Session läuft momentan nach 7 Tagen automatisch ab, sodass
	 * ein Benutzer sich neu anmelden muss, wenn seine Session abgelaufen ist.
	 * <p>
	 * Außerdem muss der Benutzer, der zu der Session gehört den AktivStatus ACTIVE oder NOT_ADMIN besitzen. Ansonsten
	 * ist die Session ebenfalls nicht mehr gültig.
	 * 
	 * @param session eine zu prüfende Session. (not null)
	 * @return true, falls die Session noch gültig ist. Ansonsten false.
	 */
	boolean isSessionValid(Session session);
}
