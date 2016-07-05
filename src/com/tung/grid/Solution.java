package com.tung.grid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This is a solution for Target interview
 * @author TUNG
 *
 * 2.	Barren Land Analysis
 * You have a farm of 400m by 600m where coordinates of the field are from (0, 0) to (399, 599). A portion of the farm is barren,
 * and all the barren land is in the form of rectangles. Due to these rectangles of barren land, the remaining area of fertile
 * land is in no particular shape. An area of fertile land is defined as the largest area of land that is not covered by any of
 * the rectangles of barren land.
 *
 * Output all the fertile land area in square meters, sorted from smallest area to greatest, separated by a space.
 *
 * Solution:
 *
 * Treat the area as a matrix. Mark the barren area. Then simply traverse the matrix and count the area using depth-first-search.
 * When encounter another UNVISIT cell after the search, it means we encounter another unvisited area, reset the counter
 *
 */
public class Solution {

	public enum State {BARREN, VISIT, UNVISIT};

	private State[][] matrix;
	private int row, col;

	private int counter = 0;	//counter to keep track of area
	private List<Integer> fertiles = new ArrayList<Integer>(); //store the area in sorted order

	public Solution(int r, int c) {
		row = r;
		col = c;
		matrix = new State[row][col];

		//initialize the matrix to UNVISIT
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++)
				matrix[i][j] = State.UNVISIT;
		}
	}
	
	public Solution() {
		this(600, 400);
	}

	/**
	 * Mark the barren area start/end at (@param lx, @param ly) and (@param rx, @param ry)
	 */
	public void markBarren(int lx, int ly, int rx, int ry) {
		for(int i=ry; i<=ly; i++)
			for(int j=lx; j<=rx; j++)
				matrix[i][j] = State.BARREN;
	}

	/**
	 * Depth first search implementation using stack starting at (@param x, @param y)
	 */
	public void dfs_with_stack(int x, int y) {
		Stack<int[]> stack = new Stack<int[]>();

		stack.push(new int[] {x, y});

		while(!stack.isEmpty()) {

			int[] m = stack.pop();

			int i = m[0];
			int j = m[1];
			if(matrix[i][j] == State.UNVISIT) {

				//visit the node
				matrix[i][j] = State.VISIT;
				counter++;

				//Pushing unvisited and not barren neighboring nodes onto the stack
				if(j < col - 1 && matrix[i][j+1] == State.UNVISIT)
					stack.push(new int[] {i,j+1});

				if(j > 0 && matrix[i][j-1] == State.UNVISIT)
					stack.push(new int[] {i,j-1});

				if(i > 0 && matrix[i-1][j] == State.UNVISIT)
					stack.push(new int[] {i-1,j});

				if(i < row - 1 && matrix[i+1][j] == State.UNVISIT)
					stack.push(new int[] {i+1,j});
			}
		}
	}

	/**
	 * Flood fill the matrix using DFS. Then scan the matrix again to look for unfilled cell and start flood fill again
	 */
	public void scan() {
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++) {
				if(matrix[i][j] == State.UNVISIT) {
					counter = 0;	//reset the counter
					dfs_with_stack(i,j);
					fertiles.add(Integer.valueOf(counter));
				}
			}
		}

		//print the result
		Collections.sort(fertiles);
		System.out.println(fertiles);
	}

	public static void main(String[] args) throws Exception {
		
		Solution m = new Solution();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		String[] recs = input.split(",");
		for(String rec : recs) {
			String[] coord = rec.split(" ");
			m.markBarren(Integer.valueOf(coord[0].trim()), m.row - 1 - Integer.valueOf(coord[1].trim()),
					Integer.valueOf(coord[2].trim()), m.row - 1 - Integer.valueOf(coord[3].trim()));
		}
	    reader.close();

		m.scan();
	}
}
