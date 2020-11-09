package de.morrigan.dev.gw2.dto.admin;

import java.io.Serializable;

import de.morrigan.dev.gw2.utils.enums.ActiveState;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private ActiveState activeState;
	private String userName;

	public UserDTO(long id, ActiveState activeState, String userName) {
		super();
		this.id = id;
		this.activeState = activeState;
		this.userName = userName;
	}

	public ActiveState getActiveState() {
		return this.activeState;
	}

	public long getId() {
		return this.id;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", activeState: ").append(this.activeState);
		sb.append(", userName: ").append(this.userName);
		sb.append("]");
		return sb.toString();
	}
}
