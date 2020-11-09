package de.morrigan.dev.gw2.utils.enums;

public enum ItemType {

	UNKNOWN, // 0
	TROPHY, // 1
	WEAPON, // 2
	ARMOR, // 3
	CONSUMABLE, // 4
	GIZMO, // 5
	TRINKET, // 6
	CRAFTINGMATERIAL, // 7
	CONTAINER, // 8
	UPGRADECOMPONENT, // 9
	MINIPET, // 10
	BAG, // 11
	BACK, // 12
	TRAITGUIDE, // 13
	GATHERING, // 14
	TOOL, // 15
	MINIDECK; // 15

	public static ItemType getEnumOfOrdinal(int ordinal) {
		ItemType[] values = ItemType.values();
		if ((ordinal < 0) || (ordinal > (values.length - 1))) {
			return UNKNOWN;
		}
		return ItemType.values()[ordinal];
	}
}
