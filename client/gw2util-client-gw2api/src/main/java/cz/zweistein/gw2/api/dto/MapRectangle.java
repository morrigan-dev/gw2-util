package cz.zweistein.gw2.api.dto;

public class MapRectangle {
	
	private Point2D topLeft;
	
	private Point2D rightBottom;

	public MapRectangle(Point2D topLeft, Point2D rightBottom) {
		super();
		this.topLeft = topLeft;
		this.rightBottom = rightBottom;
	}

	public Point2D getTopLeft() {
		return this.topLeft;
	}

	public Point2D getRightBottom() {
		return this.rightBottom;
	}

	@Override
	public String toString() {
		return "MapRectangle [topLeft=" + this.topLeft + ", rightBottom=" + this.rightBottom + "]";
	}
	
}
