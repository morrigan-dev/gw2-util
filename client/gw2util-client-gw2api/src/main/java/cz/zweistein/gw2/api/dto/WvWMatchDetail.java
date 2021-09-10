package cz.zweistein.gw2.api.dto;

import java.util.List;

public class WvWMatchDetail {

	private Scores scores;

	private String matchId;

	private List<WvWMap> maps;

	public WvWMatchDetail(Scores scores, String matchId, List<WvWMap> maps) {
		super();
		this.scores = scores;
		this.matchId = matchId;
		this.maps = maps;
	}

	public Scores getScores() {
		return this.scores;
	}

	public String getMatchId() {
		return this.matchId;
	}

	public List<WvWMap> getMaps() {
		return this.maps;
	}

	@Override
	public String toString() {
		return "WvWMatchDetail [scores=" + this.scores + ", matchId=" + this.matchId + ", maps=" + this.maps + "]";
	}

}
