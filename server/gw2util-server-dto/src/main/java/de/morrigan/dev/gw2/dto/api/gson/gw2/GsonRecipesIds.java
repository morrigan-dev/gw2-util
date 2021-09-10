package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

/**
 * Kapselt das Ergebnis des GW2 API Endpunktes, der alle Rezepte IDs zurückgibt.<br>
 * - https://api.guildwars2.com/v1/recipes.json
 * 
 * @author morrigan
 */
public class GsonRecipesIds {

	/** Array mit sämtlichen Rezepte IDs */
	@SerializedName("recipes")
	private long[] ids;

	public GsonRecipesIds(long[] ids) {
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
