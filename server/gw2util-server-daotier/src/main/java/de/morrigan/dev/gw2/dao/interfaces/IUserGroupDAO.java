package de.morrigan.dev.gw2.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.UserGroupDAO;
import de.morrigan.dev.gw2.entity.UserGroup;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link UserGroupDAO}. Hier werden nur die Methoden
 * bereitgestellt, die etwas mit der Entity {@link UserGroup} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IUserGroupDAO extends IGenericDAO<UserGroup, Long> {

	/**
	 * Diese Methode lädt eine Liste mit allen Benutzergruppen, die den angegebenen AktivStatus entsprechen.
	 * 
	 * @param activeStates Eine Reihe von AktivStatus, die bei der Abfrage berücksichtigt werden sollen.
	 * @return eine Liste mit Benutzergruppen. (not null)
	 * @throws PersistenceException
	 */
	public List<UserGroup> getUserGroupByActiveState(ActiveState... activeStates) throws PersistenceException;

	/**
	 * Diese Methode lädt eine Liste mit allen Benutzergruppen, die den angegebenen AktivStatus entsprechen.
	 * <p>
	 * <b>Explizite Joins</b><br>
	 * <ul>
	 * <li>userList</li>
	 * <li>rightList</li>
	 * </ul>
	 * 
	 * @param activeStates Eine Reihe von AktivStatus, die bei der Abfrage berücksichtigt werden sollen.
	 * @return eine Liste mit Benutzergruppen. (not null)
	 * @throws PersistenceException
	 */
	public List<UserGroup> getUserGroupsForCache(ActiveState... activeStates) throws PersistenceException;
	
	UserGroup findByName(String groupname) throws PersistenceException;
}
