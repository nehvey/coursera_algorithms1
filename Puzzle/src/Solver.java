import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private static final BoardComparator BOARD_COMPARATOR = new BoardComparator();
    private final MinPQ<SearchNode> pqMain = new MinPQ<>(BOARD_COMPARATOR);
    private final MinPQ<SearchNode> pqTwin = new MinPQ<>(BOARD_COMPARATOR);
    private final Deque<Board> solutionBoards = new LinkedList<>();
    private boolean solvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // insert the initial search node (the initial board, 0 moves, and a null predecessor search node) into a priority queue
        pqMain.insert(new SearchNode(initial, null, 0));
        pqTwin.insert(new SearchNode(initial.twin(), null, 0));
        SearchNode minMain = null;
        while (!pqMain.isEmpty() && !(minMain = pqMain.delMin()).board.isGoal()) {
            SearchNode minTwin = pqTwin.delMin();
            if (minTwin.board.isGoal()) {
                solvable = false;
                return;
            }

            // process twin
            for (Board neighbor : minTwin.board.neighbors()) {
                final SearchNode searchNodePredecessor = new SearchNode(minTwin.board, minTwin.predecessor, minTwin.moves);
                if (searchNodePredecessor.board.equals(neighbor)) {
                    continue;
                }
                pqTwin.insert(new SearchNode(neighbor, searchNodePredecessor, minTwin.moves + 1));
            }

            // process main
            for (Board neighbor : minMain.board.neighbors()) {
                final SearchNode searchNodePredecessor = new SearchNode(minMain.board, minMain.predecessor, minMain.moves);
                if (searchNodePredecessor.board.equals(neighbor)) {
                    continue;
                }
                pqMain.insert(new SearchNode(neighbor, searchNodePredecessor, minMain.moves + 1));
            }

            // predecessors.add(minMain.board);
        }

        assert minMain != null;

        solutionBoards.addFirst(minMain.board);

        SearchNode predecessor = minMain.predecessor;
        if (predecessor != null) {
            do {
                solutionBoards.addFirst(predecessor.board);
            } while ((predecessor = predecessor.predecessor) != null);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solutionBoards.size() - 1;
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

    private static class BoardComparator implements Comparator<SearchNode> {
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
        // TODO

    }
}