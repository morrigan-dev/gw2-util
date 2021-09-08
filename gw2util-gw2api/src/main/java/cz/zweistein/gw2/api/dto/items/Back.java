package cz.zweistein.gw2.api.dto.items;

import java.util.List;

public class Back {
	
	private List<InfusionSlot> infusionSlots;
	
	private InfixUpgrade infixUpgrade;
	
	private String suffixItemId;

	public Back(List<InfusionSlot> infusionSlots, InfixUpgrade infixUpgrade, String suffixItemId) {
		super();
		this.infusionSlots = infusionSlots;
		this.infixUpgrade = infixUpgrade;
		this.suffixItemId = suffixItemId;
	}

	public List<InfusionSlot> getInfusionSlots() {
		return infusionSlots;
	}

	public InfixUpgrade getInfixUpgrade() {
		return infixUpgrade;
	}

	public String getSuffixItemId() {
		return suffixItemId;
	}

	@Override
	public String toString() {
		return "Back [infusionSlots=" + infusionSlots + ", infixUpgrade=" + infixUpgrade + ", suffixItemId=" + suffixItemId + "]";
	}

}
