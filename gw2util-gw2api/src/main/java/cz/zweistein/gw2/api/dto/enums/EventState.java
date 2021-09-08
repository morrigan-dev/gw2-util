package cz.zweistein.gw2.api.dto.enums;

public enum EventState {

	WARMUP("Warmup"),
	SUCCESS("Success"),
	FAIL("Fail"),
	INVALID("Invalid"),
	ACTIVE("Active"),
	PREPARATION("Preparation"),
	INACTIVE("Inactive");

	private String techName;

	private EventState(String name) {
		this.techName = name;
	}

	public static EventState resolve(String name) {

		for (EventState state : EventState.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
