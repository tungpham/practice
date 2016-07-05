package com.tung.grid;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void test1() {
		Solution m = new Solution(600, 400);
		m.markBarren("0 292 399 307");
		m.scan();
		
		List<Integer> expected = Arrays.asList(new Integer[] {116800, 116800});
		assertEquals(expected, m.getFertiles());
	}
	
	@Test
	public void test2() {
		Solution m = new Solution(600, 400);
		m.markBarren("48 192 351 207, 48 392 351 407, 120 52 135 547, 260 52 275 547");		
		m.scan();
		
		List<Integer> expected = Arrays.asList(new Integer[] {22816, 192608});				
		assertEquals(expected, m.getFertiles());
	}
}
