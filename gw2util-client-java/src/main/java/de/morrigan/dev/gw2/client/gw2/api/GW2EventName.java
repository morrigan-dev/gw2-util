package de.morrigan.dev.gw2.client.gw2.api;

public class GW2EventName {

	private final String id;
	private final String name;

	public GW2EventName(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
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
