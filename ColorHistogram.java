// Student student1 = new Student("Garrett Winslow", 300149981);
// Student student2 = new Student("Ming Gao", 300290294);

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ColorHistogram {
	
	private int depth;
	private int[] histogram;
	private long pixelCount;
	
	public ColorHistogram(int d) {
		depth = d;
	}
	
	public ColorHistogram(String filename) throws IOException {
		BufferedReader saveFile = new BufferedReader(new FileReader(filename));
		String line = saveFile.readLine();
		histogram = new int[Integer.parseInt(line)];
		depth = (32 - Integer.numberOfLeadingZeros(histogram.length)) / 3;
		line = saveFile.readLine();
		StringTokenizer st = new StringTokenizer(line);
		//String[] strint = line.split(" ");
		//for (int i = 0; i < strint.length && i < histogram.length; i++) {
		for (int i = 0; st.hasMoreTokens() && i < histogram.length; i++) {
			histogram[i] = Integer.parseInt(st.nextToken());
			//histogram[i] = Integer.parseInt(strint[i]);
			pixelCount += histogram[i];
		}
		saveFile.close();
	}
	
	public void setImage(ColorImage image) {
		image.reduceColor(depth);
		pixelCount = image.getHeight() * image.getWidth();
		histogram = new int[1 << 3 * depth];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int[] colour = image.getPixel(i, j);
				histogram[colour[0] * (1 << 2 * depth) + colour[1] * (1 << depth) + colour[2]] += 1;
			}
		}
	}
	
	public double[] getHistogram() {
		double[] result = new double[histogram.length];
		double pix = pixelCount;
		for (int i = 0; i < result.length; i++) {
			result[i] = histogram[i] / pix;
		}
		return result;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public double compare(ColorHistogram hist) {
		long result = 0;
		if (depth == hist.depth) {
			for (int i = 0; i < histogram.length; i++) {
				long[] temp = {histogram[i] * hist.pixelCount, hist.histogram[i] * pixelCount};
				result += temp[0] < temp[1] ? temp[0] : temp[1];
			}
			return result / (double) (pixelCount * hist.pixelCount);
		}
		boolean greater = depth > hist.depth;
		int group = greater ? 1 << depth - hist.depth : 1 << hist.depth - depth;
		int[][] hists = greater ? new int[][]{histogram, hist.histogram} : new int[][]{hist.histogram, histogram};
		long[] pixelCounts = greater ? new long[]{pixelCount, hist.pixelCount} : new long[]{hist.pixelCount, pixelCount};
		for (int i = 0; i < hists[1].length; i++) {
			long sum = 0;
			for (int j = 0; j < group ; j++) {
				sum += hists[0][i * group + j];
			}
			long[] temp = {sum * pixelCounts[1], hists[1][i] * pixelCounts[0]};
			result += temp[0] < temp[1] ? temp[0] : temp[1];
		}
		return result / (double) (pixelCount * hist.pixelCount);
	}
	
	public void save(String filename) throws IOException {
		if (!filename.matches("(?i).*\\.txt$")) {
			filename += ".txt";
		}
		PrintWriter saveFile = new PrintWriter(new FileWriter(filename));
		saveFile.println(histogram.length);
		for (int i : histogram) {
			saveFile.printf("%d ", i);
		}
		saveFile.close();
	}
}