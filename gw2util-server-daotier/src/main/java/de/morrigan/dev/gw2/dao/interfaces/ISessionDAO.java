package de.morrigan.dev.gw2.dao.interfaces;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.SessionDAO;
import de.morrigan.dev.gw2.entity.Session;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link SessionDAO}. Hier werden nur die Methoden
 * bereitgestellt, die etwas mit der Entity {@link Session} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface ISessionDAO extends IGenericDAO<Session, Long> {

	/**
	 * Ermittelt eine Session anhand der übergebenen SessionId.
	 * 
	 * @param sessionKey ein Session Schl[ssel. (not null)
	 * @return eine Session, sofern eine vorhanden ist. Ansonstemn null.
	 * @throws PersistenceException falls bei der Abfrage ein Fehler autritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public Session findSessionBySessionKey(String sessionKey) throws PersistenceException;

	/**
	 * Ermittel eine Session anhand des übergebenen Benutzers, sofern eine vorhanden ist.
	 * 
	 * @param user ein Benutzer. (not null)
	 * @return eine Session. (nullable)
	 * @throws PersistenceException falls bei der Abfrage ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public Session findSessionByUser(User user) throws PersistenceException;

}
