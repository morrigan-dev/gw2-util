package cz.zweistein.gw2.api.dto.enums;

public enum GuildEmblemFlag {
	
	FLIP_FOREGROUND_HORIZONTAL("FlipForegroundHorizontal"),
	FLIP_FOREGROUND_VERTICAL("FlipForegroundVertical"),
	FLIP_BACKGROUND_HORIZONTAL("FlipBackgroundHorizontal"),
	FLIP_BACKGROUND_VERTICAL("FlipBackgroundVertical");

	private String techName;

	private GuildEmblemFlag(String name) {
		this.techName = name;
	}

	public static GuildEmblemFlag resolve(String name) {

		for (GuildEmblemFlag state : GuildEmblemFlag.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}
	
}
