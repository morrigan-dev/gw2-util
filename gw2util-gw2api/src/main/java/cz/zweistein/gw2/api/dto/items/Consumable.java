package cz.zweistein.gw2.api.dto.items;

import cz.zweistein.gw2.api.dto.enums.items.ConsumableType;
import cz.zweistein.gw2.api.dto.enums.items.UnlockType;

public class Consumable {
	
	private ConsumableType type;

	private String description;
	
	private Long duration;
	
	private UnlockType unlockType;
	
	private Long colorId;
	
	private Long recipeId;

	public Consumable(ConsumableType type, String description, Long duration, UnlockType unlockType, Long colorId, Long recipeId) {
		super();
		this.type = type;
		this.description = description;
		this.duration = duration;
		this.unlockType = unlockType;
		this.colorId = colorId;
		this.recipeId = recipeId;
	}

	public UnlockType getUnlockType() {
		return this.unlockType;
	}

	public Long getColorId() {
		return this.colorId;
	}

	public Long getRecipeId() {
		return this.recipeId;
	}

	public String getDescription() {
		return description;
	}

	public Long getDuration() {
		return duration;
	}

	public ConsumableType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Consumable [type=" + this.type + ", description=" + this.description + ", duration=" + this.duration + ", unlockType=" + this.unlockType
				+ ", colorId=" + this.colorId + ", recipeId=" + this.recipeId + "]";
	}
	
}
