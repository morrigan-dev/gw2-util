package de.morrigan.dev.gw2.dto.api.gson;

import com.google.gson.annotations.SerializedName;

public class GsonTraits {

	@SerializedName("ID")
	private long id;

	@SerializedName("DataID")
	private long dataID;

	@SerializedName("Name")
	private String name;

	@SerializedName("Description")
	private String description;

	@SerializedName("Tier")
	private int tier;

	@SerializedName("Class")
	private int classification;

	@SerializedName("Type")
	private int type;

	@SerializedName("Line")
	private int line;

	@SerializedName("Index")
	private int index;

	public GsonTraits(long id, long dataID, String name, String description, int tier, int classification, int type,
			int line, int index) {
		super();
		this.classification = classification;
		this.dataID = dataID;
		this.description = description;
		this.id = id;
		this.index = index;
		this.line = line;
		this.name = name;
		this.tier = tier;
		this.type = type;
	}

	public int getClassification() {
		return this.classification;
	}

	public long getDataID() {
		return this.dataID;
	}

	public String getDescription() {
		return this.description;
	}

	public long getId() {
		return this.id;
	}

	public int getIndex() {
		return this.index;
	}

	public int getLine() {
		return this.line;
	}

	public String getName() {
		return this.name;
	}

	public int getTier() {
		return this.tier;
	}

	public int getType() {
		return this.type;
	}

	public void setClassification(int classification) {
		this.classification = classification;
	}

	public void setDataID(long dataID) {
		this.dataID = dataID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", dataID: ").append(this.dataID);
		sb.append(", name: ").append(this.name);
		sb.append(", description: ").append(this.description);
		sb.append(", tier: ").append(this.tier);
		sb.append(", classification: ").append(this.classification);
		sb.append(", type: ").append(this.type);
		sb.append(", line: ").append(this.line);
		sb.append(", index: ").append(this.index);
		sb.append("]");
		return sb.toString();
	}
}
