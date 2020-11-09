package de.morrigan.dev.gw2.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "UserGroupRightRelation")
public class UserGroupRightRelation implements IEntity {
	
	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@EmbeddedId
	private UserGroupRightRelationId id;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	/** Benutzergruppe die zu der Beziehung gehört */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userGroupId")
	private UserGroup userGroup;

	/** Recht das zu der Beziehung gehört */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("rightId")
	private Right right;

	/** Konstruktor für Hibernate */
	public UserGroupRightRelation() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupRightRelation other = (UserGroupRightRelation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public long getId() {
		return this.id.hashCode();
	}

	public Right getRight() {
		return this.right;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	@Override
	public void setId(final long id) {}

	public void setRight(final Right right) {
		this.right = right;
	}

	public void setUserGroup(final UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", userGroup id: ").append(this.userGroup == null ? "null" : this.userGroup.getId());
		sb.append(", right id: ").append(this.right == null ? "null" : this.right.getId());
		sb.append("]");
		return sb.toString();
	}
}
