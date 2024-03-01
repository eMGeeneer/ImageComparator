// Student student1 = new Student("Garrett Winslow", 300149981);
// Student student2 = new Student("Ming Gao", 300290294);
// Purpose: main program, runs the search

// TODO rewrite with a proper ui and so it doesn't just run from cmdline

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

public class SimilaritySearch {
	
	public static void main(String[] args) throws IOException {
		long timer = System.currentTimeMillis();
		String queryImg = args[0];
		String dataset = args[1];
		ColorHistogram queryHist = new ColorHistogram(3);
		if (queryImg.matches("(?i).*\\.(jpg|jpeg)$")) {
			queryHist.setImage(new ColorImage(queryImg));
			queryHist.save(queryImg + ".txt");
			File dataFolder = new File(dataset);
			File[] fileList = dataFolder.listFiles();
			PriorityQueue<HistWrapper> simages = new PriorityQueue<HistWrapper>(5);
			for (File i : fileList) {
				String filename = i.getCanonicalPath();
				ColorHistogram h;
				if (filename.matches("(?i).*\\.txt$")) {
					try {
						h = new ColorHistogram(filename);
						HistWrapper hw = new HistWrapper(queryHist.compare(h), i.getName().replaceFirst("(?i)\\.txt$", ""));
						if (simages.size() < 5) {
							simages.add(hw);
						}
						else if (simages.peek().compareTo(hw) < 0) {
							simages.poll();
							simages.add(hw);
						}
					}
					catch (NumberFormatException e) {
						
					}
				}
			}
			timer = System.currentTimeMillis() - timer;
			System.out.printf("The %d most similar images are:%n", simages.size());
			for (int i = simages.size(); i > 0; i--) {
				System.out.printf("%d. %s similarity%n", i, simages.poll());
			}
			System.out.printf("%dms", timer);
		}
		else {
			long totalTime = 0;
			File[] queryList = new File(queryImg).listFiles();
			for (File j : queryList) {
				timer = System.currentTimeMillis();
				queryImg = j.getCanonicalPath();
				if (queryImg.matches("(?i).*\\.(jpg|jpeg)$")) {
					queryHist.setImage(new ColorImage(queryImg));
					queryHist.save(queryImg + ".txt");
					File dataFolder = new File(dataset);
					File[] fileList = dataFolder.listFiles();
					PriorityQueue<HistWrapper> simages = new PriorityQueue<HistWrapper>(5);
					for (File i : fileList) {
						String filename = i.getCanonicalPath();
						ColorHistogram h;
						if (filename.matches("(?i).*\\.txt$")) {
							try {
								h = new ColorHistogram(filename);
								HistWrapper hw = new HistWrapper(queryHist.compare(h), i.getName().replaceFirst("(?i)\\.txt$", ""));
								if (simages.size() < 5) {
									simages.add(hw);
								}
								else if (simages.peek().compareTo(hw) < 0) {
									simages.poll();
									simages.add(hw);
								}
							}
							catch (NumberFormatException e) {
								
							}
						}
					}
					totalTime += System.currentTimeMillis() - timer;
					System.out.printf("The %d most similar images to %s are:%n", simages.size(), j.getName());
					for (int i = simages.size(); i > 0; i--) {
						System.out.printf("%d. %s similarity%n", i, simages.poll());
					}
				}
			}
			System.out.printf("%dms", totalTime);
		}
	}
}
