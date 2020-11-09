package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonWeapon {

	@SerializedName("type")
	private String type;

	@SerializedName("damage_type")
	private String damageType;

	@SerializedName("min_power")
	private int minPower;

	@SerializedName("max_power")
	private int maxPower;

	@SerializedName("defense")
	private int defense;

	@SerializedName("infusion_slots")
	private GsonInfusionSlots[] infusionSlots;

	@SerializedName("infix_upgrade")
	private GsonInfixUpgrade infixUpgrade;

	@SerializedName("suffix_item_id")
	private String suffixItemId;

	@SerializedName("secondary_suffix_item_id")
	private String secondarySuffixItemId;

	public GsonWeapon(String type, String damageType, int minPower, int maxPower, int defense,
			GsonInfusionSlots[] infusionSlots, GsonInfixUpgrade infixUpgrade, String suffixItemId,
			String secondarySuffixItemId) {
		super();
		this.type = type;
		this.damageType = damageType;
		this.minPower = minPower;
		this.maxPower = maxPower;
		this.defense = defense;
		this.infusionSlots = infusionSlots;
		this.infixUpgrade = infixUpgrade;
		this.suffixItemId = suffixItemId;
		this.secondarySuffixItemId = secondarySuffixItemId;
	}

	public String getDamageType() {
		return this.damageType;
	}

	public int getDefense() {
		return this.defense;
	}

	public GsonInfixUpgrade getInfixUpgrade() {
		return this.infixUpgrade;
	}

	public GsonInfusionSlots[] getInfusionSlots() {
		return this.infusionSlots;
	}

	public int getMaxPower() {
		return this.maxPower;
	}

	public int getMinPower() {
		return this.minPower;
	}

	public String getSecondarySuffixItemId() {
		return this.secondarySuffixItemId;
	}

	public String getSuffixItemId() {
		return this.suffixItemId;
	}

	public String getType() {
		return this.type;
	}

	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setInfixUpgrade(GsonInfixUpgrade infixUpgrade) {
		this.infixUpgrade = infixUpgrade;
	}

	public void setInfusionSlots(GsonInfusionSlots[] infusionSlots) {
		this.infusionSlots = infusionSlots;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public void setMinPower(int minPower) {
		this.minPower = minPower;
	}

	public void setSecondarySuffixItemId(String secondarySuffixItemId) {
		this.secondarySuffixItemId = secondarySuffixItemId;
	}

	public void setSuffixItemId(String suffixItemId) {
		this.suffixItemId = suffixItemId;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[type: ").append(this.type);
		sb.append(", damageType: ").append(this.damageType);
		sb.append(", minPower: ").append(this.minPower);
		sb.append(", maxPower: ").append(this.maxPower);
		sb.append(", defense: ").append(this.defense);
		sb.append(", infusionSlots: ").append(this.infusionSlots);
		sb.append(", infixUpgrade: ").append(this.infixUpgrade);
		sb.append(", suffixItemId: ").append(this.suffixItemId);
		sb.append(", secondarySuffixItemId: ").append(this.secondarySuffixItemId);
		sb.append("]");
		return sb.toString();
	}
}
