package cz.zweistein.gw2.api.dto.enums;

public enum GameType {
	
	ACTIVITY("Activity"),
	DUNGEON("Dungeon"),
	PVE("Pve"),
	WVE("Wvw"),
	PVP("Pvp"),
	PVP_LOBBY("PvpLobby");
	
	private String techName;

	private GameType(String name) {
		this.techName = name;
	}

	public static GameType resolve(String name) {

		for (GameType state : GameType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}

		throw new IllegalArgumentException(name);
	}

}
