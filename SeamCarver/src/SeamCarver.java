import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    private Picture picture;
    private double[][] energyMatrix;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
        initEnergyMatrix();
    }

    private void initEnergyMatrix() {
        energyMatrix = new double[width][height];

        // init all with -1
        for (int col = 0; col < width; col++)
            Arrays.fill(energyMatrix[col], -1);

        for (int col = 0; col < width; col++)
            for (int row = 0; row < height; row++)
                energyMatrix[col][row] = energy(col, row);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (energyMatrix[x][y] != -1) {
            return energyMatrix[x][y];
        }

        if (x < 1 || x == width - 1 || y < 1 || y == height - 1) {
            return 1000;
        }
        return Math.sqrt(deltaX(x, y) + deltaY(x, y));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] tmp = energyMatrix;
        double[][] transposed = transpose(energyMatrix);
        energyMatrix = transposed;
        width = transposed.length;
        height = transposed[0].length;
        int[] verticalSeam = findVerticalSeam();
        energyMatrix = tmp;
        width = tmp.length;
        height = tmp[0].length;
        return verticalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[width][height];
        CoordinateEdge[][] edgeTo = new CoordinateEdge[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
            distTo[x][0] = 0.0;
        }

        int n = 0;
        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int x1 = x - 1;
                int x2 = x;
                int x3 = x + 1;

                //1
                if (x1 >= 0) {
//                    System.out.println("x1=" + x1 + "," + (y + 1));
                    relax(new CoordinateEdge(
                                    new CoordinateVertex(x, y),
                                    new CoordinateVertex(x1, y + 1),
                                    energy(x1, y + 1)),
                            distTo,
                            edgeTo);
                }


                //2
//                System.out.println("x2=" + x2 + "," + (y + 1));
                relax(new CoordinateEdge(
                                new CoordinateVertex(x, y),
                                new CoordinateVertex(x2, y + 1),
                                energy(x2, y + 1)),
                        distTo,
                        edgeTo);

                //3
                if (x3 < width) {
//                    System.out.println("x3=" + x3 + "," + (y + 1));
                    relax(new CoordinateEdge(
                                    new CoordinateVertex(x, y),
                                    new CoordinateVertex(x3, y + 1),
                                    energy(x3, y + 1)),
                            distTo,
                            edgeTo);
                }
                n++;
            }
        }

        double minWeight = Double.POSITIVE_INFINITY;
        int minX = 0;
        for (int x = 0; x < width; x++) {
            if (distTo[x][height - 1] < minWeight) {
                minWeight = distTo[x][height - 1];
                minX = x;
            }
        }

        return pathTo(minX, height - 1, edgeTo);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        picture = transposePicture(picture);
        width = picture.width();
        height = picture.height();

        removeVerticalSeam(seam);

        picture = transposePicture(picture);
        width = picture.width();
        height = picture.height();
        initEnergyMatrix();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }

        Picture newPicture = new Picture(width - 1, height);
        for (int row = 0; row < height; row++) {
            int newCol = 0;
            for (int col = 0; col < width; col++) {
                if (seam[row] != col) {
                    Color color = picture.get(col, row);
                    newPicture.set(newCol, row, color);
                    newCol++;
                }
            }
        }
        picture = newPicture;
        width--;
        initEnergyMatrix();
    }

    private void relax(CoordinateEdge e, double[][] distTo, CoordinateEdge[][] edgeTo) {
        CoordinateVertex v = e.from(), w = e.to();
        if (distTo[w.getX()][w.getY()] > distTo[v.getX()][v.getY()] + e.weight()) {
            distTo[w.getX()][w.getY()] = distTo[v.getX()][v.getY()] + e.weight();
            edgeTo[w.getX()][w.getY()] = e;
        }
    }

    private int[] pathTo(int x, int y, CoordinateEdge[][] edgeTo) {
        Stack<Integer> s = new Stack<>();
        for (CoordinateEdge e = edgeTo[x][y]; e != null; e = edgeTo[e.from().getX()][e.from().getY()]) {
            s.push(e.from().getX());
        }

        int yPos = 0;
        int[] path = new int[s.size() + 1];
        for (Integer xPos : s) {
            path[yPos] = xPos;
            yPos++;
        }

        // including destination vertex itself
        path[s.size()] = x;

        return path;
    }

    private double deltaX(int x, int y) {
        final int rgb1 = picture.getRGB(x + 1, y);
        final int r1 = (rgb1 >> 16) & 0xFF;
        final int g1 = (rgb1 >> 8) & 0xFF;
        final int b1 = (rgb1) & 0xFF;
        final int rgb2 = picture.getRGB(x - 1, y);
        final int r2 = (rgb2 >> 16) & 0xFF;
        final int g2 = (rgb2 >> 8) & 0xFF;
        final int b2 = (rgb2) & 0xFF;

        return Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2);
    }

    private double deltaY(int x, int y) {
        final int rgb1 = picture.getRGB(x, y + 1);
        final int r1 = (rgb1 >> 16) & 0xFF;
        final int g1 = (rgb1 >> 8) & 0xFF;
        final int b1 = (rgb1) & 0xFF;
        final int rgb2 = picture.getRGB(x, y - 1);
        final int r2 = (rgb2 >> 16) & 0xFF;
        final int g2 = (rgb2 >> 8) & 0xFF;
        final int b2 = (rgb2) & 0xFF;

        return Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2);
    }

    private double[][] transpose(double[][] a) {
        int n = a.length;
        int m = a[0].length;
        double[][] res = new double[m][n];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                res[y][x] = a[x][y];
            }
        }
        return res;
    }

    private Picture transposePicture(Picture a) {
        int n = a.width();
        int m = a.height();
        Picture newPicture = new Picture(m, n);
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                newPicture.set(y, x, a.get(x, y));
            }
        }
        return newPicture;
    }

    public static void main(String[] args) {
        int a = 0;
    }
}
