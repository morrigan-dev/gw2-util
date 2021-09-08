package cz.zweistein.gw2.api.dto.enums.items;

public enum ItemClass {
	
	ARMOR("Armor"),
	CRAFTING_MATERIAL("CraftingMaterial"),
	WEAPON("Weapon"),
	BAG("Bag"),
	CONTAINER("Container"),
	TROPHY("Trophy"), //no bound information
	CONSUMABLE("Consumable"),
	TRINKET("Trinket"),
	GIZMO("Gizmo"),
	UPGRADE_COMPONENT("UpgradeComponent"),
	BACK("Back"),
	MINIPET("MiniPet"), //no bound information
	GATHERING("Gathering"),
	TOOL("Tool");
	
	private String techName;

	private ItemClass(String name) {
		this.techName = name;
	}

	public static ItemClass resolve(String name) {

		for (ItemClass state : ItemClass.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
