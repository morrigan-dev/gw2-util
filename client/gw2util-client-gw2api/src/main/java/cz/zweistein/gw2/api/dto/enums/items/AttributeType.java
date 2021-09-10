package cz.zweistein.gw2.api.dto.enums.items;

public enum AttributeType {
	
	POWER("Power"),
	PRECISION("Precision"),
	CONDITION_DAMAGE("ConditionDamage"),
	TOUGHNESS("Toughness"),
	VITALITY("Vitality"),
	CRITICAL_DAMAGE("CritDamage"),
	HEALING_POWER("Healing");

	private String techName;

	private AttributeType(String name) {
		this.techName = name;
	}

	public static AttributeType resolve(String name) {

		for (AttributeType state : AttributeType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}
	
}
