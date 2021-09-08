package cz.zweistein.gw2.api.util;

import java.util.HashMap;

public class ParametersMap extends HashMap<String, Object> {
	private static final long serialVersionUID = -5661347582268044771L;

	public void putIfNotNull(String key, Object value) {
		if (value != null) {
			put(key, value);
		}

	}

}
