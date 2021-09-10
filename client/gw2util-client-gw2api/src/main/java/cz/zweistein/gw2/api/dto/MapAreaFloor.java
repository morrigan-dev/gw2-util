package cz.zweistein.gw2.api.dto;

import java.util.List;

public class MapAreaFloor {
	
	private String name;

	private Long minLevel;
	
	private Long maxLevel;
	
	private MapRectangle mapRectangle;
	
	private MapRectangle continentRectangle;
	
	private Long defaultFloor;
	
	private List<SkillChallenge> skillChallenges;
	
	private List<PointOfInterest> pois;
	
	private List<Heart> tasks;
	
	private List<MapAreaSector> sectors;

	public MapAreaFloor(String name, Long minLevel, Long maxLevel, MapRectangle mapRectangle, MapRectangle continentRectangle, Long defaultFloor,
			List<SkillChallenge> skillChallenges, List<PointOfInterest> pois, List<Heart> tasks, List<MapAreaSector> sectors) {
		super();
		this.name = name;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.mapRectangle = mapRectangle;
		this.continentRectangle = continentRectangle;
		this.defaultFloor = defaultFloor;
		this.skillChallenges = skillChallenges;
		this.pois = pois;
		this.tasks = tasks;
		this.sectors = sectors;
	}

	public String getName() {
		return this.name;
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

	public Long getDefaultFloor() {
		return this.defaultFloor;
	}

	public List<SkillChallenge> getSkillChallenges() {
		return this.skillChallenges;
	}

	public List<PointOfInterest> getPois() {
		return this.pois;
	}

	public List<Heart> getTasks() {
		return this.tasks;
	}

	public List<MapAreaSector> getSectors() {
		return this.sectors;
	}

	@Override
	public String toString() {
		return "MapAreaFloor [name=" + this.name + ", minLevel=" + this.minLevel + ", maxLevel=" + this.maxLevel + ", mapRectangle=" + this.mapRectangle
				+ ", continentRectangle=" + this.continentRectangle + ", defaultFloor=" + this.defaultFloor + ", skillChallenges=" + this.skillChallenges
				+ ", pois=" + this.pois + ", tasks=" + this.tasks + ", sectors=" + this.sectors + "]";
	}
	
}
