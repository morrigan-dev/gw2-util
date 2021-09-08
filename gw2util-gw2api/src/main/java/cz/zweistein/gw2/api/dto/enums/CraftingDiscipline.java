package cz.zweistein.gw2.api.dto.enums;

public enum CraftingDiscipline {
	
	JEWELER("Jeweler"),
	CHEF("Chef"),
	TAILOR("Tailor"),
	LEATHERWORKER("Leatherworker"),
	ARMORSMITH("Armorsmith"),
	ARTIFICIER("Artificer"),
	HUNTSMAN("Huntsman"),
	WEAPONSMITH("Weaponsmith");
	
	private String techName;

	private CraftingDiscipline(String name) {
		this.techName = name;
	}

	public static CraftingDiscipline resolve(String name) {

		for (CraftingDiscipline state : CraftingDiscipline.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
