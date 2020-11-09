package de.morrigan.dev.gw2.client.gui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnailator;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.resources.ImageManager;

public abstract class AbstractGW2Waypoint implements Waypoint {

	protected class ZoomScale {

		private final int zoom;
		private final double scale;

		public ZoomScale(int zoom, double scale) {
			super();
			this.zoom = zoom;
			this.scale = scale;
		}

		public double getScale() {
			return this.scale;
		}

		public int getZoom() {
			return this.zoom;
		}
	}

	/** Handel auf den ImageManager */
	protected static final ImageManager IMAGE_MANAGER = ImageManager.getInstance();

	/** Basis URL zur ArenaNet GW2 Tile API */
	protected static final String BASE_URL = "https://render.guildwars2.com/file/";

	public static BufferedImage getImageByType(WPType wpType, WPSubType wpSubType, boolean rich, boolean permanent) {
		BufferedImage icon;
		switch (wpType) {
			case ORE:
				if (rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ORE_RICH_PERMANENT_ICON);
				} else if (rich && !permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ORE_RICH_ICON);
				} else if (!rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ORE_PERMANENT_ICON);
				} else {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.ORE_ICON);
				}
				break;
			case WOOD:
				if (rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.WOOD_RICH_PERMANENT_ICON);
				} else if (rich && !permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.WOOD_RICH_ICON);
				} else if (!rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.WOOD_PERMANENT_ICON);
				} else {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.WOOD_ICON);
				}
				break;
			case PLANT:
				if (rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_RICH_PERMANENT_ICON);
				} else if (rich && !permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_RICH_ICON);
				} else if (!rich && permanent) {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_PERMANENT_ICON);
				} else {
					icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.PLANT_ICON);
				}
				break;
			case POI:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.POI_ICON);
				break;
			case UNLOCK:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.UNLOCK_ICON);
				break;
			case VISTA:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.VISTA_ICON);
				break;
			case SKILL_CHALLENGE:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.SKILL_CHALLEGENE_ICON);
				break;
			case HEART:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.HEART_ICON);
				break;
			case WAYPOINT:
				icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.WAYPOINT_ICON);
				break;
			case OTHER:
				switch (wpSubType) {
					case GUILD_BOUNTY:
						icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.GUILD_BOUNTY_ICON);
						break;
					case CHEST:
						if (permanent) {
							icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.CHEST_PERMANENT_ICON);
						} else {
							icon = (BufferedImage) IMAGE_MANAGER.getImage(ImageManager.CHEST_ICON);
						}
						break;

					default:
						icon = null;
						break;
				}
				break;

			default:
				icon = null;
				break;
		}
		return icon;
	}

	/** Art des Wegpunktes */
	private WPType wpType;

	/** Unterart des Wegpunktes */
	private final WPSubType wpSubType;

	/** Position des Wegpunktes auf der Karte */
	private final GeoPosition position;

	/** Infotext für die Tooltip-Anzeige */
	private final String hoverInfo;

	/** Gibt an, ob ein Hover angezeigt werden soll */
	private final boolean hoverEnabled;

	/** Map mit den Icons für einen GW2 Wegpunkt. Der Schlüssel entspricht einem Zoomlevel. */
	private Map<Integer, BufferedImage> icons = new HashMap<Integer, BufferedImage>();

	public AbstractGW2Waypoint(GeoPosition position, String hoverInfo, WPType wpType, WPSubType wpSubType) {
		this(position, hoverInfo, wpType, wpSubType, false, false);
	}

	public AbstractGW2Waypoint(GeoPosition position, String hoverInfo, WPType wpType, WPSubType wpSubType,
			boolean rich, boolean permanent) {
		super();

		this.position = position;
		this.hoverInfo = hoverInfo;
		this.wpType = wpType;
		this.wpSubType = wpSubType;
		if ((hoverInfo == null) || hoverInfo.isEmpty()) {
			this.hoverEnabled = false;
		} else {
			this.hoverEnabled = true;
		}

		updateIcon(wpType, wpSubType, rich, permanent);
	}

	public AbstractGW2Waypoint(GeoPosition position, WPType wpType, WPSubType wpSubType) {
		this(position, null, wpType, wpSubType, false, false);
	}

	public String getHoverInfo() {
		return this.hoverInfo;
	}

	public BufferedImage getIcon(int zoom) {
		return this.icons.get(zoom);
	}

	public long getId() {
		return 0;
	}

	@Override
	public GeoPosition getPosition() {
		return this.position;
	}

	public WPSubType getWPSubType() {
		return this.wpSubType;
	}

	public WPType getWpType() {
		return this.wpType;
	}

	public boolean isHoverEnabled() {
		return this.hoverEnabled;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[position: ").append(this.position);
		sb.append(", wpType: ").append(this.wpType);
		sb.append(", wpSubType: ").append(this.wpSubType);
		sb.append("]");
		return sb.toString();
	}

	protected abstract ZoomScale[] getZoomScale();

	protected void updateIcon(WPType wpType, WPSubType wpSubType, boolean rich, boolean permanent) {
		BufferedImage icon = getImageByType(wpType, wpSubType, rich, permanent);
		if (icon != null) {
			ZoomScale[] zoomScales = getZoomScale();
			this.icons.clear();
			for (int i = 0; i < zoomScales.length; i++) {
				ZoomScale zoomScale = zoomScales[i];
				int width = (int) (icon.getWidth() / zoomScale.getScale());
				int height = (int) (icon.getHeight() / zoomScale.getScale());
				BufferedImage resizedIcon = Thumbnailator.createThumbnail(icon, width, height);
				this.icons.put(zoomScale.getZoom(), resizedIcon);
			}
		}
	}
}
