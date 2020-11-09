package de.morrigan.dev.gw2.utils.enums;

/**
 * Seltenheitsgrad eines Items aus GuildWars 2.
 * 
 * @author morrigan
 */
public enum ItemRarity {

	UNKNOWN, JUNK, BASIC, COMMON, UNCOMMON, RARE, EXOTIC, LEGENDARY;

	public static ItemRarity getEnumOfOrdinal(int ordinal) {
		ItemRarity[] values = ItemRarity.values();
		if ((ordinal < 0) || (ordinal > values.length - 1)) {
			return UNKNOWN;
		}
		return ItemRarity.values()[ordinal];
	}
}
