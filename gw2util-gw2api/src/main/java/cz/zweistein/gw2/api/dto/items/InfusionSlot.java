package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.items.InfusionSlotFlag;

public class InfusionSlot {
	
	private List<InfusionSlotFlag> flag;
	
	private Long itemId;

	public InfusionSlot(List<InfusionSlotFlag> flag, Long itemId) {
		super();
		this.flag = flag;
		this.itemId = itemId;
	}

	public List<InfusionSlotFlag> getFlag() {
		return flag;
	}

	public Long getItemId() {
		return itemId;
	}

	@Override
	public String toString() {
		return "InfusionSlot [flag=" + flag + ", item=" + itemId + "]";
	}

}