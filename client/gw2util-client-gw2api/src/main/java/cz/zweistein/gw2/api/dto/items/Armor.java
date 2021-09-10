package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.items.ItemType;
import cz.zweistein.gw2.api.dto.enums.items.WeightClass;

public class Armor {
	
	private ItemType armorType;
	
	private WeightClass weightClass;
	
	private Long defense;
	
	private List<InfusionSlot> infusionSlots;
	
	private InfixUpgrade infixUpgrade;

	public Armor(ItemType armorType, WeightClass weightClass, Long defense, List<InfusionSlot> infusionSlots, InfixUpgrade infixUpgrade) {
		super();
		this.armorType = armorType;
		this.weightClass = weightClass;
		this.defense = defense;
		this.infusionSlots = infusionSlots;
		this.infixUpgrade = infixUpgrade;
	}

	public ItemType getArmorType() {
		return armorType;
	}

	public WeightClass getWeightClass() {
		return weightClass;
	}

	public Long getDefense() {
		return defense;
	}

	public List<InfusionSlot> getInfusionSlots() {
		return infusionSlots;
	}

	public InfixUpgrade getInfixUpgrade() {
		return infixUpgrade;
	}

	@Override
	public String toString() {
		return "Armor [armorType=" + armorType + ", weightClass=" + weightClass + ", defense=" + defense + ", infusionSlots=" + infusionSlots + ", infixUpgrade=" + infixUpgrade + "]";
	} 

}
