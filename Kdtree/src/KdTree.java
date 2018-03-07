import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, p, 1);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(p, root);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 1);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private int size;

        public Node(Point2D p, int size) {
            this.p = p;
            this.size = size;
        }
    }

    private Node insert(Node node, Point2D p, int depth) {
        if (node == null) {
            return new Node(p, 1);
        }

        int cmp = 0;

        if (depth % 2 != 0) { // compare x for odd node
            if (p.x() < node.p.x()) {
                cmp = -1;
            }
            if (p.x() > node.p.x()) {
                cmp = +1;
            }
        } else {// compare y for even node
            if (p.y() < node.p.y()) {
                cmp = -1;
            }
            if (p.y() > node.p.y()) {
                cmp = +1;
            }
        }

        if (cmp < 0) { // insert to left
            node.lb = insert(node.lb, p, depth + 1);
        } else { // insert to right
            node.rt = insert(node.rt, p, depth + 1);
        }
        node.size++;

        return node;
    }

    private boolean contains(Point2D p, Node node) {
        if (node == null) {
            return false;
        }
        if (node.p.equals(p)) {
            return true;
        }
        return contains(p, node.lb) || contains(p, node.rt);
    }

    private void draw(Node node, int depth) {
        if (node == null) {
            return;
        }

        double xmin, xmax, ymin, ymax;
        if (depth % 2 != 0) { // x - RED
            xmin = node.p.x();
            xmax = node.p.x();
            ymin = 0;
            ymax = 1;
            StdDraw.setPenColor(StdDraw.RED);
        } else { // y - BLUE
            xmin = 0;
            xmax = 1;
            ymin = node.p.y();
            ymax = node.p.y();
            StdDraw.setPenColor(StdDraw.BLUE);
        }

        StdDraw.setPenRadius(0.001);
        StdDraw.line(xmin, ymin, xmax, ymax);

        draw(node.lb, depth + 1);
        draw(node.rt, depth + 1);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
