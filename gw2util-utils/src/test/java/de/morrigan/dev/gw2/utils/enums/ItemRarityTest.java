package de.morrigan.dev.gw2.utils.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ItemRarityTest {

	@Test
	public void test_if_getEnumOfOrdinal_return_correct_enum_by_lowest_ordinal() {
		ItemRarity expected = ItemRarity.JUNK;
		ItemRarity actual = ItemRarity.getEnumOfOrdinal(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_if_getEnumOfOrdinal_return_correct_enum_by_hightes_ordinal() {
		ItemRarity expected = ItemRarity.LEGENDARY;
		ItemRarity actual = ItemRarity.getEnumOfOrdinal(7);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_if_getEnumOfOrdinal_return_correct_enum_by_invalid_ordinal() {
		ItemRarity expected = ItemRarity.UNKNOWN;
		ItemRarity actual = ItemRarity.getEnumOfOrdinal(-1);
		assertEquals(expected, actual);
	}
}
