package cz.zweistein.gw2.api.dto.enums.items;

public enum DamageType {
	
	PHYSICAL("Physical"),
	ICE("Ice"),
	FIRE("Fire"),
	LIGHTNING("Lightning");
	
	private String techName;

	private DamageType(String name) {
		this.techName = name;
	}

	public static DamageType resolve(String name) {

		for (DamageType state : DamageType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
