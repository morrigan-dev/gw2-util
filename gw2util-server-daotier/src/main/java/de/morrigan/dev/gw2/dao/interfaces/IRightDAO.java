package de.morrigan.dev.gw2.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.RightDAO;
import de.morrigan.dev.gw2.entity.Right;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link RightDAO}. Hier werden nur die Methoden bereitgestellt,
 * die etwas mit der Entity {@link Right} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IRightDAO extends IGenericDAO<Right, Long> {

	/**
	 * Lädt eine Liste mit allen Rechten, die den angegebenen AktivStatus entsprechen.
	 * 
	 * @param activeStates eine Reihe von AktivStatus, die bei der Abfrage berücksichtigt werden sollen. (not null)
	 * @return eine Liste mit Rechten. (not null)
	 * @throws PersistenceException falls bei der Abfrage ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public List<Right> getRightByActiveState(ActiveState... activeStates) throws PersistenceException;
}
