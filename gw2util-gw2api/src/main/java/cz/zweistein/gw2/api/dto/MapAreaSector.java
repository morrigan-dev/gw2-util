package cz.zweistein.gw2.api.dto;

public class MapAreaSector {

	private Point2D location;

	private Long level;

	private String name;

	private Long id;

	public MapAreaSector(Point2D location, Long level, String name, Long id) {
		super();
		this.location = location;
		this.level = level;
		this.name = name;
		this.id = id;
	}

	public Point2D getLocation() {
		return this.location;
	}

	public Long getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MapAreaSector [location=" + this.location + ", level=" + this.level + ", name=" + this.name + ", id=" + this.id + "]";
	}

}
