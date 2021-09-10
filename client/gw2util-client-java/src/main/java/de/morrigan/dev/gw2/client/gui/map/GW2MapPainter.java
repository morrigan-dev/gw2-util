package de.morrigan.dev.gw2.client.gui.map;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultWaypointRenderer;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;
import org.jdesktop.swingx.painter.AbstractPainter;

public class GW2MapPainter extends AbstractPainter<JXMapViewer> {

	private WaypointRenderer<? super Waypoint> renderer = new DefaultWaypointRenderer();

	private List<Waypoint> waypoints = new CopyOnWriteArrayList<>();

	public GW2MapPainter(JXMapViewer mapViewer) {
		super();

		setAntialiasing(true);
		setCacheable(false);
	}

	public List<Waypoint> getWaypoints() {
		return this.waypoints;
	}

	public void setRenderer(WaypointRenderer<Waypoint> r) {
		this.renderer = r;
	}

	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints.clear();
		this.waypoints.addAll(waypoints);
	}

	@Override
	protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
		if (this.renderer == null) {
			return;
		}

		Rectangle viewportBounds = map.getViewportBounds();

		g.translate(-viewportBounds.getX(), -viewportBounds.getY());

		for (Waypoint w : getWaypoints()) {
			this.renderer.paintWaypoint(g, map, w);
		}

		g.translate(viewportBounds.getX(), viewportBounds.getY());

	}
}
