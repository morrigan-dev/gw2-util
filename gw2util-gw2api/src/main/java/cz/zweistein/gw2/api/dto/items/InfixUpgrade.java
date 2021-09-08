package cz.zweistein.gw2.api.dto.items;

import java.util.List;

public class InfixUpgrade {
	
	private Buff buff;
	
	private List<Attribute> attributes;

	public InfixUpgrade(Buff buff, List<Attribute> attributes) {
		super();
		this.buff = buff;
		this.attributes = attributes;
	}

	public Buff getBuff() {
		return buff;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		return "InfixUpgrade [buff=" + buff + ", attributes=" + attributes + "]";
	}

}
