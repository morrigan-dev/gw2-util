package cz.zweistein.gw2.api.dto;

import java.util.Map;

public class MapFloor {

	private Point2D textureDimensions;

	private Map<Long, MapRegion> regions;

	public MapFloor(Point2D textureDimensions, Map<Long, MapRegion> regions) {
		super();
		this.textureDimensions = textureDimensions;
		this.regions = regions;
	}

	public Point2D getTextureDimensions() {
		return this.textureDimensions;
	}

	public Map<Long, MapRegion> getRegions() {
		return this.regions;
	}

	@Override
	public String toString() {
		return "MapFloor [textureDimensions=" + this.textureDimensions + ", regions=" + this.regions + "]";
	}

}
