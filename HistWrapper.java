// Student student1 = new Student("Garrett Winslow", 300149981);
// Student student2 = new Student("Ming Gao", 300290294);

public class HistWrapper implements Comparable<HistWrapper> {
		
	private double sim;
	private String filename;
	
	public HistWrapper(double n, String s) {
		sim = n;
		filename = s;
	}
	
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
