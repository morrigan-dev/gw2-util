package de.morrigan.dev.gw2.dto.common.enums;

import org.jboss.logging.Logger;

public enum DamageType {

	UNKNOWN("", "unknown"), //
	CHOKING("Choking", "choking"), //
	FIRE("Fire", "fire"), //
	ICE("Ice", "ice"), //
	LIGHTNING("Lightning", "lightning"), //
	PHYSICAL("Physical", "physical");

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(DamageType.class);

	public static final DamageType getValueOf(String value) {
		DamageType result = null;
		for (DamageType damageType : values()) {
			if (damageType.apiValue.equals(value)) {
				result = damageType;
				break;
			}
		}
		if (result == null) {
			LOG.warn(value + " konnte nicht in ein DamageType umgewandelt werden!");
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private DamageType(final String apiValue, final String labelKey) {
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
