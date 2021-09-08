package cz.zweistein.gw2.api.dto;

public class SkillChallenge {

	private Point2D location;

	public SkillChallenge(Point2D location) {
		super();
		this.location = location;
	}

	public Point2D getLocation() {
		return this.location;
	}

	@Override
	public String toString() {
		return "SkillChallenge [location=" + this.location + "]";
	}
	
	
	
}
