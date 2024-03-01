// Student student1 = new Student("Garrett Winslow", 300149981);
// Student student2 = new Student("Ming Gao", 300290294);
// Purpose: A wrapper to make the histograms of images easier to compare

public class HistWrapper implements Comparable<HistWrapper> {
		
	private double sim;
	private String filename;
	
	public HistWrapper(double n, String s) {
		sim = n;
		filename = s;
	}

	// Compares two HistWrappers
	// @returns 1 if this has a higher similarity to the query image than the other HistWrapper,
	// -1 if this has a lower similarity, and 0 if they are the same
	public int compareTo(HistWrapper h) {
		if (sim > h.sim) {
			return 1;
		}
		return sim < h.sim ? -1 : 0;
	}
	
	public String toString() {
		return String.format("%s, %f", filename, sim);
	}
}
