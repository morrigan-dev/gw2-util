package cz.zweistein.gw2.api.dto.enums.items;

public enum ItemType {
	
	BOOTS("Boots"),
	DESSERT("Dessert"),
	COMPONENT("Component"),
	AMULET("Amulet"),
	WARHORN("Warhorn"),
	LEGGINGS("Leggings"),
	PISTOL("Pistol"),
	SHOULDERS("Shoulders"),
	TRIDENT("Trident"),
	HELM("Helm"),
	SHORTBOW("ShortBow"),
	MEAL("Meal"),
	MACE("Mace"),
	LONGBOW("LongBow"),
	DAGGER("Dagger"),
	RING("Ring"),
	HARPOON("Harpoon"),
	SWORD("Sword"),
	SHIELD("Shield"),
	UPGRADE_COMPONENT("UpgradeComponent"),
	GREATSWORD("Greatsword"),
	INGREDIENT_COOKING("IngredientCooking"),
	RIFLE("Rifle"),
	DYE("Dye"),
	INSCRIPTION("Inscription"),
	BULK("Bulk"),
	HAMMER("Hammer"),
	SNACK("Snack"),
	FOCUS("Focus"),
	GLOVES("Gloves"),
	SEASONING("Seasoning"),
	FEAST("Feast"),
	STAFF("Staff"),
	SCEPTER("Scepter"),
	SOUP("Soup"),
	CONSUMABLE("Consumable"),
	AXE("Axe"),
	REFINEMENT("Refinement"),
	POTION("Potion"),
	INSIGNIA("Insignia"),
	SPEARGUN("Speargun"),
	BAG("Bag"),
	EARRING("Earring"),
	TORCH("Torch"),
	COAT("Coat"),
	ACCESSORY("Accessory"),
	HELM_AQUATIC("HelmAquatic"),
	TOY("Toy"),
	TWO_HANDED_TOY("TwoHandedToy");
	
	private String techName;

	private ItemType(String name) {
		this.techName = name;
	}

	public static ItemType resolve(String name) {

		for (ItemType state : ItemType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
