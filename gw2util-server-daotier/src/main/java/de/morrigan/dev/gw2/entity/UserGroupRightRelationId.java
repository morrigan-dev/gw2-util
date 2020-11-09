package de.morrigan.dev.gw2.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserGroupRightRelationId implements Serializable {
  
	private static final long serialVersionUID = 1L;

	@Column(name = "UserGroupID")
  private long userGroupId;

  @Column(name = "RightID")
  private long rightId;
  
  public UserGroupRightRelationId() {
  	super();
  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (rightId ^ (rightId >>> 32));
		result = prime * result + (int) (userGroupId ^ (userGroupId >>> 32));
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
		UserGroupRightRelationId other = (UserGroupRightRelationId) obj;
		if (rightId != other.rightId)
			return false;
		if (userGroupId != other.userGroupId)
			return false;
		return true;
	}

	public long getUserGroupId() {
		return userGroupId;
	}

	public long getRightId() {
		return rightId;
	}
}
