package cz.zweistein.gw2.api.dto;

import cz.zweistein.gw2.api.dto.enums.EventState;

public class Event {

	private String eventId;

	private Long mapId;

	private Long worldId;

	private EventState eventState;

	public Event(String eventId, Long mapId, Long worldId, EventState eventState) {
		super();
		this.mapId = mapId;
		this.eventId = eventId;
		this.worldId = worldId;
		this.eventState = eventState;
	}

	public EventState getEventState() {
		return eventState;
	}

	public Long getWorldId() {
		return worldId;
	}

	public String getEventId() {
		return eventId;
	}

	public Long getMapId() {
		return mapId;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + this.eventId + ", mapId=" + this.mapId + ", worldId=" + this.worldId + ", eventState=" + this.eventState + "]";
	}

}
