package de.morrigan.dev.gw2.dto.common.enums;

import org.jboss.logging.Logger;

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

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(ItemType.class);

	public static final ItemType getValueOf(String value) {
		ItemType result = null;
		for (ItemType itemType : values()) {
			if (itemType.apiValue.equals(value)) {
				result = itemType;
				break;
			}
		}
		if (result == null) {
			LOG.warn(value + " konnte nicht in ein ItemType umgewandelt werden!");
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

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
}
