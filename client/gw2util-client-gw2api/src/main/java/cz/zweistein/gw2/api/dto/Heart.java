package cz.zweistein.gw2.api.dto;

public class Heart {
	
	private Point2D location;
	
	private Long level;
	
	private String objective;
	
	private Long id;

	public Heart(Point2D location, Long level, String objective, Long id) {
		super();
		this.location = location;
		this.level = level;
		this.objective = objective;
		this.id = id;
	}

	public Point2D getLocation() {
		return this.location;
	}

	public Long getLevel() {
		return this.level;
	}

	public String getObjective() {
		return this.objective;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Heart [location=" + this.location + ", level=" + this.level + ", objective=" + this.objective + ", id=" + this.id + "]";
	}

}
