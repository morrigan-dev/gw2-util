package de.morrigan.dev.gw2.dto.api.gson;

import com.google.gson.annotations.SerializedName;

public class GsonStats {

	@SerializedName("Type")
	private int type;

	@SerializedName("Value")
	private double value;

	public GsonStats(int type, double value) {
		super();
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return this.type;
	}

	public double getValue() {
		return this.value;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[type: ").append(this.type);
		sb.append(", soldBy: ").append(this.value);
		sb.append("]");
		return sb.toString();
	}
}
