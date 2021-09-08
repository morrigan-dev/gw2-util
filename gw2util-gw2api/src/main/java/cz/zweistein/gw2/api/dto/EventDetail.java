package cz.zweistein.gw2.api.dto;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.EventFlag;

public class EventDetail {
	
	private List<EventFlag> flags;
	
	private Long mapId;
	
	private Long level;
	
	private EventLocation location;
	
	private String name;

	public EventDetail(List<EventFlag> flags, Long mapId, Long level, EventLocation location, String name) {
		super();
		this.flags = flags;
		this.mapId = mapId;
		this.level = level;
		this.location = location;
		this.name = name;
	}

	public List<EventFlag> getFlags() {
		return this.flags;
	}

	public Long getMapId() {
		return this.mapId;
	}

	public Long getLevel() {
		return this.level;
	}

	public EventLocation getLocation() {
		return this.location;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "EventDetail [flags=" + this.flags + ", mapId=" + this.mapId + ", level=" + this.level + ", location=" + this.location + ", name=" + this.name
				+ "]";
	}

}
