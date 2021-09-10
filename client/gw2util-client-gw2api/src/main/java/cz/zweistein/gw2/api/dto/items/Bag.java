package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.items.BagModifier;

public class Bag {
	
	private Long size;
	
	private List<BagModifier> bagModifiers;
	
	public Bag(Long size, List<BagModifier> bagModifiers) {
		super();
		this.size = size;
		this.bagModifiers = bagModifiers;
	}

	public Long getSize() {
		return size;
	}

	public List<BagModifier> getBagModifiers() {
		return bagModifiers;
	}

	@Override
	public String toString() {
		return "Bag [size=" + size + ", bagModifier=" + bagModifiers + "]";
	}

}
