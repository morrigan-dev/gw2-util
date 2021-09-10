package cz.zweistein.gw2.api.dto.enums;

public enum PoIType {
	
	POINT_OF_INTEREST("landmark"),
	WAYPOINT("waypoint"),
	VISTA("vista"),
	UNLOCK("unlock");
	
	private String techName;

	private PoIType(String name) {
		this.techName = name;
	}

	public static PoIType resolve(String name) {

		for (PoIType state : PoIType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
