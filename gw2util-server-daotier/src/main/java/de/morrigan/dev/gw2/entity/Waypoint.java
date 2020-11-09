package de.morrigan.dev.gw2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.dto.common.enums.WPType;
import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Entity
@Table(name = "Waypoint")
public class Waypoint implements IEntity, IActiveStateEntity, IAuditEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	/** Aktivstatus des Datensatzes */
	@Enumerated(EnumType.STRING)
	@Column(name = "ActiveState", length = 50, nullable = false)
	private ActiveState activeState;

	/** Erstellungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	private Date createDate;

	/** Erstellungsbenutzer des Datensatzes */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CreateUser", nullable = false)
	private User createUser;

	/** Änderungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdateDate", nullable = false)
	private Date updateDate;

	/** Änderungsbenutzer des Datensatzes */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UpdateUser", nullable = false)
	private User updateUser;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	/** Art des Wegpunktes */
	@Enumerated(EnumType.STRING)
	@Column(name = "WPType", length = 50, nullable = false)
	private WPType wpType;

	/** Unterart des Wegpunktes, der speziellere Informationen über den Wegpunkt liefert */
	@Enumerated(EnumType.STRING)
	@Column(name = "WPSubType", length = 50, nullable = false)
	private WPSubType wpSubType;

	/** Geographischer Längengrad des Wegpunktes auf der Karte */
	@Column(name = "Longitude", nullable = false)
	private double longitude;

	/** Geographischer Breitengrad des Wegpunktes auf der Karte */
	@Column(name = "Latitude", nullable = false)
	private double latitude;

	/** Schlüssel auf die Hauptinformation des Wegpunktes */
	@Column(name = "InformationKey", length = 255, nullable = false)
	private String informationKey = "";

	/** Flag für reichhaltige Resourcen */
	@Column(name = "Rich", length = 1, nullable = false)
	private boolean rich;

	/** Flag für permanente Resourcen */
	@Column(name = "Permanent", length = 1, nullable = false)
	private boolean permanent;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/**
	 * Konstruktor für Hibernate
	 */
	public Waypoint() {
		super();
	}

	/**
	 * Konstruktor zur Erstellung eines neuen Wegpunktes.
	 * 
	 * @param activeState Aktivstatus des Datensatzes. (not null)
	 * @param createDate Erstellungsdatum des Datensatzes. (not null)
	 * @param createUser Erstellungsbenutzer des Datensatzes. (not null)
	 * @param updateDate Änderungsdatum des Datensatzes. (not null)
	 * @param updateUser Änderungsbenutzer des Datensatzes. (not null)
	 * @param wpType Art des Wegpunktes. (not null)
	 * @param wpSubType Unterart des Wegpunktes, der speziellere Informationen über den Wegpunkt liefert. (not null)
	 * @param longitude Geographischer Längengrad des Wegpunktes auf der Karte. (not null)
	 * @param latitude Geographischer Breitengrad des Wegpunktes auf der Karte. (not null)
	 * @param informationKey Schlüssel auf die Hauptinformation des Wegpunktes. (not null)
	 * @param rich Flag für reichhaltige Resourcen.
	 * @param permanent Flag für permanente Resourcen
	 */
	public Waypoint(long id, ActiveState activeState, Date createDate, User createUser, Date updateDate,
			User updateUser, WPType wpType, WPSubType wpSubType, double longitude, double latitude,
			String informationKey, boolean rich, boolean permanent) {
		super();
		this.id = 0;
		this.activeState = activeState;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.wpType = wpType;
		this.wpSubType = wpSubType;
		this.longitude = longitude;
		this.latitude = latitude;
		this.informationKey = informationKey;
		this.rich = rich;
		this.permanent = permanent;
	}

	@Override
	public ActiveState getActiveState() {
		return this.activeState;
	}

	@Override
	public Date getCreateDate() {
		return this.createDate;
	}

	@Override
	public User getCreateUser() {
		return this.createUser;
	}

	@Override
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

	@Override
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public User getUpdateUser() {
		return this.updateUser;
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

	@Override
	public void setActiveState(ActiveState activeState) {
		this.activeState = activeState;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	@Override
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

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
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
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", createUser id: ").append(this.createUser.getId());
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", updateUser id: ").append(this.updateUser.getId());
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
