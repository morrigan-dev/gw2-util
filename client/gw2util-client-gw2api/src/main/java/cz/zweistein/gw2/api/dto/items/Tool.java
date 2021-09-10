package cz.zweistein.gw2.api.dto.items;

import cz.zweistein.gw2.api.dto.enums.items.ToolType;

public class Tool {
	
	private ToolType type;
	
	private Long charges;

	public Tool(ToolType type, Long charges) {
		super();
		this.type = type;
		this.charges = charges;
	}

	public ToolType getType() {
		return this.type;
	}

	public Long getCharges() {
		return this.charges;
	}

	@Override
	public String toString() {
		return "Tool [type=" + this.type + ", charges=" + this.charges + "]";
	}

}
