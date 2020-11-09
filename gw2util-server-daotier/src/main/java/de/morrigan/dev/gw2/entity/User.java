package de.morrigan.dev.gw2.entity;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Entity
@Table(name = "Users")
public class User implements IEntity, IActiveStateEntity {

	/** ID des System Admins */
	public static final long SYS_ADMIN_ID = -1;

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	/** Gibt den Aktivstatus des Datensatzes an */
	@Enumerated(EnumType.STRING)
	@Column(name = "ActiveState", length = 50, nullable = false)
	private ActiveState activeState;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	/** Vorname eines Benutzers */
	@Column(name = "FirstName", length = 255, nullable = false)
	private String firstName = "";

	/** Nachname eines Benutzers */
	@Column(name = "LastName", length = 255, nullable = false)
	private String lastName = "";

	/** Benutzername für die Anmeldung */
	@Column(name = "UserName", length = 255, nullable = false)
	private String userName = "";

	/** Password eines Benutzers */
	@Column(name = "Password", length = 255, nullable = false)
	private String password = "";

	/** Salt-Hash eines Benutzers */
	@Column(name = "SaltHash", length = 255, nullable = false)
	private String saltHash = "";

	/** Erstellungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	private Date createDate;

	/** Änderungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdateDate", nullable = false)
	private Date updateDate;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/** Benutzergruppe dem der User angehört */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserGroupID", nullable = false)
	private UserGroup userGroup;

	/** ClientData die zu dem User gehören */
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<ClientData> clientDataSet;

	/** Konstruktor für Hibernate */
	public User() {
		super();
	}

	public User(ActiveState activeState, String firstName, String lastName, String userName, String password,
			String saltHash, Date createDate, Date updateDate, UserGroup userGroup) {
		super();
		this.activeState = activeState;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.saltHash = saltHash;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.userGroup = userGroup;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public ActiveState getActiveState() {
		return this.activeState;
	}

	public Set<ClientData> getClientDataSet() {
		return this.clientDataSet;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getSaltHash() {
		return this.saltHash;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public void setActiveState(final ActiveState activeState) {
		this.activeState = activeState;
	}

	public void setClientDataSet(Set<ClientData> clientDataSet) {
		this.clientDataSet = clientDataSet;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setSaltHash(String saltHash) {
		this.saltHash = saltHash;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserGroup(final UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", firstName: ").append(this.firstName);
		sb.append(", lastName: ").append(this.lastName);
		sb.append(", userName: ").append(this.userName);
		sb.append(", userGroup id: ").append(this.userGroup == null ? "null" : this.userGroup.getId());
		sb.append(", clientDataSet size: ").append(this.clientDataSet == null ? "null" : this.clientDataSet.size());
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append("]");
		return sb.toString();
	}
}
