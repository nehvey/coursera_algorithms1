import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

public class SAP {

	private final Digraph digraph;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		digraph = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		LockStepBFS bfs = new LockStepBFS(digraph, v, w);
		if (bfs.getShortestPath() != Integer.MAX_VALUE) {
			return bfs.getShortestPath();
		}
		return -1;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		return new LockStepBFS(digraph, v, w).getAncestor();
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		LockStepBFS bfs = new LockStepBFS(digraph, v, w);
		if (bfs.getShortestPath() != Integer.MAX_VALUE) {
			return bfs.getShortestPath();
		}
		return -1;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return new LockStepBFS(digraph, v, w).getAncestor();
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		System.out.println(sap.length(8, 13));
		System.out.println(sap.ancestor(10, 7));
		System.out.println(sap.length(Arrays.asList(8), Arrays.asList(13)));
	}
}
