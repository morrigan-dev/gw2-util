package de.morrigan.dev.gw2.dto.api.gson;

import com.google.gson.annotations.SerializedName;

public class GsonItemPrice {

	@SerializedName("sell")
	private int sell;

	@SerializedName("buy")
	private int buy;

	@SerializedName("updated")
	private long updated;

	public GsonItemPrice(int sell, int buy, long updated) {
		super();
		this.sell = sell;
		this.buy = buy;
		this.updated = updated;
	}

	public int getBuy() {
		return this.buy;
	}

	public int getSell() {
		return this.sell;
	}

	public long getUpdated() {
		return this.updated;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[sell: ").append(this.sell);
		sb.append(", buy: ").append(this.buy);
		sb.append(", updated: ").append(this.updated);
		sb.append("]");
		return sb.toString();
	}

}
