package de.morrigan.dev.gw2.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.morrigan.dev.gw2.dto.common.enums.ItemRarity;
import de.morrigan.dev.gw2.dto.common.enums.ItemType;
import de.morrigan.dev.gw2.dto.common.enums.Language;
import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "Item")
public class Item implements IEntity, IAuditEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	/** Erstellungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreateDate", nullable = false)
	private Date createDate;

	/** Erstellungsbenutzer des Datensatzes */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CreateUser", nullable = false)
	private User createUser;

	/** Änderungsdatum des Datensatzes */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UpdateDate", nullable = false)
	private Date updateDate;

	/** Änderungsbenutzer des Datensatzes */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UpdateUser", nullable = false)
	private User updateUser;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	@Column(name = "ExternalID", nullable = false)
	private long externalId;

	@Column(name = "Name", nullable = false)
	private String name = "";

	@Column(name = "Description", nullable = false)
	private String description = "";

	@Column(name = "ItemType", nullable = false)
	@Enumerated(EnumType.STRING)
	private ItemType type;

	@Column(name = "Level", nullable = false)
	private int level;

	@Column(name = "ItemRarity", nullable = false)
	@Enumerated(EnumType.STRING)
	private ItemRarity rarity;

	@Column(name = "VendorValue", nullable = false)
	private long vendorValue;

	@Column(name = "IconFileID", nullable = false)
	private long iconFileId;

	@Column(name = "IconFileSignature", nullable = false)
	private String iconFileSignature = "";

	@Column(name = "DefaultSkin", nullable = false)
	private long defaultSkin;

	@Column(name = "Language", nullable = false)
	@Enumerated(EnumType.STRING)
	private Language language;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "WeaponID")
	private Weapon weapon;

	@Column(name = "WeaponID", insertable = false, updatable = false, nullable = true)
	private Long weaponId;

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private Set<ItemGameTypeRel> gameTypes;

	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private Set<ItemFlagRel> flags;

	public Item() {
		super();
	}

	public Item(Date createDate, User createUser, Date updateDate, User updateUser, long externalId, String name,
			String description, ItemType type, int level, ItemRarity rarity, long vendorValue, long iconFileId,
			String iconFileSignature, long defaultSkin, Language language, Weapon weapon) {
		super();
		this.id = 0;
		this.createDate = createDate;
		this.createUser = createUser;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.externalId = externalId;
		this.name = name == null ? "" : name;
		this.description = description == null ? "" : description;
		this.type = type;
		this.level = level;
		this.rarity = rarity;
		this.vendorValue = vendorValue;
		this.iconFileId = iconFileId;
		this.iconFileSignature = iconFileSignature == null ? "" : iconFileSignature;
		this.defaultSkin = defaultSkin;
		this.language = language;
		this.weapon = weapon;
	}

	@Override
	public Date getCreateDate() {
		return this.createDate;
	}

	@Override
	public User getCreateUser() {
		return this.createUser;
	}

	public long getDefaultSkin() {
		return this.defaultSkin;
	}

	public String getDescription() {
		return this.description;
	}

	public long getExternalId() {
		return this.externalId;
	}

	public Set<ItemFlagRel> getFlags() {
		return this.flags;
	}

	public Set<ItemGameTypeRel> getGameTypes() {
		return this.gameTypes;
	}

	public long getIconFileId() {
		return this.iconFileId;
	}

	public String getIconFileSignature() {
		return this.iconFileSignature;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Language getLanguage() {
		return this.language;
	}

	public int getLevel() {
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public ItemRarity getRarity() {
		return this.rarity;
	}

	public ItemType getType() {
		return this.type;
	}

	@Override
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	public User getUpdateUser() {
		return this.updateUser;
	}

	public long getVendorValue() {
		return this.vendorValue;
	}

	public Weapon getWeapon() {
		return this.weapon;
	}

	public Long getWeaponId() {
		return this.weaponId;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public void setDefaultSkin(long defaultSkin) {
		this.defaultSkin = defaultSkin;
	}

	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}

	public void setExternalId(long externalId) {
		this.externalId = externalId;
	}

	public void setFlags(Set<ItemFlagRel> flags) {
		this.flags = flags;
	}

	public void setGameTypes(Set<ItemGameTypeRel> gameTypes) {
		this.gameTypes = gameTypes;
	}

	public void setIconFileId(long iconFileId) {
		this.iconFileId = iconFileId;
	}

	public void setIconFileSignature(String iconFileSignature) {
		this.iconFileSignature = iconFileSignature == null ? "" : iconFileSignature;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name == null ? "" : name;
	}

	public void setRarity(ItemRarity rarity) {
		this.rarity = rarity;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public void setVendorValue(long vendorValue) {
		this.vendorValue = vendorValue;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", createDate: ").append(this.createDate);
		sb.append(", createUser id: ").append(this.createUser.getId());
		sb.append(", updateDate: ").append(this.updateDate);
		sb.append(", updateUser id: ").append(this.updateUser.getId());
		sb.append(", externalId: ").append(this.externalId);
		sb.append(", name: ").append(this.name);
		sb.append(", description: ").append(this.description);
		sb.append(", type: ").append(this.type);
		sb.append(", level: ").append(this.level);
		sb.append(", rarity: ").append(this.rarity);
		sb.append(", vendorValue: ").append(this.vendorValue);
		sb.append(", iconFileId: ").append(this.iconFileId);
		sb.append(", iconFileSignature: ").append(this.iconFileSignature);
		sb.append(", defaultSkin: ").append(this.defaultSkin);
		sb.append(", language: ").append(this.language);
		sb.append(", weapon id: ").append(this.weaponId);
		sb.append("]");
		return sb.toString();
	}
}
