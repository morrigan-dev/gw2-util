package de.morrigan.dev.gw2.business.builder.interfaces;

import de.morrigan.dev.gw2.dto.map.WaypointDTO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.Waypoint;

public interface IWaypointBuilder {

	/**
	 * Erzeugt ein {@link WaypointDTO} und befüllt dieses komplett mit den Werten aus der übergebenen Entity.
	 * 
	 * @param waypoint eine Entity mit Wegpunkt-Informationen. (not null)
	 * @return ein DTO mit Wegpunkt-Informationen. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public WaypointDTO buildDTO(Waypoint waypoint);

	/**
	 * Erzeugt eine neue {@link Waypoint} Entity und befüllt diese komplett mit den Werten aus dem übergebenen DTO.
	 * 
	 * @param newWaypoint ein DTO mit Wegpunkt-Informationen. (not null)
	 * @param executingUser ein ausführender Benutzer. (not null)
	 * @return eine neue Entity mit Wegpunkt-Informationen. (not null)
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	public Waypoint createNewEntity(WaypointDTO newWaypoint, User executingUser);
}
