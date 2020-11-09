package de.morrigan.dev.gw2.dto.api.gson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GsonItem {

	@SerializedName("ID")
	private long id;

	@SerializedName("ExternalID")
	private long externalId;

	@SerializedName("DataID")
	private long dataId;

	@SerializedName("Icon")
	private String icon;

	@SerializedName("Name")
	private String name;

	@SerializedName("Rarity")
	private int rarity;

	@SerializedName("Value")
	private int value;

	@SerializedName("Defense")
	private int defense;

	@SerializedName("MinPower")
	private int minPower;

	@SerializedName("MaxPower")
	private int maxPower;

	@SerializedName("Type")
	private int type;

	@SerializedName("Description")
	private String description;

	@SerializedName("Level")
	private int level;

	@SerializedName("RequiredLevel")
	private int requiredLevel;

	@SerializedName("ArmorType")
	private int armorType;

	@SerializedName("ArmorWeightType")
	private int armorWeightType;

	@SerializedName("Stats")
	private List<GsonStats> stats;

	@SerializedName("SoldBy")
	private List<Object> soldBy;

	public GsonItem(int armorType, int armorWeightType, long dataId, int defense, String description, long externalId,
			String icon, long id, int level, int maxPower, int minPower, String name, int rarity, int requiredLevel,
			List<Object> soldBy, List<GsonStats> stats, int type, int value) {
		super();
		this.armorType = armorType;
		this.armorWeightType = armorWeightType;
		this.dataId = dataId;
		this.defense = defense;
		this.description = description;
		this.externalId = externalId;
		this.icon = icon;
		this.id = id;
		this.level = level;
		this.maxPower = maxPower;
		this.minPower = minPower;
		this.name = name;
		this.rarity = rarity;
		this.requiredLevel = requiredLevel;
		this.soldBy = soldBy;
		this.stats = stats;
		this.type = type;
		this.value = value;
	}

	public int getArmorType() {
		return this.armorType;
	}

	public int getArmorWeightType() {
		return this.armorWeightType;
	}

	public long getDataId() {
		return this.dataId;
	}

	public int getDefense() {
		return this.defense;
	}

	public String getDescription() {
		return this.description;
	}

	public long getExternalId() {
		return this.externalId;
	}

	public String getIcon() {
		return this.icon;
	}

	public long getId() {
		return this.id;
	}

	public int getLevel() {
		return this.level;
	}

	public int getMaxPower() {
		return this.maxPower;
	}

	public int getMinPower() {
		return this.minPower;
	}

	public String getName() {
		return this.name;
	}

	public int getRarity() {
		return this.rarity;
	}

	public int getRequiredLevel() {
		return this.requiredLevel;
	}

	public List<Object> getSoldBy() {
		return this.soldBy;
	}

	public List<GsonStats> getStats() {
		return this.stats;
	}

	public int getType() {
		return this.type;
	}

	public int getValue() {
		return this.value;
	}

	public void setArmorType(int armorType) {
		this.armorType = armorType;
	}

	public void setArmorWeightType(int armorWeightType) {
		this.armorWeightType = armorWeightType;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExternalId(long externalId) {
		this.externalId = externalId;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public void setMinPower(int minPower) {
		this.minPower = minPower;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public void setSoldBy(List<Object> soldBy) {
		this.soldBy = soldBy;
	}

	public void setStats(List<GsonStats> stats) {
		this.stats = stats;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", externalId: ").append(this.externalId);
		sb.append(", dataId: ").append(this.dataId);
		sb.append(", icon: ").append(this.icon);
		sb.append(", name: ").append(this.name);
		sb.append(", rarity: ").append(this.rarity);
		sb.append(", stats: ").append(this.stats);
		sb.append(", soldBy: ").append(this.soldBy);
		sb.append("]");
		return sb.toString();
	}
}
