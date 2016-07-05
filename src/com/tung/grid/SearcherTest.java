package com.tung.grid;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SearcherTest {
	
	String text = Searcher.readFile(new File("data/french_armed_forces.txt"));			

	@Test
	public void bruteForce_single_term() {
		assertEquals(3, Searcher.bruteForce(text, "nation"));		
	}
	
	@Test
	public void bruteForce_phrase() {
		assertEquals(2, Searcher.bruteForce(text, "powerful nation"));
	}
	
	@Test
	public void regx_single_term() {		
		assertEquals(3, Searcher.regx(text, "nation"));		
	}
	
	@Test
	public void regx_phrase() {
		assertEquals(2, Searcher.regx(text, "powerful nation"));
	}
	
	@Test
	public void testStructure_term() {
		assertEquals(3, Searcher.structure(text, "nation"));		
	}
	
	@Test
	public void testStructure_phrase() {
		assertEquals(2, Searcher.structure(text, "powerful nation"));
	}
}
