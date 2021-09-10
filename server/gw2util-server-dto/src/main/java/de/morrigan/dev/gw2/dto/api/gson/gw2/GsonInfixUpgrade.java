package de.morrigan.dev.gw2.dto.api.gson.gw2;

public class GsonInfixUpgrade {

	private GsonAttribute[] attributes;

	public GsonInfixUpgrade(GsonAttribute[] attributes) {
		super();
		this.attributes = attributes;
	}

	public GsonAttribute[] getAttributes() {
		return this.attributes;
	}

	public void setAttributes(GsonAttribute[] attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[attributes: ").append(this.attributes);
		sb.append("]");
		return sb.toString();
	}
}
