package cz.zweistein.gw2.api.dto.enums.items;

public enum ItemFlag {
	
	ACCOUNT_BOUND("AccountBound"),
	SOULD_BIND_ON_USE("SoulBindOnUse"),
	HIDE_SUFFIX("HideSuffix"),
	NO_SALVAGE("NoSalvage"),
	NO_SELL("NoSell"),
	SOULD_BIND_ON_ACQUIRE("SoulbindOnAcquire"),
	NO_UNDERWATER("NoUnderwater"),
	NO_MYSTIC_FORGE("NoMysticForge"),
	NOT_UPGRADEABLE("NotUpgradeable"),
	UNIQUE("Unique");
	
	private String techName;

	private ItemFlag(String name) {
		this.techName = name;
	}

	public static ItemFlag resolve(String name) {

		for (ItemFlag state : ItemFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}
	
}
