package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonIngredients {

	@SerializedName("item_id")
	private long itemId;

	@SerializedName("count")
	private long count;

	public GsonIngredients(long itemId, long count) {
		super();
		this.itemId = itemId;
		this.count = count;
	}

	public long getCount() {
		return this.count;
	}

	public long getItemId() {
		return this.itemId;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[itemId: ").append(this.itemId);
		sb.append(", count: ").append(this.count);
		sb.append("]");
		return sb.toString();
	}
}
