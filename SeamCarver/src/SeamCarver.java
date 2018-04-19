import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private final Picture picture;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null) {
			throw new IllegalArgumentException();
		}
		this.picture = picture;
	}

	// current picture
	public Picture picture() {
		return picture;
	}

	// width of current picture
	public int width() {
		return picture.width();
	}

	// height of current picture
	public int height() {
		return picture.height();
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		return 0;
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

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		return null;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
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

	public static void main(String[] args) {
	}
}
