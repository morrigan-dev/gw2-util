package de.morrigan.dev.gw2.utils.enums;

public enum ItemType {

	UNKNOWN,
	TROPHY,
	WEAPON,
	ARMOR,
	CONSUMABLE,
	GIZMO,
	TRINKET,
	CRAFTINGMATERIAL,
	CONTAINER,
	UPGRADECOMPONENT,
	MINIPET,
	BAG,
	BACK,
	TRAITGUIDE,
	GATHERING,
	TOOL,
	MINIDECK;

	public static ItemType getEnumOfOrdinal(int ordinal) {
		ItemType[] values = ItemType.values();
		if ((ordinal < 0) || (ordinal > (values.length - 1))) {
			return UNKNOWN;
		}
		return ItemType.values()[ordinal];
	}
}
