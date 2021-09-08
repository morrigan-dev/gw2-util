package cz.zweistein.gw2.api.dto.enums;

public enum LocationType {
	
	SPHERE("sphere"),
	CYLINDER("cylinder"),
	POLY("poly");
	
	private String techName;

	private LocationType(String name) {
		this.techName = name;
	}

	public static LocationType resolve(String name) {

		for (LocationType state : LocationType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
