package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.GameType;
import cz.zweistein.gw2.api.dto.enums.Restriction;
import cz.zweistein.gw2.api.dto.enums.items.ItemClass;
import cz.zweistein.gw2.api.dto.enums.items.ItemFlag;
import cz.zweistein.gw2.api.dto.enums.items.Rarity;

public class Item {

	private Long itemId;

	private String name;

	private String description;

	private Long level;

	private Rarity rarity;

	private Long vendorValue;

	private List<GameType> gameTypes;

	private List<ItemFlag> flags;

	private List<Restriction> restrictions;

	private Long suffixItemId;

	private ItemClass itemType;

	private Armor armor;

	private Weapon weapon;

	private Bag bag;

	private Container container;

	private Consumable consumable;

	private Trinket trinket;

	private UpgradeComponent upgradeComponent;

	private Back back;

	private Gathering gathering;

	private Gizmo gizmo;

	private Tool tool;

	public Item(Long itemId, String name, String description, Long level, Rarity rarity, Long vendorValue, List<GameType> gameTypes,
			List<ItemFlag> flags, List<Restriction> restrictions, Long suffixItemId, ItemClass itemType, Armor armor, Weapon weapon, Bag bag,
			Container container, Consumable consumable, Trinket trinket, UpgradeComponent upgradeComponent, Back back, Gathering gathering, Gizmo gizmo,
			Tool tool) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.level = level;
		this.rarity = rarity;
		this.vendorValue = vendorValue;
		this.gameTypes = gameTypes;
		this.flags = flags;
		this.restrictions = restrictions;
		this.suffixItemId = suffixItemId;
		this.itemType = itemType;
		this.armor = armor;
		this.weapon = weapon;
		this.bag = bag;
		this.container = container;
		this.consumable = consumable;
		this.trinket = trinket;
		this.upgradeComponent = upgradeComponent;
		this.back = back;
		this.gathering = gathering;
		this.gizmo = gizmo;
		this.tool = tool;
	}

	public Gizmo getGizmo() {
		return gizmo;
	}

	public Back getBack() {
		return back;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getLevel() {
		return this.level;
	}

	public Rarity getRarity() {
		return this.rarity;
	}

	public Long getVendorValue() {
		return this.vendorValue;
	}

	public List<GameType> getGameTypes() {
		return this.gameTypes;
	}

	public List<ItemFlag> getFlags() {
		return this.flags;
	}

	public List<Restriction> getRestrictions() {
		return this.restrictions;
	}

	public Armor getArmor() {
		return this.armor;
	}

	public Consumable getConsumable() {
		return consumable;
	}

	public Long getSuffixItemId() {
		return this.suffixItemId;
	}

	public UpgradeComponent getUpgradeComponent() {
		return upgradeComponent;
	}

	public ItemClass getItemType() {
		return this.itemType;
	}

	public Bag getBag() {
		return bag;
	}

	public Container getContainer() {
		return container;
	}

	public Trinket getTrinket() {
		return trinket;
	}

	public Gathering getGathering() {
		return gathering;
	}

	public Tool getTool() {
		return this.tool;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + this.itemId + ", name=" + this.name + ", description=" + this.description + ", level=" + this.level + ", rarity="
				+ this.rarity + ", vendorValue=" + this.vendorValue + ", gameTypes=" + this.gameTypes + ", flags=" + this.flags + ", restrictions="
				+ this.restrictions + ", suffixItemId=" + this.suffixItemId + ", itemType=" + this.itemType + ", armor=" + this.armor + ", weapon="
				+ this.weapon + ", bag=" + this.bag + ", container=" + this.container + ", consumable=" + this.consumable + ", trinket=" + this.trinket
				+ ", upgradeComponent=" + this.upgradeComponent + ", back=" + this.back + ", gathering=" + this.gathering + ", gizmo=" + this.gizmo + ", tool="
				+ this.tool + "]";
	}

}
