package cz.zweistein.gw2.api.dto;

public class Point2D {

	private Double x;

	private Double y;

	public Point2D(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return this.x;
	}

	public Double getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "Point3D [x=" + this.x + ", y=" + this.y + "]";
	}

}
