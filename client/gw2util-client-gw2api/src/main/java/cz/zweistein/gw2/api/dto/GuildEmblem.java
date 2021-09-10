package cz.zweistein.gw2.api.dto;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.GuildEmblemFlag;


public class GuildEmblem {
	
	private Long backgroundId;
	
	private Long foregroundId;
	
	private List<GuildEmblemFlag> flags;
	
	private Long foregroundPrimaryColorId;
	
	private Long foregroundSecondaryColorId;
	
	private Long backgroundColorId;
	
	public GuildEmblem(Long backgroundId, Long foregroundId, List<GuildEmblemFlag> flags, Long foregroundPrimaryColorId, Long foregroundSecondaryColorId, Long backgroundColorId) {
		super();
		this.backgroundId = backgroundId;
		this.foregroundId = foregroundId;
		this.flags = flags;
		this.foregroundPrimaryColorId = foregroundPrimaryColorId;
		this.foregroundSecondaryColorId = foregroundSecondaryColorId;
		this.backgroundColorId = backgroundColorId;
	}

	public List<GuildEmblemFlag> getFlags() {
		return this.flags;
	}

	public Long getBackgroundId() {
		return this.backgroundId;
	}

	public Long getForegroundId() {
		return this.foregroundId;
	}

	public Long getForegroundPrimaryColorId() {
		return this.foregroundPrimaryColorId;
	}

	public Long getForegroundSecondaryColorId() {
		return this.foregroundSecondaryColorId;
	}

	public Long getBackgroundColorId() {
		return this.backgroundColorId;
	}

	@Override
	public String toString() {
		return "GuildEmblem [backgroundId=" + this.backgroundId + ", foregroundId=" + this.foregroundId + ", flags=" + this.flags
				+ ", foregroundPrimaryColorId=" + this.foregroundPrimaryColorId + ", foregroundSecondaryColorId=" + this.foregroundSecondaryColorId
				+ ", backgroundColorId=" + this.backgroundColorId + "]";
	}

}
