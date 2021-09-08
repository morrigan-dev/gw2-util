package cz.zweistein.gw2.api.dto.enums;

public enum WvWSide {

	RED("Red"), GREEN("Green"), BLUE("Blue");

	private String techName;

	private WvWSide(String name) {
		this.techName = name;
	}

	public static WvWSide resolve(String name) {

		for (WvWSide state : WvWSide.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
