import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class PointSET {

    private final Set<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        points.forEach(Point2D::draw);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        final List<Point2D> pointsInside = new ArrayList<>();
        points.forEach(p -> {
            if (rect.contains(p)) {
                pointsInside.add(p);
            }
        });

        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.stream().map(point -> new AbstractMap.SimpleEntry<>(point, p.distanceSquaredTo(point))).min(
                (p1, p2) -> p1.getValue() > p2.getValue() ? 1 : -1).get().getKey();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}