package cz.zweistein.gw2.app.color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Map.Entry;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dao.OfflineJsonDao;
import cz.zweistein.gw2.api.dto.Color;
import cz.zweistein.gw2.api.util.ColorConversion;

public class ColorSheet {

	public static void main(String[] args) throws RemoteException, FileNotFoundException, UnsupportedEncodingException {

		GW2API api = new GW2API();
		api.setDao(new OfflineJsonDao());

		StringBuilder sb = new StringBuilder();

		Map<Long, Color> colors = api.getColors();

		sb.append("<html><head><title>Color Sheet</title></head><body><table>");

		sb.append("<tr><th>Name</th><th>Base</th><th>Default</th><th>Cloth</th><th>Leather</th><th>Metal</th></th>\n");

		for (Entry<Long, Color> entry : colors.entrySet()) {

			sb.append("<tr><td>");
			sb.append(entry.getValue().getName());

			sb.append("</td><td style=\"background-color: ").append(ColorConversion.getRgbColor(entry.getValue().getBaseRgb())).append(";\">");
			sb.append("&nbsp;");
			sb.append("</td><td style=\"background-color: ");
			if (entry.getValue().getDefaultColor() != null) {
				sb.append(ColorConversion.getRgbColor(entry.getValue().getDefaultColor().getRgb()));
			}
			sb.append(";\">");
			sb.append("&nbsp;");
			sb.append("</td><td style=\"background-color: ");
			if (entry.getValue().getClothColor() != null) {
				sb.append(ColorConversion.getRgbColor(entry.getValue().getClothColor().getRgb()));
			}
			sb.append(";\">");
			sb.append("&nbsp;");
			sb.append("</td><td style=\"background-color: ");
			if (entry.getValue().getLeatherColor() != null) {
				sb.append(ColorConversion.getRgbColor(entry.getValue().getLeatherColor().getRgb()));
			}
			sb.append(";\">");
			sb.append("&nbsp;");
			sb.append("</td><td style=\"background-color: ");
			if (entry.getValue().getMetalColor() != null) {
				sb.append(ColorConversion.getRgbColor(entry.getValue().getMetalColor().getRgb()));
			}
			sb.append(";\">");
			sb.append("&nbsp;");
			sb.append("</tr>\n");

		}

		sb.append("</table></body></html>");

		PrintWriter writer = new PrintWriter("colorsheet.html", "UTF-8");
		writer.println(sb.toString());
		writer.close();

	}

}
