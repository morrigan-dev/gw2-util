package de.morrigan.dev.gw2.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.morrigan.dev.gw2.utils.enums.ActiveState;

public class UserDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private ActiveState activeState;
	private String firstName;
	private String lastName;
	private String userName;
	private Date createDate;
	private Date updateDate;
	private UserGroupDTO userGroup;
	private List<ClientDataDTO> clientData;

	public UserDetailDTO(long id, ActiveState activeState, String firstName, String lastName, String userName,
			Date createDate, Date updateDate, UserGroupDTO userGroup, List<ClientDataDTO> clientData) {
		super();
		this.id = id;
		this.activeState = activeState;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.userGroup = userGroup;
		this.clientData = clientData;
	}

	public ActiveState getActiveState() {
		return this.activeState;
	}

	public List<ClientDataDTO> getClientData() {
		return this.clientData;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public long getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public UserGroupDTO getUserGroup() {
		return this.userGroup;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setActiveState(ActiveState activeState) {
		this.activeState = activeState;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserGroup(UserGroupDTO userGroup) {
		this.userGroup = userGroup;
	}

	public void setUserName(String userName) {
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
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", userGroup: ").append(this.userGroup);
		sb.append(", clientData size: ").append(this.clientData == null ? "null" : this.clientData.size());
		sb.append("]");
		return sb.toString();
	}

}
