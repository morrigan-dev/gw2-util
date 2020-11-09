package de.morrigan.dev.gw2.client.gui.map.waypoints;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.dto.common.enums.WPType;

public class WPGW2PointOfInterest extends AbstractGW2Waypoint {

	public WPGW2PointOfInterest(GeoPosition position, String hoverInfo) {
		super(position, hoverInfo, WPType.POI, null);
	}

	@Override
	protected ZoomScale[] getZoomScale() {
		return new ZoomScale[] { new ZoomScale(0, 1), new ZoomScale(1, 1.5), new ZoomScale(2, 1.5), new ZoomScale(3, 3) };
	}
}
