package de.morrigan.dev.gw2.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.morrigan.dev.gw2.dao.WaypointDAO;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.entity.Waypoint;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Dieses Interface beschreibt die Schnittstelle für das {@link WaypointDAO}. Hier werden nur die Methoden
 * bereitgestellt, die etwas mit der Entity {@link Waypoint} zu tun haben.
 * 
 * @author morrigan
 */
@Local
public interface IWaypointDAO extends IGenericDAO<Waypoint, Long> {

	/**
	 * Ermittelt die Anzahl an Wegpunkten, die aktiv sind, zu dem angegebenen Typ passen und sich innerhalb des
	 * angegebenen Bereiches befinden.
	 * 
	 * @param wpSubType eine Unterart eines Wegpunktes. (not null)
	 * @param fromLatitude Breitengrad ab dem nach Wegpunkten gesucht wird
	 * @param fromLongitude Längengrad ab dem nach Wegpunkten gesucht wird
	 * @param toLatitude Breitengrad bis zu dem nach Wegpunkten gesucht wird
	 * @param toLongitude Längengrad bis zu dem nach Wegpunkten gesucht wird
	 * @return eine Anzahl an Wegpunkten.
	 * @throws PersistenceException falls bei der Abfrage ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	int getAmountOf(WPSubType wpSubType, double fromLatitude, double fromLongitude, double toLatitude,
			double toLongitude) throws PersistenceException;

	/**
	 * Lädt eine Liste mit Wegpunkten anhand der angegebenen Parameter.
	 * 
	 * @param fromLatitude Breitengrad ab dem nach Wegpunkten gesucht wird
	 * @param fromLongitude Längengrad ab dem nach Wegpunkten gesucht wird
	 * @param toLatitude Breitengrad bis zu dem nach Wegpunkten gesucht wird
	 * @param toLongitude Längengrad bis zu dem nach Wegpunkten gesucht wird
	 * @return eine Liste mit Wegpunkten. (not null)
	 * @throws PersistenceException falls bei der Abfrage ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	List<Waypoint> getWaypoints(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude)
			throws PersistenceException;

	/**
	 * Lädt eine Liste mit Wegpunkten anhand der angegebenen Parameter.
	 * 
	 * @param wpTypes eine Hauptart eines Wegpunktes. (not null)
	 * @param wpSubType eine Unterart eines Wegpunktes. (not null)
	 * @param active ein Array lit Aktivstatus, die berücksichtigt werden. (not null)
	 * @return eine Liste mit Wegpunkten. (not null)
	 * @throws PersistenceException falls bei der Abfrage ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	List<Waypoint> getWaypoints(WPType[] wpTypes, WPSubType wpSubType, ActiveState... active)
			throws PersistenceException;
}
