package cz.zweistein.gw2.api.dto.items;

import cz.zweistein.gw2.api.dto.enums.items.AttributeType;

public class Attribute {
	
	private AttributeType attribute;
	
	private Long modifier;

	public Attribute(AttributeType attribute, Long modifier) {
		super();
		this.attribute = attribute;
		this.modifier = modifier;
	}

	public AttributeType getAttribute() {
		return attribute;
	}

	public Long getModifier() {
		return modifier;
	}

	@Override
	public String toString() {
		return "Attribute [attribute=" + attribute + ", modifier=" + modifier + "]";
	}

}
