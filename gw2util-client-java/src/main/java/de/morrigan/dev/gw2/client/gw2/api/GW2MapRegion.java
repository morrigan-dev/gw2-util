package de.morrigan.dev.gw2.client.gw2.api;

import cz.zweistein.gw2.api.dto.MapRegion;

public class GW2MapRegion {

	private final Long id;
	private final MapRegion mapRegion;

	public GW2MapRegion(Long id, MapRegion mapRegion) {
		super();
		this.id = id;
		this.mapRegion = mapRegion;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GW2MapRegion other = (GW2MapRegion) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return this.id;
	}

	public MapRegion getMapRegion() {
		return this.mapRegion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", mapRegion: ").append(this.mapRegion);
		sb.append("]");
		return sb.toString();
	}
}
