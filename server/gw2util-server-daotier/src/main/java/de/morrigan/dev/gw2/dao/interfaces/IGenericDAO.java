package de.morrigan.dev.gw2.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface muss von jedem DAO implementiert werden, da gewisse Methoden für jedes DAO sinnvoll und somit
 * vorhanden sein sollen.
 * 
 * @author morrigan
 */
public interface IGenericDAO<T, U extends Serializable> {

	/** Löscht den übergebenen Datensatz physisch aus der Datenbank */
	public void delete(T entity) throws PersistenceException;

	/** Löscht den übergebenen (detached) Datensatz physisch aus der Datenbank */
	public void deleteDetached(U id) throws PersistenceException;

	/** @return alle Datensätze aus der Tabelle */
	public List<T> findAll() throws PersistenceException;

	/** @return den Datensatz, der die übergebene ID besitzt oder null, wenn kein Datensatz mit der ID gefunden wurde. */
	public T findById(U id) throws PersistenceException;

	/** Setzt den Status des Datensatzes auf gelöscht und aktualisiert in in der DB */
	public T markAsDeleted(T entity) throws PersistenceException;

	/** Aktualisiert den übergebenen Datensatz in der Datenbank */
	public T merge(T entity) throws PersistenceException;

	/**
	 * Speichert den übergebenen Datensatz in der Datenbank. Dabei wird ein persist durchgeführt, wenn die ID der Entity
	 * 0 ist und andernfalls ein merge. In beiden Fällen wird ein aktualisiertes Entity-Objekt zurückgegeben.
	 * <p>
	 * <b>Historisierung</b><br>
	 * Wenn die Entity das Interface IHistoricized implementiert, dann wird der zu aktualisierende Datensatz
	 * historisiert. Dabei wird wie folgt vorgegangen:<br>
	 * - Es wird immer ein neuer Datensatz mit neuer ID geschrieben<br>
	 * - Der aktive Datensatz hat nach dem Speichern die Eigenschaft: ID = MID<br>
	 * - Alle historisierten Datensätze, die zusammengehören haben die Eigenschaften: Alle MIDs sind gleich und die IDs
	 * sind kleiner als die ID des entsprechenden aktiven Datensatzes
	 * 
	 * @param entity der zu speichernde Datensatz.
	 * @return der gespeicherte und aktualisierte Datensatz.
	 * @throws PersistenceException falls beim Speichern ein Fehler auftrat.
	 */
	public T save(T entity) throws PersistenceException;

	/**
	 * Setzt die Audit-Informationen einer Entity. Muss vor jedem Speichern aufgerufen werden.
	 * 
	 * @param auditEntity eine Entity, dessen Audit-Informationen aktualisiert werden sollen. (nullable)
	 * @param executingUser ein Benutzer, der die Änderung durchgeführt hat. (not null)
	 * @param setCreateData true, falls die Erstellungsinformationen auch überschrieben werden sollen. Ansonsten werden
	 * nur die Aktualisierungsdaten gesetzt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public <S extends IAuditEntity> void setAuditInformation(final S auditEntity, User executingUser,
			boolean setCreateData);
}
