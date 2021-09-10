package cz.zweistein.gw2.api.dto;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.LocationType;

public class EventLocation {

	private Point3D center;
	
	private Double rotation;
	
	private Double radius;
	
	private Double height;
	
	private ZRange zRange;
	
	private List<Point2D> points;

	private LocationType type;

	public EventLocation(Point3D center, Double rotation, Double radius, Double height, ZRange zRange, List<Point2D> points, LocationType type) {
		super();
		this.center = center;
		this.rotation = rotation;
		this.radius = radius;
		this.height = height;
		this.zRange = zRange;
		this.points = points;
		this.type = type;
	}

	public ZRange getzRange() {
		return this.zRange;
	}

	public List<Point2D> getPoints() {
		return this.points;
	}
	
	
	public Double getHeight() {
		return this.height;
	}

	public Point3D getCenter() {
		return this.center;
	}

	public Double getRotation() {
		return this.rotation;
	}

	public Double getRadius() {
		return this.radius;
	}

	public LocationType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "EventLocation [center=" + this.center + ", rotation=" + this.rotation + ", radius=" + this.radius + ", height=" + this.height + ", zRange="
				+ this.zRange + ", points=" + this.points + ", type=" + this.type + "]";
	}
	
}
