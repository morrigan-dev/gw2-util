package cz.zweistein.gw2.api.dto.enums.items;

public enum WeightClass {
	
	LIGHT("Light"),
	MEDIUM("Medium"),
	HEAVY("Heavy"),
	CLOTHING("Clothing");
	
	private String techName;

	private WeightClass(String name) {
		this.techName = name;
	}

	public static WeightClass resolve(String name) {

		for (WeightClass state : WeightClass.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
