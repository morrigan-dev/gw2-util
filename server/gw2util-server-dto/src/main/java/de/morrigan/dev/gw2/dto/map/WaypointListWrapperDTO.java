package de.morrigan.dev.gw2.dto.map;

import java.util.ArrayList;
import java.util.List;

import de.morrigan.dev.gw2.dto.ResultDTO;

public class WaypointListWrapperDTO extends ResultDTO {

	private static final long serialVersionUID = -495346186314065709L;

	private List<WaypointDTO> resourceWPs = new ArrayList<>();
	private List<WaypointDTO> otherWPs = new ArrayList<>();

	public WaypointListWrapperDTO() {
		super();
	}

	public WaypointListWrapperDTO(List<WaypointDTO> resourceWPs, List<WaypointDTO> otherWPs) {
		super();
		this.resourceWPs = resourceWPs;
		this.otherWPs = otherWPs;
	}

	public List<WaypointDTO> getOtherWPs() {
		return this.otherWPs;
	}

	public List<WaypointDTO> getResourceWPs() {
		return this.resourceWPs;
	}

	public void setOtherWPs(List<WaypointDTO> otherWPs) {
		this.otherWPs = otherWPs;
	}

	public void setResourceWPs(List<WaypointDTO> resourceWPs) {
		this.resourceWPs = resourceWPs;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[resourceWPs size: ").append(this.resourceWPs.size());
		sb.append(", otherWPs size: ").append(this.otherWPs.size());
		sb.append("]");
		return sb.toString();
	}
}
