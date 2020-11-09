package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

public class GsonItemDetails {

	@SerializedName("item_id")
	private long itemId;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("type")
	private String type;

	@SerializedName("level")
	private int level;

	@SerializedName("rarity")
	private String rarity;

	@SerializedName("vendor_value")
	private long vendorValue;

	@SerializedName("icon_file_id")
	private long iconFileId;

	@SerializedName("icon_file_signature")
	private String iconFileSignature;

	@SerializedName("default_skin")
	private long defaultSkin;

	@SerializedName("game_types")
	private String[] gameTypes;

	@SerializedName("flags")
	private String[] flags;

	@SerializedName("restrictions")
	private String[] restrictions;

	@SerializedName("weapon")
	private GsonWeapon weapon;

	public GsonItemDetails(long itemId, String name, String description, String type, int level, String rarity,
			long vendorValue, long iconFileId, String iconFileSignature, long defaultSkin, String[] gameTypes,
			String[] flags, String[] restrictions, GsonWeapon weapon) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.level = level;
		this.rarity = rarity;
		this.vendorValue = vendorValue;
		this.iconFileId = iconFileId;
		this.iconFileSignature = iconFileSignature;
		this.defaultSkin = defaultSkin;
		this.gameTypes = gameTypes;
		this.flags = flags;
		this.restrictions = restrictions;
		this.weapon = weapon;
	}

	public long getDefaultSkin() {
		return this.defaultSkin;
	}

	public String getDescription() {
		return this.description;
	}

	public String[] getFlags() {
		return this.flags;
	}

	public String[] getGameTypes() {
		return this.gameTypes;
	}

	public long getIconFileId() {
		return this.iconFileId;
	}

	public String getIconFileSignature() {
		return this.iconFileSignature;
	}

	public long getItemId() {
		return this.itemId;
	}

	public int getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public String getRarity() {
		return this.rarity;
	}

	public String[] getRestrictions() {
		return this.restrictions;
	}

	public String getType() {
		return this.type;
	}

	public long getVendorValue() {
		return this.vendorValue;
	}

	public GsonWeapon getWeapon() {
		return this.weapon;
	}

	public void setDefaultSkin(long defaultSkin) {
		this.defaultSkin = defaultSkin;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	public void setGameTypes(String[] gameTypes) {
		this.gameTypes = gameTypes;
	}

	public void setIconFileId(long iconFileId) {
		this.iconFileId = iconFileId;
	}

	public void setIconFileSignature(String iconFileSignature) {
		this.iconFileSignature = iconFileSignature;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public void setRestrictions(String[] restrictions) {
		this.restrictions = restrictions;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVendorValue(long vendorValue) {
		this.vendorValue = vendorValue;
	}

	public void setWeapon(GsonWeapon weapon) {
		this.weapon = weapon;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[itemId: ").append(this.itemId);
		sb.append(", name: ").append(this.name);
		sb.append(", description: ").append(this.description);
		sb.append(", type: ").append(this.type);
		sb.append(", level: ").append(this.level);
		sb.append(", rarity: ").append(this.rarity);
		sb.append(", vendorValue: ").append(this.vendorValue);
		sb.append(", iconFileId: ").append(this.iconFileId);
		sb.append(", iconFileSignature: ").append(this.iconFileSignature);
		sb.append(", defaultSkin: ").append(this.defaultSkin);
		sb.append(", gameTypes: ").append(this.gameTypes);
		sb.append(", flags: ").append(this.flags);
		sb.append(", restrictions: ").append(this.restrictions);
		sb.append(", weapon: ").append(this.weapon);
		sb.append("]");
		return sb.toString();
	}

}
