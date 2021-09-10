package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Flag {

	UNKNOWN("", "unknown"), //

	// Item Flags
	ACCOUNT_BOUND("AccountBound", "accountBound"), //
	ACCOUNT_BIND_ON_USE("AccountBindOnUse", "accountBindOnUse"), //
	HIDE_SUFFIX("HideSuffix", "hideSuffix"), //
	NO_SALVAGE("NoSalvage", "noSalvage"), //
	NO_MYSTIC_FORGE("NoMysticForge", "noMysticForge"), //
	NO_SELL("NoSell", "noSell"), //
	SOUL_BIND_ON_USE("SoulBindOnUse", "soulBindOnUse"), //
	SOUL_BIND_ON_ACQUIRE("SoulbindOnAcquire", "soulbindOnAcquire"), //

	// Rezepte Flags
	LEARNED_FROM_ITEM("LearnedFromItem", "learnedFromItem"), //
	AUTO_LEARNED("AutoLearned", "autoLearned");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(Flag.class);

	public static final Flag getValueOf(String value) {
		Flag result = null;
		for (Flag flag : values()) {
			if (flag.apiValue.equals(value)) {
				result = flag;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein Flag umgewandelt werden!", value);
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private Flag(final String apiValue, final String labelKey) {
		this.apiValue = apiValue;
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
