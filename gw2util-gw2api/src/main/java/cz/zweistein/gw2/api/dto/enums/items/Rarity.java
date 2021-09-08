package cz.zweistein.gw2.api.dto.enums.items;

public enum Rarity {
	
	BASIC("Basic"),
	RARE("Rare"),
	EXOTIC("Exotic"),
	MASTERWORK("Masterwork"),
	FINE("Fine"),
	ASCENDED("Ascended"),
	JUNK("Junk"),
	LEGENDARY("Legendary");
	
	private String techName;

	private Rarity(String name) {
		this.techName = name;
	}

	public static Rarity resolve(String name) {

		for (Rarity state : Rarity.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
