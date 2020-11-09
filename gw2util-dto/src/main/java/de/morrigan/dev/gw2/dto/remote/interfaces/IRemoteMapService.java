package de.morrigan.dev.gw2.dto.remote.interfaces;

import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.MapInfoDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.dto.map.WaypointDTO;
import de.morrigan.dev.gw2.dto.map.WaypointListWrapperDTO;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

public interface IRemoteMapService {

	/**
	 * Erzeugt einen neuen Wegpunkt.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param newWaypoint ein zu speichernder Wegpunkt. (not null)
	 * @return den gespeicherten Wegpunkt. (not null)
	 * @throws ServiceException falls während des Speichern des Wegpunktes ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	WaypointDTO createNewWaypoint(AuthenticateDTO authDTO, WaypointDTO newWaypoint) throws ServiceException;

	/**
	 * Setzt den mittels ID angegebenen Wegpunkt auf den Status {@link ActiveState#DELETED}.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param idOfWaypoint eine ID eines existierenden Wegpunktes.
	 * @throws ServiceException falls während des 'Löschens' ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	void deleteWaypoint(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException;

	/**
	 * Setzt alle Wegpunkte im angebegebenen Bereich auf den Status {@link ActiveState#DELETED}, sofern der Wegpunkt
	 * nicht als permanent markiert ist.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param mapName Name der Karte der zu dem angegebenen Bereich gehört.
	 * @param fromLatitude Breitengrad ab dem nach Wegpunkten gesucht wird
	 * @param fromLongitude Längengrad ab dem nach Wegpunkten gesucht wird
	 * @param toLatitude Breitengrad bis zu dem nach Wegpunkten gesucht wird
	 * @param toLongitude Längengrad bis zu dem nach Wegpunkten gesucht wird
	 * @throws ServiceException falls während des 'Löschens' ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	void deleteWaypoints(AuthenticateDTO authDTO, String mapName, double fromLatitude, double fromLongitude,
			double toLatitude, double toLongitude) throws ServiceException;

	/**
	 * Erzeugt einen Wrapper in dem mehrere Listen mit Wegpunkten enthalten sind, die auf dem Client angezeigt werden
	 * können.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @return einen Wrapper mit Wegpunkten für den Client. (not null)
	 * @throws ServiceException falls beim Laden der Wegpunkt ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	WaypointListWrapperDTO getAllAvailableWaypoints(AuthenticateDTO authDTO) throws ServiceException;

	/**
	 * Ermittelt verschiedene Informationen zu dem angegebenen Kartenbereich und verpackt diese in einem entsprechenden
	 * dTO, welches an den Client zurückgegeben wird.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param mapName Name der Karte der zu dem angegebenen Bereich gehört.
	 * @param fromLatitude Breitengrad ab dem nach Wegpunkten gesucht wird
	 * @param fromLongitude Längengrad ab dem nach Wegpunkten gesucht wird
	 * @param toLatitude Breitengrad bis zu dem nach Wegpunkten gesucht wird
	 * @param toLongitude Längengrad bis zu dem nach Wegpunkten gesucht wird
	 * @throws ServiceException falls während des 'Löschens' ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	MapInfoDTO getMapInfo(AuthenticateDTO authDTO, String mapName, double fromLatitude, double fromLongitude,
			double toLatitude, double toLongitude) throws ServiceException;

	/**
	 * Markiert den mittels ID angegebenen Wegpunkt als permanent.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param idOfWaypoint eine ID eines existierenden Wegpunktes.
	 * @throws ServiceException falls während des Speicherns ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	void markAsPermanent(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException;

	/**
	 * Markiert den mittels ID angegebenen Wegpunkt als reichhaltig.
	 * 
	 * @param authDTO enthält Informationen über den Client und seiner Session. (not null)
	 * @param idOfWaypoint eine ID eines existierenden Wegpunktes.
	 * @throws ServiceException falls während des Speicherns ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	void markAsRich(AuthenticateDTO authDTO, long idOfWaypoint) throws ServiceException;

}
