package cz.zweistein.gw2.api.dto.enums.items;

public enum BagModifier {
	
	NO_SELL_OR_SORT("no_sell_or_sort");
	
	private String techName;

	private BagModifier(String name) {
		this.techName = name;
	}

	public static BagModifier resolve(String name) {

		for (BagModifier state : BagModifier.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

	public String getTechName() {
		return techName;
	}

}
