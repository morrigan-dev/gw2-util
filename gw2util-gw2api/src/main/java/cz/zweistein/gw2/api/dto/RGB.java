package cz.zweistein.gw2.api.dto;

public class RGB {
	
	private Long r;
	
	private Long g;
	
	private Long b;

	public RGB(Long r, Long g, Long b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Long getR() {
		return r;
	}

	public Long getG() {
		return g;
	}

	public Long getB() {
		return b;
	}

	@Override
	public String toString() {
		return "RGB [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
}
