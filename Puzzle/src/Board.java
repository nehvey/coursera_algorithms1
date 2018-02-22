import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

	private static int[][] goal;
	private static int[] xCoordinates;
	private static int[] yCoordinates;
	private static int n = 0;
	private final int[][] blocks;
	private int manhattanDistance = -1;
	private int hammingDistance = -1;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		this.blocks = blocks;
		// static part is always the same per puzzle run, so we run it only for the 1st Board
		if (n == 0) {
			n = blocks.length;
			goal = new int[n][n];
			xCoordinates = new int[n * n];
			yCoordinates = new int[n * n];
			int j = 0;
			for (int i = 0; i < n; i++) {
				for (int k = 0; k < n; k++) {
					if (j + 1 < n * n) {
						goal[i][k] = j + 1;
					} else {
						goal[i][k] = 0;
					}
					xCoordinates[j] = i;
					yCoordinates[j] = k;
					j++;
				}
			}
		}
	}

	// board dimension n
	public int dimension() {
		return n;
	}

	// number of blocks out of place
	public int hamming() {
		if (hammingDistance != -1) {
			return hammingDistance;
		}
		int result = 0;
		int j = 0;
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if (j > 0 && blocks[i][k] != j) {
					result++;
				}
				j++;
			}
		}
		hammingDistance = result;
		return result;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		if (manhattanDistance != -1) {
			return manhattanDistance;
		}
		int result = 0;
		int j = 0;
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				final int blockNumber = blocks[i][k];
				if (blockNumber > 0 && blockNumber != j) {
					result += Math.abs(i - xCoordinates[blockNumber]) + Math.abs(k - yCoordinates[blockNumber]);
				}
				j++;
			}
		}
		manhattanDistance = result;
		return result;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return blocks[n - 1][n - 1] == 0 && Arrays.deepEquals(blocks, goal);
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		return null;
	}

	// does this board equal other?
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other == null)
			return false;
		if (other.getClass() != this.getClass())
			return false;
		Board that = (Board) other;
		return Arrays.deepEquals(this.blocks, that.blocks);
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		List<Board> neighbors = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				final int block = blocks[i][k];
				if (block == 0) {
					// up
					if (i - 1 >= 0) {
						int[][] blocksCopy = Arrays.stream(blocks).map(int[]::clone).toArray(int[][]::new);
						final int tmp = blocksCopy[i - 1][k];
						blocksCopy[i - 1][k] = block;
						blocksCopy[i][k] = tmp;
						neighbors.add(new Board(blocksCopy));
					}
					// down
					if (i + 1 < n) {
						int[][] blocksCopy = Arrays.stream(blocks).map(int[]::clone).toArray(int[][]::new);
						final int tmp = blocksCopy[i + 1][k];
						blocksCopy[i + 1][k] = block;
						blocksCopy[i][k] = tmp;
						neighbors.add(new Board(blocksCopy));
					}
					// left
					if (k - 1 >= 0) {
						int[][] blocksCopy = Arrays.stream(blocks).map(int[]::clone).toArray(int[][]::new);
						final int tmp = blocksCopy[i][k - 1];
						blocksCopy[i][k - 1] = block;
						blocksCopy[i][k] = tmp;
						neighbors.add(new Board(blocksCopy));
					}
					// right
					if (k + 1 < n) {
						int[][] blocksCopy = Arrays.stream(blocks).map(int[]::clone).toArray(int[][]::new);
						final int tmp = blocksCopy[i][k + 1];
						blocksCopy[i][k + 1] = block;
						blocksCopy[i][k] = tmp;
						neighbors.add(new Board(blocksCopy));
					}
					break;
				}
			}
		}
		return neighbors;
	}

	@Override
	public int hashCode() {
		int orderSum = 0;
		int j = 1;
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				orderSum += blocks[i][k] * j;
				j++;
			}
		}
		return orderSum;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}

		return s.toString();
	}

	// unit tests (not graded)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// my tests
		System.out.println(initial.toString());

		System.out.println("goal:");
		System.out.println(new Board(initial.goal));
		System.out.println("Hamming - " + initial.hamming());
		System.out.println("Manhattan - " + initial.manhattan());

		System.out.println("~~~~~~~~~~~Neighbors~~~~~~~~~~");
		for (Board board : initial.neighbors()) {
			System.out.println(board);
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~~~~~~~Equal?~~~~~~~~~~~");
		Board duplicate = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
		System.out.println(initial.equals(duplicate));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}