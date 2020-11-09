package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonRecipesDetails {

	@SerializedName("recipe_id")
	private long recipeId;

	@SerializedName("type")
	private String type;

	@SerializedName("output_item_id")
	private long outputItemId;

	@SerializedName("output_item_count")
	private long outputItemCount;

	@SerializedName("min_rating")
	private long minRating;

	@SerializedName("time_to_craft_ms")
	private long timeToCraftMS;

	@SerializedName("disciplines")
	private String[] disciplines;

	@SerializedName("flags")
	private String[] flags;

	@SerializedName("ingredients")
	private GsonIngredients[] ingredients;

	public GsonRecipesDetails(long recipeId, String type, long outputItemId, long outputItemCount, long minRating,
			long timeToCraftMS, String[] disciplines, String[] flags, GsonIngredients[] ingredients) {
		super();
		this.recipeId = recipeId;
		this.type = type;
		this.outputItemId = outputItemId;
		this.outputItemCount = outputItemCount;
		this.minRating = minRating;
		this.timeToCraftMS = timeToCraftMS;
		this.disciplines = disciplines;
		this.flags = flags;
		this.ingredients = ingredients;
	}

	public String[] getDisciplines() {
		return this.disciplines;
	}

	public String[] getFlags() {
		return this.flags;
	}

	public GsonIngredients[] getIngredients() {
		return this.ingredients;
	}

	public long getMinRating() {
		return this.minRating;
	}

	public long getOutputItemCount() {
		return this.outputItemCount;
	}

	public long getOutputItemId() {
		return this.outputItemId;
	}

	public long getRecipeId() {
		return this.recipeId;
	}

	public long getTimeToCraftMS() {
		return this.timeToCraftMS;
	}

	public String getType() {
		return this.type;
	}

	public void setDisciplines(String[] disciplines) {
		this.disciplines = disciplines;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	public void setIngredients(GsonIngredients[] ingredients) {
		this.ingredients = ingredients;
	}

	public void setMinRating(long minRating) {
		this.minRating = minRating;
	}

	public void setOutputItemCount(long outputItemCount) {
		this.outputItemCount = outputItemCount;
	}

	public void setOutputItemId(long outputItemId) {
		this.outputItemId = outputItemId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public void setTimeToCraftMS(long timeToCraftMS) {
		this.timeToCraftMS = timeToCraftMS;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[recipeId: ").append(this.recipeId);
		sb.append(", type: ").append(this.type);
		sb.append(", outputItemId ").append(this.outputItemId);
		sb.append(", outputItemCount: ").append(this.outputItemCount);
		sb.append(", minRating: ").append(this.minRating);
		sb.append(", timeToCraftMS: ").append(this.timeToCraftMS);
		sb.append(", disciplines: ").append(this.disciplines);
		sb.append(", flags: ").append(this.flags);
		sb.append(", ingredients: ").append(this.ingredients);
		sb.append("]");
		return sb.toString();
	}
}
