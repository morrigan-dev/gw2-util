package cz.zweistein.gw2.api.dto.enums.items;

public enum ConsumableType {
	
	UNLOCK("Unlock"),
	GENERIC("Generic"),
	FOOD("Food"),
	UTILITY("Utility"),
	TRANSMUTATION("Transmutation"),
	APPEARANCE_CHANGE("AppearanceChange"),
	CONTRACT_NPC("ContractNpc"),
	HALLOWEEN("Halloween"),
	IMMEDIATE("Immediate"),
	BOOZE("Booze");
	
	private String techName;

	private ConsumableType(String name) {
		this.techName = name;
	}

	public static ConsumableType resolve(String name) {

		for (ConsumableType state : ConsumableType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}


}
