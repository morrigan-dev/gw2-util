package de.morrigan.dev.gw2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class BitUtilsTest {

	private static final long ALL_BITS_ONE = -1L;
	
	@Test
	public void test_if_removeLongBit_remove_lowest_bit_correct() {
		long expected = -2;
		long actual = BitUtils.removeLongBit(ALL_BITS_ONE, 0);
		assertEquals(expected, actual);
	}

	@Test
	public void test_if_removeLongBit_remove_highest_bit_correct() {
		long expected = Long.MAX_VALUE;
		long actual = BitUtils.removeLongBit(ALL_BITS_ONE, 63);
		assertEquals(expected, actual);
	}

	@Test
	public void test_if_removeLongBit_throws_IAE_if_position_is_below_range() {
		assertThrows(IllegalArgumentException.class, () -> {
			BitUtils.removeLongBit(ALL_BITS_ONE, -1);
		});
	}
	
	@Test
	public void test_if_removeLongBit_throws_IAE_if_position_is_exceed_range() {
		assertThrows(IllegalArgumentException.class, () -> {
			BitUtils.removeLongBit(ALL_BITS_ONE, 64);
		});
	}

	@Test
	public void test_if_removeLongBitsByMask_remove_bits_correct() {
		long mask = 0b1000000000000000000000000000000000000000000000000000000000000001L;
		long expected = Long.MAX_VALUE - 1;
		long actual = BitUtils.removeLongBitsByMask(ALL_BITS_ONE, mask);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_if_setLongBit_set_lowest_bit_correct() {
		long expected = 1L;
		long actual = BitUtils.setLongBit(0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_if_setLongBit_set_highest_bit_correct() {
		long expected = Long.MIN_VALUE;
		long actual = BitUtils.setLongBit(63);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_if_setLongBit_throws_IAE_if_position_is_below_range() {
		assertThrows(IllegalArgumentException.class, () -> {
			BitUtils.setLongBit(-1);
		});
	}
	
	@Test
	public void test_if_setLongBit_throws_IAE_if_position_is_exceed_range() {
		assertThrows(IllegalArgumentException.class, () -> {
			BitUtils.setLongBit(64);
		});
	}
}
