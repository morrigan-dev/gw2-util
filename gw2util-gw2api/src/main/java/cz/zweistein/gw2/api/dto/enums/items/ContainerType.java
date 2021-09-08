package cz.zweistein.gw2.api.dto.enums.items;

public enum ContainerType {
	
	DEFAULT("Default"),
	GIFT_BOX("GiftBox");
	
	private String techName;

	private ContainerType(String name) {
		this.techName = name;
	}

	public static ContainerType resolve(String name) {

		for (ContainerType state : ContainerType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
