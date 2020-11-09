package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DamageType {

	UNKNOWN("", "unknown"),
	CHOKING("Choking", "choking"),
	FIRE("Fire", "fire"),
	ICE("Ice", "ice"),
	LIGHTNING("Lightning", "lightning"),
	PHYSICAL("Physical", "physical");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(DamageType.class);

	public static final DamageType getValueOf(String value) {
		DamageType result = null;
		for (DamageType damageType : values()) {
			if (damageType.apiValue.equals(value)) {
				result = damageType;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein DamageType umgewandelt werden!", value);
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
}
