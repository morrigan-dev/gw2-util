package de.morrigan.dev.gw2.client.gw2.api;

import cz.zweistein.gw2.api.dto.Continent;

public class GW2Continent {

	private final Long id;
	private final Continent continent;

	public GW2Continent(Long id, Continent continent) {
		super();
		this.id = id;
		this.continent = continent;
	}

	public Continent getContinent() {
		return this.continent;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", continent: ").append(this.continent);
		sb.append("]");
		return sb.toString();
	}
}
