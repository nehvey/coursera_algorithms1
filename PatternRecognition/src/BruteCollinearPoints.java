import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final Point[] points;
    private final LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < len - 3; i++) {
            for (int j = i + 1; j < len - 2; j++) {
                for (int k = j + 1; k < len - 1; k++) {
                    for (int l = k + 1; l < len; l++) {
                        Point[] potentialLine = {points[i], points[j], points[k], points[l]};
                        Arrays.sort(potentialLine);
                        double slope = potentialLine[0].slopeTo(potentialLine[1]);
                        if (potentialLine[0].slopeTo(potentialLine[2]) == slope
                                && potentialLine[0].slopeTo(potentialLine[3]) == slope) {
                            segments.add(new LineSegment(potentialLine[0], potentialLine[3]));
//                            System.out.println("line segment - " + potentialLine[0].toString() + potentialLine[1] + potentialLine[2] + potentialLine[3]);
                        }
//                        System.out.println(points[i].toString() + points[j] + points[k] + points[l]);
                    }
                }
            }
        }
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        Point[] points = new Point[]{new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5)};
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        brute.segments();
    }
}
