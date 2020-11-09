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

import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Entity
@Table(name = "ClientData")
public class ClientData implements IEntity, IActiveStateEntity, IAuditEntity {

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

	/** MAC Adresse des Clients */
	@Column(name = "MAC", length = 255, nullable = false)
	private String mac;

	/** IP Adresse des Clients */
	@Column(name = "IP", length = 39, nullable = false)
	private String ip;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/** Benutzer zu dem die Clientdaten gehören */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "UserID")
	private User user;

	/**
	 * Konstruktor für Hibernate
	 */
	public ClientData() {
		super();
	}

	/**
	 * Konstruktor zur Erstellung eines neuen ClientData Objekts.
	 * 
	 * @param activeState Aktivstatus des Datensatzes. (not null)
	 * @param createDate Erstellungsdatum des Datensatzes. (not null)
	 * @param createUser Erstellungsbenutzer des Datensatzes. (not null)
	 * @param updateDate Änderungsdatum des Datensatzes. (not null)
	 * @param updateUser Änderungsbenutzer des Datensatzes. (not null)
	 * @param mac MAC Adresse des Clients. (not null)
	 * @param ip IP Adresse des Clients. (not null)
	 * @param user Benutzer zu dem die Clientdaten gehören. (not null)
	 */
	public ClientData(ActiveState activeState, Date createDate, User createUser, Date updateDate, User updateUser,
			String mac, String ip, User user) {
		super();
		this.id = 0;
		this.activeState = activeState;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.mac = mac;
		this.ip = ip;
		this.user = user;
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

	public String getIp() {
		return this.ip;
	}

	public String getMac() {
		return this.mac;
	}

	@Override
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public User getUpdateUser() {
		return this.updateUser;
	}

	public User getUser() {
		return this.user;
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

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", createUser: ").append(this.createUser.getId());
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", updateUser: ").append(this.updateUser.getId());
		sb.append(", mac: ").append(this.mac);
		sb.append(", ip: ").append(this.ip);
		sb.append(", user id: ").append(this.user.getId());
		sb.append("]");
		return sb.toString();
	}
}
