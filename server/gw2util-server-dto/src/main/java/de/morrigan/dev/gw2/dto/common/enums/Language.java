package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Language {

	GERMAN("de", "german"), //
	ENGLISH("en", "english"), //
	SPANISH("es", "spanish"), //
	FRENCH("fr", "french");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(Language.class);

	public static final Language getValueOf(String value) {
		Language result = null;
		for (Language language : values()) {
			if (language.apiValue.equals(value)) {
				result = language;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein Language umgewandelt werden!", value);
			result = ENGLISH;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private Language(String apiValue, String labelKey) {
		this.apiValue = apiValue;
		this.labelKey = labelKey;
	}

	public String getApiValue() {
		return this.apiValue;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
