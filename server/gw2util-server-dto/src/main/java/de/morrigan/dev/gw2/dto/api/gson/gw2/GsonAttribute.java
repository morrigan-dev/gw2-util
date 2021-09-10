package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonAttribute {

	@SerializedName("attribute")
	private String attribute;

	@SerializedName("modifier")
	private int modifier;

	public GsonAttribute(String attribute, int modifier) {
		super();
		this.attribute = attribute;
		this.modifier = modifier;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public int getModifier() {
		return this.modifier;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[attribute: ").append(this.attribute);
		sb.append(", modifier: ").append(this.modifier);
		sb.append("]");
		return sb.toString();
	}
}
