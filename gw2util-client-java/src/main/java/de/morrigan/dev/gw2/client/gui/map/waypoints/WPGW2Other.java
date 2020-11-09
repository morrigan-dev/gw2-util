package de.morrigan.dev.gw2.client.gui.map.waypoints;

import java.util.Date;

import org.jboss.logging.Logger;
import org.jdesktop.swingx.mapviewer.GeoPosition;

import de.morrigan.dev.gw2.client.gui.AbstractGW2Waypoint;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.resources.ResourceManager;

public class WPGW2Other extends AbstractGW2Waypoint {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(WPGW2Other.class);

	private final long id;
	private final Date updateDate;
	private final String username;

	public WPGW2Other(long id, GeoPosition position, WPType wpType, WPSubType wpSubType, Date updateDate,
			String username) {
		super(position, ResourceManager.getInstance().getLabel(wpSubType.getLabelKey()), wpType, wpSubType);
		this.id = id;
		this.updateDate = updateDate;
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WPGW2Other other = (WPGW2Other) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", position: ").append(getPosition());
		sb.append(", wpType: ").append(getWpType());
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected ZoomScale[] getZoomScale() {
		ZoomScale[] result = new ZoomScale[] {};
		switch (getWPSubType()) {
			case GUILD_BOUNTY:
				result = new ZoomScale[] { new ZoomScale(0, 1), new ZoomScale(1, 1.5), new ZoomScale(2, 2),
						new ZoomScale(3, 2.5) };
				break;

			case CHEST:
				result = new ZoomScale[] { new ZoomScale(0, 1), new ZoomScale(1, 1.5), new ZoomScale(2, 2.5),
						new ZoomScale(3, 3) };
				break;
		}
		return result;
	}
}
