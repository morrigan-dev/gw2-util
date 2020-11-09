package de.morrigan.dev.gw2.client.gui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.jboss.logging.Logger;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

import de.morrigan.dev.gw2.client.Main;
import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Map;
import de.morrigan.dev.gw2.dto.common.enums.WPType;

public class GW2MapRenderer implements WaypointRenderer<Waypoint> {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(GW2MapRenderer.class);

	private JXMapViewer mapViewer;

	/** Zoom Level ab dem Markierungen auf der Karte angezeigt werden */
	private int markersZoomLevel;

	public GW2MapRenderer(JXMapViewer mapViewer, int markersZoomLevel) {
		super();

		this.mapViewer = mapViewer;
		this.markersZoomLevel = markersZoomLevel;
	}

	@Override
	public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
		int zoom = this.mapViewer.getZoom();

		if (waypoint instanceof AbstractGW2Waypoint) {
			AbstractGW2Waypoint wp = (AbstractGW2Waypoint) waypoint;
			if (wp.getWpType() == WPType.MAP_INFO) {
				WPGW2Map wpMap = (WPGW2Map) waypoint;
				Point2D p1 = this.mapViewer.getTileFactory().geoToPixel(wpMap.getTopLeft(), zoom);
				Point2D p2 = this.mapViewer.getTileFactory().geoToPixel(wpMap.getBottomRight(), zoom);
				float fontSize = 25f;
				switch (zoom) {
					case 5:
						fontSize = 16f;
						break;
					case 4:
						fontSize = 20f;
						break;
					case 3:
						fontSize = 24f;
						break;
					case 2:
						fontSize = 26f;
						break;
					default:
						break;
				}
				int width = (int) p2.getX() - (int) p1.getX();
				int height = (int) p2.getY() - (int) p1.getY();
				// g.setColor(new Color(100, 100, 100));
				// g.drawRect((int) p1.getX(), (int) p1.getY(), width, height);

				if (zoom > 1) {
					Font labelFont = Main.getInstance().getCronosItalic().deriveFont(fontSize);
					g.setFont(labelFont);
					FontMetrics fm = g.getFontMetrics();

					// Zeichne Kartenname
					String mapName = wpMap.getMapName();
					int labelWidth = fm.stringWidth(mapName);
					int labelX = ((int) p1.getX() - (labelWidth / 2)) + (width / 2);

					g.setColor(new Color(30, 30, 30, 150));
					g.drawString(mapName, labelX - 2, (int) p1.getY() + (height / 2));
					g.drawString(mapName, labelX + 2, (int) p1.getY() + (height / 2));
					g.drawString(mapName, labelX, ((int) p1.getY() + (height / 2)) - 2);
					g.drawString(mapName, labelX, (int) p1.getY() + (height / 2) + 2);

					g.setColor(new Color(30, 30, 30));
					g.drawString(mapName, labelX - 1, (int) p1.getY() + (height / 2));
					g.drawString(mapName, labelX + 1, (int) p1.getY() + (height / 2));
					g.drawString(mapName, labelX, ((int) p1.getY() + (height / 2)) - 1);
					g.drawString(mapName, labelX, (int) p1.getY() + (height / 2) + 1);

					g.setColor(new Color(216, 174, 100));
					g.drawString(mapName, labelX, (int) p1.getY() + (height / 2));

					// Zeichne Min Max Level
					StringBuilder minMaxLevel = new StringBuilder();
					minMaxLevel.append(wpMap.getMinLevel()).append(" - ").append(wpMap.getMaxLevel());
					String minMaxString = minMaxLevel.toString();

					labelWidth = fm.stringWidth(minMaxString);
					labelX = ((int) p1.getX() - (labelWidth / 2)) + (width / 2);
					int minMaxLevelOffset = fm.getHeight();

					g.setColor(new Color(30, 30, 30, 150));
					g.drawString(minMaxString, labelX - 2, (int) p1.getY() + (height / 2) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX + 2, (int) p1.getY() + (height / 2) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX, (((int) p1.getY() + (height / 2)) - 2) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX, (int) p1.getY() + (height / 2) + 2 + minMaxLevelOffset);

					g.setColor(new Color(30, 30, 30));
					g.drawString(minMaxString, labelX - 1, (int) p1.getY() + (height / 2) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX + 1, (int) p1.getY() + (height / 2) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX, (((int) p1.getY() + (height / 2)) - 1) + minMaxLevelOffset);
					g.drawString(minMaxString, labelX, (int) p1.getY() + (height / 2) + 1 + minMaxLevelOffset);

					g.setColor(new Color(133, 133, 133));
					g.drawString(minMaxString, labelX, (int) p1.getY() + (height / 2) + minMaxLevelOffset);

				}

			} else {
				if (zoom > this.markersZoomLevel) {
					return;
				}
				BufferedImage imgToDraw = wp.getIcon(zoom);
				if (imgToDraw != null) {
					Point2D p = this.mapViewer.getTileFactory().geoToPixel(wp.getPosition(), zoom);
					g.drawImage(imgToDraw, (int) (p.getX() - (imgToDraw.getWidth() / 2)),
							(int) (p.getY() - (imgToDraw.getHeight() / 2)), null);
				} else {
					LOG.warn("Bild nicht vorhanden: " + wp.getClass());
				}
			}
		}
	}
}
