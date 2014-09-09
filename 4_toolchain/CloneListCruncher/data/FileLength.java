package data;

public class FileLength {
	
	private String language;
	private int solutionSet;
	private int solution;
	private int fileLength;
	
	public FileLength(String language, int solutionSet, int solution, int fileLength) {
		this.language = language;
		this.solutionSet = solutionSet;
		this.solution = solution;
		this.fileLength = fileLength;
	}

	public String getLanguage() {
		return language;
	}

	public int getSolutionSet() {
		return solutionSet;
	}

	public int getSolution() {
		return solution;
	}

	public int getFileLength() {
		return fileLength;
	}

}
