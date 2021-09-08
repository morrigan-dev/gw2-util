package cz.zweistein.gw2.api.dto.enums;

public enum CraftingFlag {
	
	AUTO_LEARNED("AutoLearned"),
	LEARNED_FROM_ITEM("LearnedFromItem");
	
	private String techName;

	private CraftingFlag(String name) {
		this.techName = name;
	}

	public static CraftingFlag resolve(String name) {

		for (CraftingFlag state : CraftingFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
