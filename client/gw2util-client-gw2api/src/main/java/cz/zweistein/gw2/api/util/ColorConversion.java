package cz.zweistein.gw2.api.util;

import java.awt.Color;

import cz.zweistein.gw2.api.dto.RGB;

public class ColorConversion {
	
	public static String getRgbColor(RGB color) {
		return String.format("#%06X", (0xFFFFFF & new Color(color.getR().intValue(), color.getG().intValue(), color.getB().intValue()).getRGB()));
	}
}
