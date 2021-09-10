package cz.zweistein.gw2.api.dto.enums.items;

public enum UpgradeComponentFlag {
	
	AXE("Axe"),
	LONGBOW("LongBow"),
	SHOTBOW("ShortBow"),
	DAGGER("Dagger"),
	FOCUS("Focus"),
	GREATSWORD("Greatsword"),
	HAMMER("Hammer"),
	HARPOON("Harpoon"),
	MACE("Mace"),
	PISTOL("Pistol"),
	RIFLE("Rifle"),
	SCEPTER("Scepter"),
	SHIELD("Shield"),
	SPEARGUN("Speargun"),
	STAFF("Staff"),
	SWORD("Sword"),
	TORCH("Torch"),
	TRIDENT("Trident"),
	WARHORN("Warhorn"),
	TRINKET("Trinket"),
	HEAVY_ARMOR("HeavyArmor"),
	LIGHT_ARMOR("LightArmor"),
	MEDIUM_ARMOR("MediumArmor");
	
	private String techName;

	private UpgradeComponentFlag(String name) {
		this.techName = name;
	}

	public static UpgradeComponentFlag resolve(String name) {

		for (UpgradeComponentFlag state : UpgradeComponentFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
