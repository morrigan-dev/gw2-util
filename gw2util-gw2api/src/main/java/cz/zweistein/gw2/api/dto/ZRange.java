package cz.zweistein.gw2.api.dto;

public class ZRange {
	
	private Double from;
	
	private Double to;
	
	public ZRange(Double from, Double to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Double getFrom() {
		return this.from;
	}

	public Double getTo() {
		return this.to;
	}

	@Override
	public String toString() {
		return "ZRange [from=" + this.from + ", to=" + this.to + "]";
	}
	
}
