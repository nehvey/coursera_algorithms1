import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private final Point[] points;
    private final LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        List<Point> pointsList = new ArrayList<>();
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
            if (pointsList.contains(point)) {
                throw new IllegalArgumentException();
            }
            pointsList.add(point);
        }

        this.points = pointsList.toArray(new Point[pointsList.size()]);
        this.segments = calculateSegments();
        this.numberOfSegments = segments.length;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private LineSegment[] calculateSegments() {
        List<LineSegment> segments = new ArrayList<>();
        int len = points.length;

        for (Point point : points) {
            Comparator<Point> pointComparator = point.slopeOrder();
            Point[] rest = Arrays.copyOfRange(points, 0, len);
            Arrays.sort(rest, pointComparator);
            int n = 0;
            for (int k = 0; k < rest.length; k++) {
                if (!point.equals(rest[k])) {
                    double slope = point.slopeTo(rest[k]);
                    if (k + 1 < rest.length && point.slopeTo(rest[k + 1]) == slope) {
                        n++;
                    } else {
                        if (n > 1) {
                            segments.add(new LineSegment(point, rest[k]));
                        }
                        n = 0;
                    }
                }
            }
        }
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
