package cz.zweistein.gw2.api.dto;

import java.util.Map;

public class MapRegion {

	private String name;

	private Point2D labelLocation;

	private Map<Long, MapAreaFloor> maps;

	public MapRegion(String name, Point2D labelLocation, Map<Long, MapAreaFloor> maps) {
		super();
		this.name = name;
		this.labelLocation = labelLocation;
		this.maps = maps;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point2D getLabelLocation() {
		return this.labelLocation;
	}

	public void setLabelLocation(Point2D labelLocation) {
		this.labelLocation = labelLocation;
	}

	public Map<Long, MapAreaFloor> getMaps() {
		return this.maps;
	}

	public void setMaps(Map<Long, MapAreaFloor> maps) {
		this.maps = maps;
	}

	@Override
	public String toString() {
		return "MapRegion [name=" + this.name + ", labelLocation=" + this.labelLocation + ", maps=" + this.maps + "]";
	}

}
