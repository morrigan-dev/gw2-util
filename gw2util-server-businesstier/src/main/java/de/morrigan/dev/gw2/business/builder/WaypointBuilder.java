package de.morrigan.dev.gw2.business.builder;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.business.builder.interfaces.IWaypointBuilder;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.dto.map.WaypointDTO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.Waypoint;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

/**
 * Diese Klasse bietet verschiedene Methoden zur Umwandlung von {@link Waypoint} Entities in {@link WaypointDTO} und
 * umgekehrt.
 * 
 * @author morrigan
 */
public class WaypointBuilder implements IWaypointBuilder {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(WaypointBuilder.class);

	@Override
	public WaypointDTO buildDTO(Waypoint waypoint) {
		Validate.notNull(waypoint, "Der Parameter (waypoint) darf nicht null sein!");
		LOG.debug("waypoint: {}", waypoint);

		long id = waypoint.getId();
		Date updateDate = waypoint.getUpdateDate();
		String updateUsername = waypoint.getUpdateUser().getUserName();
		WPType wpType = waypoint.getWpType();
		WPSubType wpSubType = waypoint.getWpSubType();
		double longitude = waypoint.getLongitude();
		double latitude = waypoint.getLatitude();
		String informationKey = "";
		boolean rich = waypoint.isRich();
		boolean permanent = waypoint.isPermanent();
		WaypointDTO waypointDTO = new WaypointDTO(id, updateDate, updateUsername, wpType, wpSubType, longitude,
				latitude, informationKey, rich, permanent);
		LOG.debug("waypointDTO: {}", waypointDTO);
		assert waypointDTO != null : "waypointDTO darf nicht null sein!";
		return waypointDTO;
	}

	@Override
	public Waypoint createNewEntity(WaypointDTO waypointDTO, User executingUser) {
		Validate.notNull(waypointDTO, "Der Parameter (waypointDTO) darf nicht null sein!");
		Validate.notNull(executingUser, "Der Parameter (executingUser) darf nicht null sein!");
		LOG.debug("newWaypoint: {}, executingUser: {}", waypointDTO, executingUser);

		ActiveState activeState = ActiveState.ACTIVE;
		Date currentDate = new Date();
		WPType wpType = waypointDTO.getWpType();
		WPSubType wpSubType = waypointDTO.getWpSubType();
		double longitude = waypointDTO.getLongitude();
		double latitude = waypointDTO.getLatitude();
		String informationKey = "";
		Waypoint newWaypoint = new Waypoint(activeState, currentDate, executingUser, currentDate, executingUser,
				wpType, wpSubType, longitude, latitude, informationKey, false, false);
		LOG.debug("newWaypoint: {}", newWaypoint);
		assert newWaypoint != null : "newWaypoint darf nicht null sein!";
		return newWaypoint;
	}
}
