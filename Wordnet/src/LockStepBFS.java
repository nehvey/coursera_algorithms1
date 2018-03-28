import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class LockStepBFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked1;  // marked1[v] = is there an s->v path?
    private int[] edgeTo1;      // edgeTo1[v] = last edge on shortest s->v path
    private int[] distTo1;      // distTo1[v] = length of shortest s->v path

    private boolean[] marked2;  // marked1[v] = is there an s->v path?
    private int[] edgeTo2;      // edgeTo1[v] = last edge on shortest s->v path
    private int[] distTo2;      // distTo1[v] = length of shortest s->v path

    private int shortestPath = Integer.MAX_VALUE;
    private int ancestor = -1;

    /**
     * Computes the shortest path from {@code s1}/{@code s2} and every other vertex in graph {@code G}.
     * @param G the digraph
     * @param s1 the source #1 vertex
     * @param s2 the source #2 vertex
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public LockStepBFS(Digraph G, int s1, int s2) {
        marked1 = new boolean[G.V()];
        distTo1 = new int[G.V()];
        edgeTo1 = new int[G.V()];

        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        edgeTo2 = new int[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo1[v] = INFINITY;
			distTo2[v] = INFINITY;
		}
        validateVertex(s1);
        validateVertex(s2);
        bfs(G, s1, s2);
    }

    /**
     * Computes the shortest path from any one of the source vertices in {@code sources}
     * to every other vertex in graph {@code G}.
     * @param G the digraph
     * @param sources the source vertices
     * @throws IllegalArgumentException unless each vertex {@code v} in
     *         {@code sources} satisfies {@code 0 <= v < V}
     */
	public LockStepBFS(Digraph G, Iterable<Integer> sources1, Iterable<Integer> sources2) {
        marked1 = new boolean[G.V()];
        distTo1 = new int[G.V()];
        edgeTo1 = new int[G.V()];

        marked2 = new boolean[G.V()];
        distTo2 = new int[G.V()];
        edgeTo2 = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo1[v] = INFINITY;
            distTo2[v] = INFINITY;
        }
		validateVertices(sources1);
		validateVertices(sources2);
		bfs(G, sources1, sources2);
	}

	// BFS from single sources
	private void bfs(Digraph G, int s1, int s2) {
		Queue<Integer> q1 = new Queue<Integer>();
		Queue<Integer> q2 = new Queue<Integer>();
		marked1[s1] = true;
		distTo1[s1] = 0;
		marked2[s2] = true;
		distTo2[s2] = 0;
		q1.enqueue(s1);
		q2.enqueue(s2);

		while (!q1.isEmpty() || !q2.isEmpty()) {
			if (!q1.isEmpty()) {
				int v1 = q1.dequeue();
				for (int w : G.adj(v1)) {
					if (!marked1[w]) {
						// System.out.println(w + " by " + Thread.currentThread());
						edgeTo1[w] = v1;
						distTo1[w] = distTo1[v1] + 1;
						marked1[w] = true;
						q1.enqueue(w);

						if (distTo2[w] != Integer.MAX_VALUE) {
							shortestPath = distTo1[w] + distTo2[w];
							ancestor = w;
							break;
						}
					}
				}
			}

			if (!q2.isEmpty()) {
				int v2 = q2.dequeue();
				for (int w : G.adj(v2)) {
					if (!marked2[w]) {
						// System.out.println(w + " by " + Thread.currentThread());
						edgeTo2[w] = v2;
						distTo2[w] = distTo2[v2] + 1;
						marked2[w] = true;
						q2.enqueue(w);

						if (distTo1[w] != Integer.MAX_VALUE) {
							shortestPath = distTo1[w] + distTo2[w];
							ancestor = w;
							break;
						}
					}
				}
			}
		}
	}

	// BFS from multiple sources
	private void bfs(Digraph G, Iterable<Integer> sources1, Iterable<Integer> sources2) {
		Queue<Integer> q1 = new Queue<Integer>();
		for (int s : sources1) {
			marked1[s] = true;
			distTo1[s] = 0;
			q1.enqueue(s);
		}

		Queue<Integer> q2 = new Queue<Integer>();
		for (int s : sources2) {
			marked2[s] = true;
			distTo2[s] = 0;
			q2.enqueue(s);
		}

		while (!q1.isEmpty() || !q2.isEmpty()) {
			if (!q1.isEmpty()) {
				int v1 = q1.dequeue();
				for (int w : G.adj(v1)) {
					if (!marked1[w]) {
						// System.out.println(w + " by " + Thread.currentThread());
						edgeTo1[w] = v1;
						distTo1[w] = distTo1[v1] + 1;
						marked1[w] = true;
						q1.enqueue(w);

						if (distTo2[w] != Integer.MAX_VALUE) {
							shortestPath = distTo1[w] + distTo2[w];
							ancestor = w;
							break;
						}
					}
				}
			}

			if (!q2.isEmpty()) {
				int v2 = q2.dequeue();
				for (int w : G.adj(v2)) {
					if (!marked2[w]) {
						// System.out.println(w + " by " + Thread.currentThread());
						edgeTo2[w] = v2;
						distTo2[w] = distTo2[v2] + 1;
						marked2[w] = true;
						q2.enqueue(w);

						if (distTo1[w] != Integer.MAX_VALUE) {
							shortestPath = distTo1[w] + distTo2[w];
							ancestor = w;
							break;
						}
					}
				}
			}
		}
	}

    /**
     * Is there a directed path from the source {@code s} (or sources) to vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a directed path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked1[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source {@code s}
     * (or sources) to vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo1[v];
    }

    /**
     * Returns a shortest path from {@code s} (or sources) to {@code v}, or
     * {@code null} if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);

        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo1[x] != 0; x = edgeTo1[x])
            path.push(x);
        path.push(x);
        return path;
    }

    public int getShortestPath() {
        return shortestPath;
    }

    public int getAncestor() {
        return ancestor;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked1.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked1.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }


    /**
     * Unit tests the {@code BreadthFirstDirectedPaths} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("->" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
    }

}
