package cz.zweistein.gw2.api.dto;

import java.util.Date;

public class WvWMatch {
	
	private String matchId;

	private Long blueWorldId;
	
	private Long redWorldId;
	
	private Long greenWorldId;
	
	private Date startTime;
	
	private Date endTime;

	public WvWMatch(String matchId, Long blueWorldId, Long redWorldId, Long greenWorldId, Date startTime, Date endTime) {
		super();
		this.matchId = matchId;
		this.blueWorldId = blueWorldId;
		this.redWorldId = redWorldId;
		this.greenWorldId = greenWorldId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public Long getBlueWorldId() {
		return this.blueWorldId;
	}

	public Long getRedWorldId() {
		return this.redWorldId;
	}

	public Long getGreenWorldId() {
		return this.greenWorldId;
	}

	public String getMatchId() {
		return this.matchId;
	}

	@Override
	public String toString() {
		return "WvWMatch [matchId=" + this.matchId + ", blueWorldId=" + this.blueWorldId + ", redWorldId=" + this.redWorldId + ", greenWorldId="
				+ this.greenWorldId + ", startTime=" + this.startTime + ", endTime=" + this.endTime + "]";
	}
	
}

