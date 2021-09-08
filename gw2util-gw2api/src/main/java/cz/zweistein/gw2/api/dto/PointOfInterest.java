package cz.zweistein.gw2.api.dto;

import cz.zweistein.gw2.api.dto.enums.PoIType;

public class PointOfInterest {
	
	private Point2D location;
	
	private String name;
	
	private Long floor;
	
	private Long id;
	
	private PoIType type;

	public PointOfInterest(Point2D location, String name, Long floor, Long id, PoIType type) {
		super();
		this.location = location;
		this.name = name;
		this.floor = floor;
		this.id = id;
		this.type = type;
	}

	public Point2D getLocation() {
		return this.location;
	}

	public String getName() {
		return this.name;
	}

	public Long getFloor() {
		return this.floor;
	}

	public Long getId() {
		return this.id;
	}

	public PoIType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "PointOfInterest [location=" + this.location + ", name=" + this.name + ", floor=" + this.floor + ", id=" + this.id + ", type=" + this.type + "]";
	}

}
