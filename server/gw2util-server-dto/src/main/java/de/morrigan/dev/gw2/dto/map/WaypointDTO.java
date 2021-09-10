package de.morrigan.dev.gw2.dto.map;

import java.io.Serializable;
import java.util.Date;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;

public class WaypointDTO implements Serializable {

	private static final long serialVersionUID = 6287852547846533410L;

	private long id;
	private Date updateDate;
	private String updateUsername;
	private WPType wpType;
	private WPSubType wpSubType;
	private double longitude;
	private double latitude;
	private String informationKey = "";
	private boolean rich;
	private boolean permanent;

	public WaypointDTO() {
		super();
	}

	public WaypointDTO(long id, Date updateDate, String updateUsername, WPType wpType, WPSubType wpSubType,
			double longitude, double latitude, String informationKey, boolean rich, boolean permanent) {
		super();
		this.id = id;
		this.updateDate = updateDate;
		this.updateUsername = updateUsername;
		this.wpType = wpType;
		this.wpSubType = wpSubType;
		this.longitude = longitude;
		this.latitude = latitude;
		this.informationKey = informationKey;
		this.rich = rich;
		this.permanent = permanent;
	}

	public long getId() {
		return this.id;
	}

	public String getInformationKey() {
		return this.informationKey;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateUsername() {
		return this.updateUsername;
	}

	public WPSubType getWpSubType() {
		return this.wpSubType;
	}

	public WPType getWpType() {
		return this.wpType;
	}

	public boolean isPermanent() {
		return this.permanent;
	}

	public boolean isRich() {
		return this.rich;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInformationKey(String informationKey) {
		this.informationKey = informationKey;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public void setRich(boolean rich) {
		this.rich = rich;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}

	public void setWpSubType(WPSubType wpSubType) {
		this.wpSubType = wpSubType;
	}

	public void setWpType(WPType wpType) {
		this.wpType = wpType;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", updateUsername: ").append(this.updateUsername);
		sb.append(", wpType: ").append(this.wpType);
		sb.append(", wpSubType: ").append(this.wpSubType);
		sb.append(", longitude: ").append(this.longitude);
		sb.append(", latitude: ").append(this.latitude);
		sb.append(", informationKey: ").append(this.informationKey);
		sb.append(", rich: ").append(this.rich);
		sb.append(", permanent: ").append(this.permanent);
		sb.append("]");
		return sb.toString();
	}
}
