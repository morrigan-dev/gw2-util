package de.morrigan.dev.gw2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "GemsStatistic")
public class GemsStatistic implements IEntity, IAuditEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

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

	/** Anzahl der Edelsteine, die aus einem Gold umgewandelt werden */
	@Column(name = "CoinsToGem", length = 255, nullable = false)
	private long coinsToGem;

	/** Anzahl an Kupfer das aus einem Edelstein umgewandelt wird */
	@Column(name = "GemToCoins", length = 255, nullable = false)
	private long gemToCoins;

	/**
	 * Konstruktor für Hibernate
	 */
	public GemsStatistic() {
		super();
	}

	/**
	 * Konstruktor zur Erstellung eines neuen GemsStatistic Objektes.
	 * 
	 * @param createDate Erstellungsdatum des Datensatzes. (not null)
	 * @param createUser Erstellungsbenutzer des Datensatzes. (not null)
	 * @param updateDate Änderungsdatum des Datensatzes. (not null)
	 * @param updateUser Änderungsbenutzer des Datensatzes. (not null)
	 * @param coinsToGem Anzahl der Edelsteine, die aus einem Gold umgewandelt werden
	 * @param gemToCoins Anzahl an Kupfer das aus einem Edelstein umgewandelt wird
	 */
	public GemsStatistic(long id, Date createDate, User createUser, Date updateDate, User updateUser, long coinsToGem,
			long gemToCoins) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.coinsToGem = coinsToGem;
		this.gemToCoins = gemToCoins;
	}

	public long getCoinsToGem() {
		return this.coinsToGem;
	}

	@Override
	public Date getCreateDate() {
		return this.createDate;
	}

	@Override
	public User getCreateUser() {
		return this.createUser;
	}

	public long getGemToCoins() {
		return this.gemToCoins;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public User getUpdateUser() {
		return this.updateUser;
	}

	public void setCoinsToGem(long coinsToGem) {
		this.coinsToGem = coinsToGem;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public void setGemToCoins(long gemToCoins) {
		this.gemToCoins = gemToCoins;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", createUser: ").append(this.createUser);
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", updateUser: ").append(this.updateUser);
		sb.append(", coinsToGem: ").append(this.coinsToGem);
		sb.append(", gemToCoins: ").append(this.gemToCoins);
		sb.append("]");
		return sb.toString();
	}
}
