import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

	private BoardComparator boardComparator = new BoardComparator();
	private MinPQ<SearchNode> pq = new MinPQ<>(boardComparator);
	// private Set<Board> predecessors = new HashSet<>();
	private Deque<Board> solutionBoards = new LinkedList<>();

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		// insert the initial search node (the initial board, 0 moves, and a null predecessor search node) into a priority queue
		pq.insert(new SearchNode(initial, null, 0));
		SearchNode min = null;
		while (!pq.isEmpty() && !(min = pq.delMin()).board.isGoal()) {
			// if (predecessors.contains(min.board)) {
			// continue;
			// }
			// System.out.println(min.board);
			for (Board neighbor : min.board.neighbors()) {
				final SearchNode searchNodePredecessor = new SearchNode(min.board, min.predecessor, min.moves);
				if (searchNodePredecessor.board.equals(neighbor)) {
					continue;
				}
				pq.insert(new SearchNode(neighbor, searchNodePredecessor, min.moves + 1));
			}

			// predecessors.add(min.board);
		}

		assert min != null;

		SearchNode predecessor = min.predecessor;
		do {
			solutionBoards.addFirst(predecessor.board);
		} while ((predecessor = predecessor.predecessor) != null);
	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return true;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (isSolvable()) {
			return solutionBoards.size();
		}
		return -1;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (isSolvable()) {
			return solutionBoards;
		}
		return null;
	}

	private class BoardComparator implements Comparator<SearchNode> {
		@Override
		public int compare(SearchNode b1, SearchNode b2) {
			// return (b1.board.hamming() + b1.moves) - (b2.board.hamming() + b2.moves);
			return (b1.board.manhattan() + b1.moves) - (b2.board.manhattan() + b2.moves);
		}
	}

	private class SearchNode {
		Board board;
		SearchNode predecessor;
		int moves;

		SearchNode(Board board, SearchNode predecessor, int moves) {
			this.board = board;
			this.predecessor = predecessor;
			this.moves = moves;
		}
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

	}
}