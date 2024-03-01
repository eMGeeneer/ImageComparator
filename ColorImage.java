// Student student1 = new Student("Garrett Winslow", 300149981);
// Student student2 = new Student("Ming Gao", 300290294);

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class ColorImage {
	
	private int width, height, depth;
	private int[][][] image;
	
	public ColorImage(String filename) throws IOException {
		if (filename.matches("(?i).*\\.(jpg|jpeg)$")) {
			depth = 8;
			File file = new File(filename);
			BufferedImage img = ImageIO.read(file);
			image = new int[img.getWidth()][img.getHeight()][3];
			width = image.length;
			height = image[0].length;
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					int rgb = img.getRGB(x, y);
					image[x][y] = new int[]{(rgb & 0xFF0000) >> 2 * depth, (rgb & 0xFF00) >> depth, rgb & 0xFF};
				}
			}
		}
		else {
			throw new IOException("Invalid file type. Only .jpg and .jpeg extensions are accepted.");
		}
	}
	
	public void reduceColor(int d) {
		int reduction = depth - d;
		for (int[][] i : image) {
			for (int[] j : i) {
				for (int k = 0; k < 3; k++) {
					j[k] >>= reduction;
				}
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int[] getPixel(int x, int y) {
		return image[x][y];
	}
}
