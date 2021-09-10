package cz.zweistein.gw2.api.dto.enums.items;

public enum UnlockType {
	
	CRAFTING_RECIPE("CraftingRecipe"),
	DYE("Dye");
	
	private String techName;

	private UnlockType(String name) {
		this.techName = name;
	}

	public static UnlockType resolve(String name) {

		for (UnlockType state : UnlockType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
