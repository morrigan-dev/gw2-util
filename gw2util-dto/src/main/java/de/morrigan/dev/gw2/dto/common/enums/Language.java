package de.morrigan.dev.gw2.dto.common.enums;

import org.jboss.logging.Logger;

public enum Language {

	GERMAN("de", "german"), //
	ENGLISH("en", "english"), //
	SPANISH("es", "spanish"), //
	FRENCH("fr", "french");

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(Language.class);

	public static final Language getValueOf(String value) {
		Language result = null;
		for (Language language : values()) {
			if (language.apiValue.equals(value)) {
				result = language;
				break;
			}
		}
		if (result == null) {
			LOG.warn(value + " konnte nicht in ein Language umgewandelt werden!");
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

	public void setApiValue(String apiValue) {
		this.apiValue = apiValue;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}
}
