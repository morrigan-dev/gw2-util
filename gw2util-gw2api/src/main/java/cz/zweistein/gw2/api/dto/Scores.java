package cz.zweistein.gw2.api.dto;

public class Scores {

	private Long redScore;

	private Long greenScore;

	private Long blueScore;

	public Scores(Long redScore, Long greenScore, Long blueScore) {
		super();
		this.redScore = redScore;
		this.greenScore = greenScore;
		this.blueScore = blueScore;
	}

	public Long getRedScore() {
		return this.redScore;
	}

	public Long getGreenScore() {
		return this.greenScore;
	}

	public Long getBlueScore() {
		return this.blueScore;
	}

	@Override
	public String toString() {
		return "Scores [redScore=" + this.redScore + ", greenScore=" + this.greenScore + ", blueScore=" + this.blueScore + "]";
	}

}
