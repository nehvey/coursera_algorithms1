public class CoordinateEdge {
    private final CoordinateVertex v;
    private final CoordinateVertex w;
    private final double weight;

    public CoordinateEdge(CoordinateVertex v, CoordinateVertex w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the tail vertex of the directed edge.
     *
     * @return the tail vertex of the directed edge
     */
    public CoordinateVertex from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     *
     * @return the head vertex of the directed edge
     */
    public CoordinateVertex to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     *
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }

}
