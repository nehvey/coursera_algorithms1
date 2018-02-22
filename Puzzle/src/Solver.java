import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solver {

    private BoardComparator boardComparator = new BoardComparator();
    private MinPQ<Board> pq = new MinPQ<>(boardComparator);
    private int moves = 0;
    private Set<Board> predecessors = new HashSet<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // insert the initial search node (the initial board, 0 moves, and a null predecessor search node) into a priority queue
        pq.insert(initial);
        Board min;
        while (!pq.isEmpty() && !(min = pq.delMin()).isGoal()) {
            if (predecessors.contains(min)) {
                continue;
            }
//            System.out.println(min);
            for (Board neighbor : min.neighbors()) {
                if (!predecessors.contains(neighbor)) {
                    pq.insert(neighbor);
                }
            }

//            System.out.println(min.manhattan());
//            System.out.println(min.hamming());
            predecessors.add(min);
            moves++;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
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
//            return b1.hamming() - b2.hamming();
            return b1.manhattan() - b2.manhattan();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

    }
}