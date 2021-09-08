package cz.zweistein.gw2.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cz.zweistein.gw2.api.util.Realm;

@RunWith(JUnit4.class)
public class GW2APITest {

	@Test
	public void getItemChatCode() {

		Assert.assertEquals("[&AgEEkAAA]", GW2APIUtilities.getItemChatCode(36868l));

		Assert.assertEquals("[&AgEAAAAA]", GW2APIUtilities.getItemChatCode(null));

	}

	@Test
	public void getRealm() {

		Assert.assertEquals(Realm.EU, GW2APIUtilities.getRealm(2001L));

		Assert.assertEquals(Realm.NA, GW2APIUtilities.getRealm(1013L));

		Assert.assertEquals(Realm.UNKNOWN, GW2APIUtilities.getRealm(9013L));

		Assert.assertEquals(Realm.UNKNOWN, GW2APIUtilities.getRealm(0L));

		Assert.assertEquals(Realm.UNKNOWN, GW2APIUtilities.getRealm(null));

	}

}
