package cz.zweistein.gw2.api.dto;

public class Guild {
	
	private String guildId;
	
	private String tag;
	
	private String guildName;
	
	private GuildEmblem emblem;
	
	public Guild(String guildId, String tag, String guildName, GuildEmblem emblem) {
		super();
		this.guildId = guildId;
		this.tag = tag;
		this.guildName = guildName;
		this.emblem = emblem;
	}
	
	public String getGuildId() {
		return this.guildId;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public String getGuildName() {
		return this.guildName;
	}
	
	public GuildEmblem getEmblem() {
		return this.emblem;
	}
	
	@Override
	public String toString() {
		return "Guild [guildId=" + this.guildId + ", tag=" + this.tag + ", guildName=" + this.guildName + ", emblem=" + this.emblem + "]";
	}

}
