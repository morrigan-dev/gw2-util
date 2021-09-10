package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonInfusionSlots {

	@SerializedName("flags")
	private String[] flags;

	public GsonInfusionSlots(String[] flags) {
		super();
		this.flags = flags;
	}

	public String[] getFlags() {
		return this.flags;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[flags: ").append(this.flags);
		sb.append("]");
		return sb.toString();
	}
}
