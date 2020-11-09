package de.morrigan.dev.gw2.dto.common.enums;

import org.jboss.logging.Logger;

public enum ItemRarity {

	UNKNOWN("", "unknown"), //
	ASCENDED("Ascended", "ascended"), //
	BASIC("Basic", "basic"), //
	EXOTIC("Exotic", "exotic"), //
	FINE("Fine", "fine"), //
	MASTERWORK("Masterwork", "masterwork"), //
	RARE("Rare", "rare");

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(ItemRarity.class);

	public static final ItemRarity getValueOf(String value) {
		ItemRarity result = null;
		for (ItemRarity itemRarity : values()) {
			if (itemRarity.apiValue.equals(value)) {
				result = itemRarity;
				break;
			}
		}
		if (result == null) {
			LOG.warn(value + " konnte nicht in ein ItemRarity umgewandelt werden!");
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private ItemRarity(final String apiValue, final String labelKey) {
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
