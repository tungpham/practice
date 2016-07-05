package com.tung.grid;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Searcher {		
	
	private static TreeMap<String, Integer> map = new TreeMap<String, Integer>();		
	
	public static void main(String...args) {
				
		String[] terms = {"most", "nation", "again", "electronic travel"};
		
		File dir = new File("data");
		
		//for simplicity, read all files into memory
		HashMap<String, String> data = new HashMap<String, String>();			
		for(File f : dir.listFiles()) {					
			data.put(f.getName(), readFile(f));
		}				
		
		//now perform search
		for(String term : terms) {
			System.out.println("*****  " + term + "  *****");
			for(Map.Entry<String, String> entry : data.entrySet()) {							
//				System.out.println(entry.getKey() + " - " + regx(entry.getValue(), term));
				System.out.println(entry.getKey() + " - " + structure(entry.getValue(), term));
			}	
		}				
	}		
	
	public static String readFile(File f) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = reader.readLine()) != null) {				
				sb.append(line);
			}									
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static int bruteForce(String text, String term) {			
		int counter = 0;						
		for(int i=0; i<text.length() - term.length(); i++) {
			int j=0;
			while(j < term.length() && text.charAt(i+j) == term.charAt(j)) {
				j++;
			}
			if(j == term.length()) {
				counter++;				
			}
		}		
		return counter;
	}
	
	public static int regx(String text, String term) {
		int counter = 0;		
		Pattern pattern = Pattern.compile(term);		
		Matcher matcher = pattern.matcher(text);		
		while(matcher.find()) {
			counter++;
		}
		return counter;
	}
	
	/*
	 * Rabin-Karp algorithm
	 */
	public static int structure(String text, String term) {						
		int counter = 0;
		int t = text.length();
		int p = term.length();
		//now compute text.length - term.lengh hash value
		int termH = simpleHash(term);
		for(int i=0; i < t-p; i++) {
			int tH = simpleHash(text.substring(i, i+p));
			if(tH == termH) counter++;
		}
		return counter;
								
//		for(String s : text.split("[ .,?!\":-]+")) { 			
//			s = s.trim();
//			if(map.get(s) == null) { 
//				map.put(s, 0);
//			} else {
//				int freq = map.get(s);
//				freq++;
//				map.put(s, freq);
//			}			
//		}		
//		System.out.println(map);
//		
//		return map.get(term) == null ? 0 : map.get(term);
	}
	
	private static int simpleHash(String s) {
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			hash = hash * 31 + s.charAt(i);
		}
		return hash;
	}
	
}
