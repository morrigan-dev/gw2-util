package cz.zweistein.gw2.api.transformer;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cz.zweistein.gw2.api.dto.MapRectangle;
import cz.zweistein.gw2.api.dto.Point2D;
import cz.zweistein.gw2.api.dto.Point3D;

public class JSONToJavaTransformerUtils {

	public Long parseGracefullyLong(JSONObject obj, String key) {
		Long result = null;

		Object value = obj.get(key);

		if (value != null && !"".equals(value)) {
			result = Long.parseLong((String) value);
		}

		return result;
	}

	public Double parseDouble(Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof Double) {
			return (Double) obj;
		} else if (obj instanceof Long) {
			return Double.valueOf(obj.toString());
		} else {
			throw new IllegalArgumentException(obj.toString());
		}
	}

	public Point3D parsePoint3D(JSONArray point) {
		return new Point3D(parseDouble(point.get(0)), parseDouble(point.get(1)), parseDouble(point.get(2)));
	}

	public Point2D parsePoint2D(JSONArray point) {
		return new Point2D(parseDouble(point.get(0)), parseDouble(point.get(1)));
	}

	public List<Long> parseLongList(JSONArray listObj) {
		List<Long> list = new ArrayList<Long>(listObj.size());
		for (Object itemO : listObj) {
			list.add((Long) itemO);
		}
		return list;
	}

	public MapRectangle parseRectangle(JSONArray jsonArray) {
		return new MapRectangle(parsePoint2D((JSONArray) jsonArray.get(0)), parsePoint2D((JSONArray) jsonArray.get(1)));
	}

}
