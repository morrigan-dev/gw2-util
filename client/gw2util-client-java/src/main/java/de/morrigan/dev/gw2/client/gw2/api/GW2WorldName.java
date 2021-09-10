package de.morrigan.dev.gw2.client.gw2.api;

public class GW2WorldName {

	private final Long id;
	private final String name;

	public GW2WorldName(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", name: ").append(this.name);
		sb.append("]");
		return sb.toString();
	}
}
