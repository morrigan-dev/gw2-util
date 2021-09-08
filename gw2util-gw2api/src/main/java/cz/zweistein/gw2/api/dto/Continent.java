package cz.zweistein.gw2.api.dto;

import java.util.List;

public class Continent {

	private String name;

	private Point2D size;

	private Long minZoom;

	private Long maxZoom;

	private List<Long> floors;

	public Continent(String name, Point2D size, Long minZoom, Long maxZoom, List<Long> floors) {
		super();
		this.name = name;
		this.size = size;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.floors = floors;
	}

	public String getName() {
		return this.name;
	}

	public Point2D getSize() {
		return this.size;
	}

	public Long getMinZoom() {
		return this.minZoom;
	}

	public Long getMaxZoom() {
		return this.maxZoom;
	}

	public List<Long> getFloors() {
		return this.floors;
	}

	@Override
	public String toString() {
		return "Continent [name=" + this.name + ", size=" + this.size + ", minZoom=" + this.minZoom + ", maxZoom=" + this.maxZoom + ", floors=" + this.floors
				+ "]";
	}

}
