package cz.zweistein.gw2.api.dto;

public class Point3D {
	
	private Double x;
	
	private Double y;
	
	private Double z;

	public Point3D(Double x, Double y, Double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Double getX() {
		return this.x;
	}

	public Double getY() {
		return this.y;
	}

	public Double getZ() {
		return this.z;
	}

	@Override
	public String toString() {
		return "Point3D [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}
	
}
