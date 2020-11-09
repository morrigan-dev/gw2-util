package de.morrigan.dev.gw2.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;

@Entity
@Table(name = "UserGroup")
public class UserGroup implements IEntity, IActiveStateEntity {

	public static final long DEFAULT_USER_GROUP_ID = -1;

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

	/** Name der Benutzergruppe */
	@Column(name = "Name", length = 255, nullable = false, unique = true)
	private String name = "";

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/** Liste der Besucher, die zu diesem Datensatz gehören */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroup")
	private Set<User> userSet = new HashSet<>();

	/** Liste der Rechte die zu der Gruppe gehören */
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "UserGroupRightRelation", joinColumns = @JoinColumn(name = "UserGroupID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "RightID", referencedColumnName = "ID"))
	private Set<Right> rightSet = new HashSet<>();

	/** Konstruktor für Hibernate */
	public UserGroup() {
		super();
	}
	
	public UserGroup(ActiveState activeState, String name) {
		super();
		this.activeState = activeState;
		this.name = name;
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
		final UserGroup other = (UserGroup) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public ActiveState getActiveState() {
		return this.activeState;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Set<Right> getRightSet() {
		return this.rightSet;
	}

	public Set<User> getUserSet() {
		return this.userSet;
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

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setRightSet(final Set<Right> rightSet) {
		this.rightSet = rightSet;
	}

	public void setUserSet(final Set<User> userSet) {
		this.userSet = userSet;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", name: ").append(this.name);
		sb.append(", userSet size: ").append(this.userSet == null ? "null" : this.userSet.size());
		sb.append(", rightSet size: ").append(this.rightSet == null ? "null" : this.rightSet.size());
		sb.append("]");
		return sb.toString();
	}
}
