package cz.zweistein.gw2.api.dto.enums.items;

public enum GatheringType {
	
	MINING("Mining"),
	LOGGING("Logging"),
	FORAGING("Foraging");
	
	private String techName;

	private GatheringType(String name) {
		this.techName = name;
	}

	public static GatheringType resolve(String name) {

		for (GatheringType state : GatheringType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
