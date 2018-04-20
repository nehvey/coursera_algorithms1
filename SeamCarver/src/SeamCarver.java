import java.util.Arrays;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private final Picture picture;
	private final double[][] energyMatrix;
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
		return null;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		double[][] distTo;
		DirectedEdge[][] edgeTo;
		if (width > height) {
			distTo = new double[width][height * height];
			edgeTo = new DirectedEdge[width][height * height];
		} else {
			distTo = new double[width][width * height];
			edgeTo = new DirectedEdge[width][width * height];
		}

		for (int i = 0; i < width; i++) {
			for (int v = 1; v < distTo[i].length; v++) {
				distTo[i][v] = Double.POSITIVE_INFINITY;
			}
			distTo[i][0] = 0.0;
		}

		for (int col = 0; col < width; col++) {

			int x = col;
			for (int y = 1; y < height; y++) {
				int x1 = x - 1;
				int x2 = x;
				int x3 = x + 1;

				//1
				if (x > 0) {
					System.out.println(x1 + "," + y);
					if (distTo[col] > distTo[v] + e.weight()) {
						distTo[w] = distTo[v] + e.weight();
						edgeTo[w] = e;
					}
					x = x;
				}

				//2
				System.out.println(x2 + "," + y);
				x = x;

				//3
				if (x < width) {
					System.out.println(x3 + "," + y);
					x = x;
				}

			}

		}

		return null;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null) {
			throw new IllegalArgumentException();
		}

	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		if (seam == null) {
			throw new IllegalArgumentException();
		}
	}

	// relax edge e
	// private void relax(DirectedEdge e) {
	// int v = e.from(), w = e.to();
	// if (distTo[w] > distTo[v] + e.weight()) {
	// distTo[w] = distTo[v] + e.weight();
	// edgeTo[w] = e;
	// }
	// }

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

	public static void main(String[] args) {
		int a = 0;
	}
}
