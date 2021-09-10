package de.morrigan.dev.gw2.client.gw2.api;

import cz.zweistein.gw2.api.dto.Color;

public class GW2Color {

	private final Long id;
	private final Color color;

	public GW2Color(Long id, Color color) {
		super();
		this.id = id;
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", color: ").append(this.color);
		sb.append("]");
		return sb.toString();
	}
}
