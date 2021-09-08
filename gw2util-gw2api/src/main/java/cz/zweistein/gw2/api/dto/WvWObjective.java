package cz.zweistein.gw2.api.dto;

import cz.zweistein.gw2.api.dto.enums.WvWSide;

public class WvWObjective {

	private Long objectiveId;

	private String ownerGuild;

	private WvWSide owner;

	public WvWObjective(Long objectiveId, String ownerGuild, WvWSide owner) {
		super();
		this.objectiveId = objectiveId;
		this.ownerGuild = ownerGuild;
		this.owner = owner;
	}

	public Long getObjectiveId() {
		return this.objectiveId;
	}

	public String getOwnerGuild() {
		return this.ownerGuild;
	}

	public WvWSide getOwner() {
		return this.owner;
	}

	@Override
	public String toString() {
		return "WvWObjective [objectiveId=" + this.objectiveId + ", ownerGuild=" + this.ownerGuild + ", owner=" + this.owner + "]";
	}

}
