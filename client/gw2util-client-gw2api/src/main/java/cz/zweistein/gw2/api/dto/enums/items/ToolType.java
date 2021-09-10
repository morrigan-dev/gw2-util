package cz.zweistein.gw2.api.dto.enums.items;

public enum ToolType {
	
	SALVAGE("Salvage");
	
	private String techName;

	private ToolType(String name) {
		this.techName = name;
	}

	public static ToolType resolve(String name) {

		for (ToolType state : ToolType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
