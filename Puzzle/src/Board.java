import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {

	private final int[][] blocks;
	private final int[][] goal;
	private final int[] xCoordinates;
	private final int[] yCoordinates;
	private final int n;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		n = blocks.length;
		this.blocks = blocks;
		goal = new int[n][n];
		xCoordinates = new int[n * n];
		yCoordinates = new int[n * n];
		int j = 0;
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				goal[i][k] = j;
				xCoordinates[j] = i;
				yCoordinates[j] = k;
				j++;
			}
		}
	}

	// board dimension n
	public int dimension() {
		return n;
	}

	// number of blocks out of place
	public int hamming() {
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
		return result;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
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
		return result;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if (blocks[i][k] != goal[i][k]) {
					return false;
				}
			}
		}
		return true;
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
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				if (this.blocks[i][k] != that.blocks[i][k]) {
					return false;
				}
			}
		}
		return true;

	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		return () -> new Iterator<Board>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Board next() {
				return null;
			}
		};
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
		System.out.println("Hamming - " + initial.hamming());
		System.out.println("Manhattan - " + initial.manhattan());

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