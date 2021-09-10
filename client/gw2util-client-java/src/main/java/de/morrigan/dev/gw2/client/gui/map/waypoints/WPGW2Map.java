package de.morrigan.dev.gw2.client.gui.map.waypoints;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.dto.common.enums.WPType;

public class WPGW2Map extends AbstractGW2Waypoint {

	private String mapName;
	private Long minLevel;
	private Long maxLevel;
	private GeoPosition topLeft;
	private GeoPosition bottomRight;

	public WPGW2Map(GeoPosition position, String mapName, Long minLevel, Long maxLevel, GeoPosition topLeft,
			GeoPosition bottomRight) {
		super(position, "", WPType.MAP_INFO, null);

		this.mapName = mapName;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public GeoPosition getBottomRight() {
		return this.bottomRight;
	}

	public String getMapName() {
		return this.mapName;
	}

	public Long getMaxLevel() {
		return this.maxLevel;
	}

	public Long getMinLevel() {
		return this.minLevel;
	}

	public GeoPosition getTopLeft() {
		return this.topLeft;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[mapName: ").append(this.mapName);
		sb.append(", minLevel: ").append(this.minLevel);
		sb.append(", maxLevel: ").append(this.maxLevel);
		sb.append(", topLeft: ").append(this.topLeft);
		sb.append(", bottomRight: ").append(this.bottomRight);
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected ZoomScale[] getZoomScale() {
		return new ZoomScale[] { new ZoomScale(4, 1), new ZoomScale(5, 1) };
	}
}
