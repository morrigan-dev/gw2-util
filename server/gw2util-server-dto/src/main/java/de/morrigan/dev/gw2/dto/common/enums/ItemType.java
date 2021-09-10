package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ItemType {

	UNKNOWN("", "unknown"), //
	ARMOR("Armor", "armor"), //
	BACK("Back", "back"), //
	BAG("Bag", "bag"), //
	CONSUMABLE("Consumable", "consumable"), //
	CONTAINER("Container", "container"), //
	CRAFTING_MATERIAL("CraftingMaterial", "craftingMaterial"), //
	GIZMO("Gizmo", "gizmo"), //
	MASTERWORK("Masterwork", "masterwork"), //
	MINIPET("Minipet", "minipet"), //
	TRINKET("Trinket", "trinket"), //
	TROPHY("Trophy", "trophy"), //
	UPGRADE_COMPONENT("UpgradeComponent", "upgradeComponent"), //
	WEAPON("Weapon", "weapon");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(ItemType.class);

	public static final ItemType getValueOf(String value) {
		ItemType result = null;
		for (ItemType itemType : values()) {
			if (itemType.apiValue.equals(value)) {
				result = itemType;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein ItemType umgewandelt werden!", value);
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private ItemType(final String apiValue, final String labelKey) {
		this.apiValue = apiValue;
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
