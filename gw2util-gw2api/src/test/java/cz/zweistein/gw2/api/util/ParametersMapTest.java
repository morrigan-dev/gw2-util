package cz.zweistein.gw2.api.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cz.zweistein.gw2.api.util.ParametersMap;

import org.junit.Assert;

@RunWith(JUnit4.class)
public class ParametersMapTest {

	@Test
	public void putIfNotNull() {

		ParametersMap map = new ParametersMap();

		map.putIfNotNull("key", null);

		Assert.assertEquals(null, map.get("key"));
		Assert.assertEquals(false, map.containsKey(("key")));

		map.putIfNotNull("key2", "value");

		Assert.assertEquals("value", map.get("key2"));
		Assert.assertEquals(true, map.containsKey(("key2")));

		Assert.assertEquals(1, map.keySet().size());

	}

}
