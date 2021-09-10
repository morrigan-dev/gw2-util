package cz.zweistein.gw2.api.dto;

public class ColorHue {

	private Double brightness;
	
	private Double contrast;
	
	private Double hue;
	
	private Double saturation;
	
	private Double lightness;
	
	private RGB rgb;
	
	public ColorHue(Double brightness, Double contrast, Double hue, Double saturation, Double lightness, RGB rgb) {
		super();
		this.brightness = brightness;
		this.contrast = contrast;
		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
		this.rgb = rgb;
	}

	public RGB getRgb() {
		return rgb;
	}

	public Double getBrightness() {
		return this.brightness;
	}

	public Double getContrast() {
		return this.contrast;
	}

	public Double getHue() {
		return this.hue;
	}

	public Double getSaturation() {
		return this.saturation;
	}

	public Double getLightness() {
		return this.lightness;
	}

	@Override
	public String toString() {
		return "ColorHue [brightness=" + brightness + ", contrast=" + contrast + ", hue=" + hue + ", saturation=" + saturation + ", lightness=" + lightness + ", rgb=" + rgb + "]";
	}

}
