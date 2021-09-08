package cz.zweistein.gw2.api.dto.enums;

public enum EventFlag {

	GROUP_EVENT("group_event"),
	MAP_WIDE("map_wide");
	
	private String techName;

	private EventFlag(String name) {
		this.techName = name;
	}

	public static EventFlag resolve(String name) {

		for (EventFlag state : EventFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

	
}
