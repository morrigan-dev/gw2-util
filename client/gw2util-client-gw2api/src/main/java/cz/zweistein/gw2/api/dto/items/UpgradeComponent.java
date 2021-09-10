package cz.zweistein.gw2.api.dto.items;

import java.util.List;

import cz.zweistein.gw2.api.dto.enums.items.InfusionSlotFlag;
import cz.zweistein.gw2.api.dto.enums.items.UpgradeComponentFlag;
import cz.zweistein.gw2.api.dto.enums.items.UpgradeComponentType;

public class UpgradeComponent {
	
	private UpgradeComponentType type;
	
	private List<UpgradeComponentFlag> flags;
	
	private InfixUpgrade infixUpgrade;
	
	private String suffix;

	private List<InfusionSlotFlag> infusionUpgradeFlags;
	
	private List<String> bonuses;

	public UpgradeComponent(UpgradeComponentType type, List<UpgradeComponentFlag> flags, InfixUpgrade infixUpgrade, String suffix,
			List<InfusionSlotFlag> infusionUpgradeFlags, List<String> bonuses) {
		super();
		this.type = type;
		this.flags = flags;
		this.infixUpgrade = infixUpgrade;
		this.suffix = suffix;
		this.infusionUpgradeFlags = infusionUpgradeFlags;
		this.bonuses = bonuses;
	}

	public UpgradeComponentType getType() {
		return type;
	}

	public List<UpgradeComponentFlag> getFlags() {
		return flags;
	}

	public InfixUpgrade getInfixUpgrade() {
		return infixUpgrade;
	}

	public String getSuffix() {
		return suffix;
	}

	public List<InfusionSlotFlag> getInfusionUpgradeFlags() {
		return infusionUpgradeFlags;
	}

	public List<String> getBonuses() {
		return bonuses;
	}

	@Override
	public String toString() {
		return "UpgradeComponent [type=" + this.type + ", flags=" + this.flags + ", infixUpgrade=" + this.infixUpgrade + ", suffix=" + this.suffix
				+ ", infusionUpgradeFlags=" + this.infusionUpgradeFlags + ", bonuses=" + this.bonuses + "]";
	}


}
