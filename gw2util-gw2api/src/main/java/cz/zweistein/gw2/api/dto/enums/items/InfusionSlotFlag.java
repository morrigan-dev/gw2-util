package cz.zweistein.gw2.api.dto.enums.items;

public enum InfusionSlotFlag {
	
	DEFENSE("Defense"),
	OFFENSE("Offense"),
	UTILITY("Utility");
	
	private String techName;

	private InfusionSlotFlag(String name) {
		this.techName = name;
	}

	public static InfusionSlotFlag resolve(String name) {

		for (InfusionSlotFlag state : InfusionSlotFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
