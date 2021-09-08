package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.items.DamageType;
import cz.zweistein.gw2.api.dto.enums.items.ItemType;

public class Weapon {
	
	private ItemType weaponType;
	
	private DamageType damageType;
	
	private Long minPower;
	
	private Long maxPower;
	
	private Long defense;
	
	private List<InfusionSlot> infusionSlots;
	
	private InfixUpgrade infixUpgrade;

	public Weapon(ItemType weaponType, DamageType damageType, Long minPower, Long maxPower, Long defense, List<InfusionSlot> infusionSlots, InfixUpgrade infixUpgrade) {
		super();
		this.weaponType = weaponType;
		this.damageType = damageType;
		this.minPower = minPower;
		this.maxPower = maxPower;
		this.defense = defense;
		this.infusionSlots = infusionSlots;
		this.infixUpgrade = infixUpgrade;
	}

	public ItemType getWeaponType() {
		return weaponType;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public Long getMinPower() {
		return minPower;
	}

	public Long getMaxPower() {
		return maxPower;
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
		return "Weapon [weaponType=" + weaponType + ", damageType=" + damageType + ", minPower=" + minPower + ", maxPower=" + maxPower + ", defense=" + defense + ", infusionSlots=" + infusionSlots + ", infixUpgrade=" + infixUpgrade + "]";
	}

}
