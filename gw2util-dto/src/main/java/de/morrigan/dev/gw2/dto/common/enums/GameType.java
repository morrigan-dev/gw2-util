package de.morrigan.dev.gw2.dto.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum GameType {

	UNKNOWN("", "unknown"), //
	ACTIVITY("Activity", "activity"), //
	DUNGEON("Dungeon", "dungeon"), //
	PVE("Pve", "pve"), //
	WVW("Wvw", "wvw"), //
	PVP("Pvp", "pvp"), //
	PVP_LOBBY("PvpLobby", "pvpLobby");

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GameType.class);

	public static final GameType getValueOf(String value) {
		GameType result = null;
		for (GameType gameType : values()) {
			if (gameType.apiValue.equals(value)) {
				result = gameType;
				break;
			}
		}
		if (result == null) {
			LOG.warn("{} konnte nicht in ein GameType umgewandelt werden!", value);
			result = UNKNOWN;
		}
		return result;
	}

	private String apiValue;
	private String labelKey;

	private GameType(final String apiValue, final String labelKey) {
		this.apiValue = apiValue;
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
