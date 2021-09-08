package cz.zweistein.gw2.api.dto;

import java.util.List;

public class AreaMap {
	
	private String continentName;
	
	private Long continentId;
	
	private String regionName;
	
	private Long regionId;
	
	private String mapName;
	
	private Long minLevel;
	
	private Long maxLevel;
	
	private MapRectangle mapRectangle;
	
	private MapRectangle continentRectangle;
	
	private List<Long> floors;
	
	private Long defaultFloor;

	public AreaMap(String continentName, Long continentId, String regionName, Long regionId, String mapName, Long minLevel, Long maxLevel,
			MapRectangle mapRectangle, MapRectangle continentRectangle, List<Long> floors, Long defaultFloor) {
		super();
		this.continentName = continentName;
		this.continentId = continentId;
		this.regionName = regionName;
		this.regionId = regionId;
		this.mapName = mapName;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.mapRectangle = mapRectangle;
		this.continentRectangle = continentRectangle;
		this.floors = floors;
		this.defaultFloor = defaultFloor;
	}

	public String getContinentName() {
		return this.continentName;
	}

	public Long getContinentId() {
		return this.continentId;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public Long getRegionId() {
		return this.regionId;
	}

	public String getMapName() {
		return this.mapName;
	}

	public Long getMinLevel() {
		return this.minLevel;
	}

	public Long getMaxLevel() {
		return this.maxLevel;
	}

	public MapRectangle getMapRectangle() {
		return this.mapRectangle;
	}

	public MapRectangle getContinentRectangle() {
		return this.continentRectangle;
	}

	public List<Long> getFloors() {
		return this.floors;
	}

	public Long getDefaultFloor() {
		return this.defaultFloor;
	}

	@Override
	public String toString() {
		return "AreaMap [continentName=" + this.continentName + ", continentId=" + this.continentId + ", regionName=" + this.regionName + ", regionId="
				+ this.regionId + ", mapName=" + this.mapName + ", minLevel=" + this.minLevel + ", maxLevel=" + this.maxLevel + ", mapRectangle="
				+ this.mapRectangle + ", continentRectangle=" + this.continentRectangle + ", floors=" + this.floors + ", defaultFloor=" + this.defaultFloor
				+ "]";
	}

}
