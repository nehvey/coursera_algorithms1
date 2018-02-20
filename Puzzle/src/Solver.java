import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

	private BoardComparator boardComparator = new BoardComparator();
	private MinPQ<Board> pq = new MinPQ<>(boardComparator);
	private int moves = 0;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		// insert the initial search node (the initial board, 0 moves, and a null predecessor search node) into a priority queue
		pq.insert(initial);
		Board min;
		while (!(min = pq.delMin()).isGoal()) {
//			System.out.println(min);
			for (Board neighbor : min.neighbors()) {
				pq.insert(neighbor);
			}
			moves++;
		}
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return false;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return moves;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
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

	private class BoardComparator implements Comparator<Board> {
		@Override
		public int compare(Board b1, Board b2) {
			return b1.manhattan() - b2.manhattan();
		}
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

	}
}