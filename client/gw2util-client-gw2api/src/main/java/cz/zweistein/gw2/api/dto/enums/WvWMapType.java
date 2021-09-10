package cz.zweistein.gw2.api.dto.enums;

public enum WvWMapType {

	BORDERLANDS_RED("RedHome"), BORDERLANDS_GREEN("GreenHome"), BORDERLANDS_BLUE("BlueHome"), ETERNAL_BATTLEGROUNDS("Center");

	private String techName;

	private WvWMapType(String name) {
		this.techName = name;
	}

	public static WvWMapType resolve(String name) {

		for (WvWMapType state : WvWMapType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
