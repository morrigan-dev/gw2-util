package de.morrigan.dev.gw2.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.UserDAO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link UserDAO}. Hier werden nur die Methoden bereitgestellt,
 * die etwas mit der Entity {@link User} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IUserDAO extends IGenericDAO<User, Long> {

	/**
	 * Diese Methode prüft, ob es einen Benutzer mit dem angebenen Benutzernamen und dem Passwort gibt und ob dieser
	 * sich einloggen darf.
	 * 
	 * @param userName Ein Benutzername. (not null)
	 * @param password ein Passwort. (not null)
	 * @return Einen Benutzer (not null)
	 * @throws PersistenceException
	 */
	public User authenticate(String userName, String password) throws PersistenceException;

	/**
	 * Prüft, ob der angegebene Benutzername noch verfügbar ist.
	 * 
	 * @param username ein Benutzername.
	 * @return true, falls der Benutzername noch frei ist. Ansonsten false.
	 * @throws PersistenceException
	 */
	public boolean getIsUsernameAvailable(String username) throws PersistenceException;

	/**
	 * Diese Methode lädt eine Liste mit allen Benutzern, die den angegebenen AktivStatus entsprechen.
	 * 
	 * @param activeStates Eine Reihe von AktivStatus, die bei der Abfrage berücksichtigt werden sollen.
	 * @return eine Liste mit Benutzern. (not null)
	 * @throws PersistenceException
	 * @author morrigan
	 */
	public List<User> getUserByActiveState(ActiveState... activeStates) throws PersistenceException;

	/**
	 * Diese Methode lädt einen Benutzer anhand eines Benutzernamens aus der Datenbank.
	 * 
	 * @param userName ein Benutzername.
	 * @return einen Benutzer, der den angegebenen Benutzernamen besitzt.
	 * @throws PersistenceException
	 */
	public User getUserByUsername(String userName) throws PersistenceException;
}
