package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import control.CloneListCruncher;

public class SourceData {

	// storage for source file lengths (only code without header comments)
	List<FileLength> fileLengths = new ArrayList<FileLength>();

	// calculates the number of source code lines of a file (without header comments)
	private int calcFileLength(String filePath){
		int lines = 0;
		boolean skip = false;
		String read;
		File file2Count = new File(filePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file2Count));
			while ((read = reader.readLine()) != null) {
				if(skip){
					read.endsWith("*/");
					skip = false;
				}
				if( read.startsWith("/*")){
					skip = true;
				}
				if (!(lines == 0 && read.startsWith("//")))
					lines++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
	
	// calculates the number of source code lines of all source files
	public void calcFileLengths() {
		for (String language : CloneListCruncher.LANGUAGES) {
			for (int solutionSet = 1; solutionSet <= CloneListCruncher.MAXSOLUTIONSET; solutionSet++) {
				for (int solution = 1; solution <= CloneListCruncher.SAMPLESIZE; solution++) {
					String path = CloneListCruncher.SOURCEFOLDER + File.separator + language
							+ File.separator + solutionSet
							+ File.separator + "src" + File.separator
							+ String.format("%03d", solution) + '.' + language;
					int fileLength = calcFileLength(path);
					fileLengths.add(new FileLength(language, solutionSet, solution, fileLength));
				}
			}
		}
	}
	
	// search the list of file length for a specific file and returns the length value
	public int getFileLength(String language, int solutionSet, int solution) {
		for (FileLength fl : fileLengths) {
			if (fl.getLanguage().equals(language)
					&& fl.getSolutionSet() == solutionSet
					&& fl.getSolution() == solution) {
				return fl.getFileLength();
			}
		}
		return 0;
	}
}
