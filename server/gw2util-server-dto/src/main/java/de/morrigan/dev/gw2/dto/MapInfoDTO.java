package de.morrigan.dev.gw2.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;

public class MapInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<WPSubType, Integer> resourceAmount;
	private Date latestChange;
	private Date nearestChange;

	public MapInfoDTO() {
		super();
	}

	public MapInfoDTO(Map<WPSubType, Integer> resourceAmount, Date latestChange, Date nearestChange) {
		super();
		this.resourceAmount = resourceAmount;
		this.latestChange = latestChange;
		this.nearestChange = nearestChange;
	}

	public Date getLatestChange() {
		return this.latestChange;
	}

	public Date getNearestChange() {
		return this.nearestChange;
	}

	public Map<WPSubType, Integer> getResourceAmount() {
		return this.resourceAmount;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[resourceAmount size: ").append(this.resourceAmount.size());
		sb.append(", latestChange: ").append(this.latestChange);
		sb.append(", nearestChange: ").append(this.nearestChange);
		sb.append("]");
		return sb.toString();
	}

}
