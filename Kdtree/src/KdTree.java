import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

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
        if (!contains(p)) {
            root = insert(root, p, 1);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(p, root, 1);
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
        return range(rect, root, new ArrayList<>(), 1);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return nearest(p, root, root.p, 1);
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

    private boolean contains(Point2D p, Node node, int depth) {
        if (node == null) {
            return false;
        }
        if (node.p.equals(p)) {
            return true;
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

        return cmp < 0
                // go left
                ? contains(p, node.lb, depth + 1)
                // go right
                : contains(p, node.rt, depth + 1);
    }

    private void draw(Node node, int depth) {
        if (node == null) {
            return;
        }

        double xmin, ymin, xmax, ymax;
        xmin = ymin = 0;
        xmax = ymax = 1;
        RectHV limits = getLimits(root, node.p, 1, xmin, ymin, xmax, ymax);

        if (depth % 2 != 0) { // x - RED

            xmin = node.p.x();
            ymin = limits.ymin();
            xmax = node.p.x();
            ymax = limits.ymax();
            StdDraw.setPenColor(StdDraw.RED);
        } else { // y - BLUE

            xmin = limits.xmin();
            ymin = node.p.y();
            xmax = limits.xmax();
            ymax = node.p.y();
            StdDraw.setPenColor(StdDraw.BLUE);
        }

        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        draw(node.lb, depth + 1);
        draw(node.rt, depth + 1);
    }

    private RectHV getLimits(Node node, Point2D p, int depth, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            return new RectHV(0, 0, 1, 1);
        }

        if (node.p.equals(p)) {
            return new RectHV(xmin, ymin, xmax, ymax);
        }

        int cmp = 0;

        if (depth % 2 != 0) { // compare x for odd node
            if (p.x() < node.p.x()) {
                cmp = -1;
            }
            if (p.x() > node.p.x()) {
                cmp = +1;
            }

            // left boundary
            if (p.x() > node.p.x() && p.x() - node.p.x() < p.x() - xmin) {
                xmin = node.p.x();
            }
            // right boundary
            if (p.x() < node.p.x() && node.p.x() - p.x() < xmax - p.x()) {
                xmax = node.p.x();
            }
        } else {// compare y for even node
            if (p.y() < node.p.y()) {
                cmp = -1;
            }
            if (p.y() > node.p.y()) {
                cmp = +1;
            }

            // top boundary
            if (p.y() > node.p.y() && p.y() - node.p.y() < p.y() - ymin) {
                ymin = node.p.y();
            }
            // bottom boundary
            if (p.y() < node.p.y() && node.p.y() - p.y() < ymax - p.y()) {
                ymax = node.p.y();
            }
        }

        return cmp < 0
                // go left
                ? getLimits(node.lb, p, depth + 1, xmin, ymin, xmax, ymax)
                // go right
                : getLimits(node.rt, p, depth + 1, xmin, ymin, xmax, ymax);
    }

    private Iterable<Point2D> range(RectHV rect, Node node, List<Point2D> points, int depth) {
        if (node == null) {
            return points;
        }

        if (rect.contains(node.p)) {
            points.add(node.p);
        }

        if (depth % 2 != 0) { // compare x for odd node
            if (rect.xmin() < node.p.x()) {
                range(rect, node.lb, points, depth + 1);
            }
            if (rect.xmax() >= node.p.x()) {
                range(rect, node.rt, points, depth + 1);
            }
        } else {// compare y for even node
            if (rect.ymin() < node.p.y()) {
                range(rect, node.lb, points, depth + 1);
            }
            if (rect.ymax() >= node.p.y()) {
                range(rect, node.rt, points, depth + 1);
            }
        }
        return points;
    }

    private Point2D nearest(Point2D p, Node node, Point2D nearest, int depth) {
        if (node == null) {
            return nearest;
        }

        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(nearest)) {
            nearest = node.p;
        }
        // critical optimisation doesn't work
//        else if (!node.p.equals(nearest)) {
//            return nearest;
//        }

        if (depth % 2 != 0) { // compare x for odd node
            if (p.x() < node.p.x()) {
                nearest = nearest(p, node.lb, nearest, depth + 1);
                nearest = nearest(p, node.rt, nearest, depth + 1);
            } else {
                nearest = nearest(p, node.rt, nearest, depth + 1);
                nearest = nearest(p, node.lb, nearest, depth + 1);
            }
        } else {// compare y for even node
            if (p.y() < node.p.y()) {
                nearest = nearest(p, node.lb, nearest, depth + 1);
                nearest = nearest(p, node.rt, nearest, depth + 1);
            } else {
                nearest = nearest(p, node.rt, nearest, depth + 1);
                nearest = nearest(p, node.lb, nearest, depth + 1);
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
