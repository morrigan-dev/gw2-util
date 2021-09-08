package cz.zweistein.gw2.api.dto;

public class Ingredient {

	private Long count;

	private Long itemId;

	public Ingredient(Long count, Long itemId) {
		super();
		this.count = count;
		this.itemId = itemId;
	}

	public Long getCount() {
		return this.count;
	}

	public Long getItemId() {
		return this.itemId;
	}

	@Override
	public String toString() {
		return "Ingredient [count=" + this.count + ", itemId=" + this.itemId + "]";
	}

}
