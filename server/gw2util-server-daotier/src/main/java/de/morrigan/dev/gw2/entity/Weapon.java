package de.morrigan.dev.gw2.entity;

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
import javax.persistence.Table;

import de.morrigan.dev.gw2.dto.common.enums.DamageType;
import de.morrigan.dev.gw2.dto.common.enums.WeaponType;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;

@Entity
@Table(name = "Weapon")
public class Weapon implements IEntity {

	// ========================================================================
	// Ab hier kommen die Member, die durch die Interfaces resultieren
	// ========================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	// ========================================================================
	// Ab hier kommen die eigentlichen Datenspalten
	// ========================================================================

	@Column(name = "WeaponType")
	@Enumerated(EnumType.STRING)
	private WeaponType weaponType;

	@Column(name = "DamageType")
	@Enumerated(EnumType.STRING)
	private DamageType damageType;

	@Column(name = "MinPower")
	private int minPower;

	@Column(name = "MaxPower")
	private int maxPower;

	@Column(name = "Defense")
	private int defense;

	@Column(name = "InfusionSlots")
	private String infusionSlots;

	// ========================================================================
	// Ab hier kommen die referenzierten Objekte
	// ========================================================================

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SuffixItemID", nullable = true)
	private Item suffixItem;

	@Column(name = "SuffixItemID", insertable = false, updatable = false, nullable = true)
	private Long suffixItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SecondarySuffixItemID", nullable = true)
	private Item secondarySuffixItem;

	@Column(name = "SecondarySuffixItemID", insertable = false, updatable = false, nullable = true)
	private Long secondarySuffixItemId;

	public Weapon() {
		super();
	}

	public Weapon(WeaponType weaponType, DamageType damageType, int minPower, int maxPower, int defense,
			String infusionSlots, Item suffixItem, Item secondarySuffixItem) {
		super();
		this.id = 0;
		this.weaponType = weaponType;
		this.damageType = damageType;
		this.minPower = minPower;
		this.maxPower = maxPower;
		this.defense = defense;
		this.infusionSlots = infusionSlots;
		this.suffixItem = suffixItem;
		this.secondarySuffixItem = secondarySuffixItem;
	}

	public DamageType getDamageType() {
		return this.damageType;
	}

	public int getDefense() {
		return this.defense;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public String getInfusionSlots() {
		return this.infusionSlots;
	}

	public int getMaxPower() {
		return this.maxPower;
	}

	public int getMinPower() {
		return this.minPower;
	}

	public Item getSecondarySuffixItem() {
		return this.secondarySuffixItem;
	}

	public Long getSecondarySuffixItemId() {
		return this.secondarySuffixItemId;
	}

	public Item getSuffixItem() {
		return this.suffixItem;
	}

	public Long getSuffixItemId() {
		return this.suffixItemId;
	}

	public WeaponType getWeaponType() {
		return this.weaponType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public void setInfusionSlots(String infusionSlots) {
		this.infusionSlots = infusionSlots;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public void setMinPower(int minPower) {
		this.minPower = minPower;
	}

	public void setSecondarySuffixItem(Item secondarySuffixItem) {
		this.secondarySuffixItem = secondarySuffixItem;
	}

	public void setSuffixItem(Item suffixItem) {
		this.suffixItem = suffixItem;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[id: ").append(this.id);
		sb.append(", weaponType: ").append(this.weaponType);
		sb.append(", damageType: ").append(this.damageType);
		sb.append(", minPower: ").append(this.minPower);
		sb.append(", maxPower: ").append(this.maxPower);
		sb.append(", defense: ").append(this.defense);
		sb.append(", infusionSlots: ").append(this.infusionSlots);
		sb.append(", suffixItem id: ").append(this.suffixItemId);
		sb.append(", secondarySuffixItem id: ").append(this.secondarySuffixItemId);
		sb.append("]");
		return sb.toString();
	}
}
