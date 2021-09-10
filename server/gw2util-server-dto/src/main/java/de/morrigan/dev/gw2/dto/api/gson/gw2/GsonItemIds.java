package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

/**
 * Kapselt das Ergebnis des GW2 API Endpunktes, der alle Item IDs zurückgibt.<br>
 * - /v1/items.json
 * 
 * @author morrigan
 */
public class GsonItemIds {

	/** Array mit sämtlichen Item IDs */
	@SerializedName("items")
	private long[] ids;

	public GsonItemIds(long[] ids) {
		super();
		this.ids = ids;
	}

	public long[] getIds() {
		return this.ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[ids length: ").append(this.ids.length);
		sb.append("]");
		return sb.toString();
	}
}
