package de.morrigan.dev.gw2.client.gui.map;

import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GW2TileFactoryInfo extends TileFactoryInfo {
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GW2TileFactoryInfo.class);

	/** Größe der Kartenteile */
	public static final int TILE_SIZE = 256;

	/** Maximales Zoomlevel, das vom Benutzer ausgewählt werden kann */
	public static final int MAX_AVAILABLE_ZOOM = 5;

	/** Minimales Zoomlevel, das vom Benutzer ausgewählt werden kann */
	public static final int MIN_AVAILABLE_ZOOM = 0;

	/** Maximales Zoomlevel der Karte */
	private static final int MAX_ZOOM = 7;

	/** Basis URL zur ArenaNet GW2 Tile API */
	private static final String BASE_URL = "https://tiles.guildwars2.com/";

	public GW2TileFactoryInfo(int continendId, int floor) {
		super(MIN_AVAILABLE_ZOOM, MAX_AVAILABLE_ZOOM, MAX_ZOOM, TILE_SIZE, true, true, BASE_URL + continendId + "/"
				+ floor + "/", "x", "y", "z");
		LOG.debug("continendId: {}, floor: {}", continendId, floor);
	}

	@Override
	public String getTileUrl(int x, int y, int zoom) {
		zoom = MAX_ZOOM - zoom;
		return this.baseURL + zoom + "/" + x + "/" + y + ".jpg";
	}
}
