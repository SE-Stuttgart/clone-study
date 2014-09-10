package analyzer;
import static control.CloneListCruncher.*;
import static util.Helper.*;

import java.io.*;
import java.util.*;

import util.Log;
import control.MethodParser;
import data.Clone;

public class CccdReportAnalyzer extends ReportAnalyzer {

	// maximum number of allowed leveshtein distance so that the clone is accepted
	final static int MAXLEVENSHTEIN = 35;

	// number of lines to ignore at the beginning of a source file when rating for full/partial
	// the number of starting lines is additionally calculated dynamically (by method countStartingLines)
	final static int FULLCLONE_FILEBEGIN_TOLERANCE = 8;

	// number of lines to ignore between two functions of a source file when rating for full/partial 
	final static int FULLCLONE_BETWEENFUNC_TOLERANCE = 8;

	// extended information about clone data (only needed for partial/full rating)
	// every clone file pair is associated with a list if line numbers of all clones between these files
	// the key is the filepair as String "sxf1xf2" with solutions number s, filenumber f1 and filenumber f2
	private static Map<String, Set<Integer>> clonedLineNumbersLeft = new HashMap<String, Set<Integer>>();
	private static Map<String, Set<Integer>> clonedLineNumbersRight = new HashMap<String, Set<Integer>>();

	// helper function to generate the key for the clonedLineNumbers map
	private String getKey(int solutionSet, int leftFileNumber, int rightFileNumber) {
		return solutionSet + "x" + leftFileNumber + "x" + rightFileNumber;
	}

	// checks whether all clone pairs between two files cover the whole files
	private boolean isFullClone(int solutionSetNumber, int leftFileNumber, String pathToLeftFile, int rightFileNumber, String pathToRightFile) {
		int numberOfLinesLeft = countLines(pathToLeftFile);
		int numberOfLinesRight = countLines(pathToRightFile);
		int currentGapSize = 0;
		String key = getKey(solutionSetNumber, leftFileNumber, rightFileNumber);
		Set<Integer> lineNumbersLeft = clonedLineNumbersLeft.get(key);
		Set<Integer> lineNumbersRight = clonedLineNumbersRight.get(key);
		if (lineNumbersLeft == null || lineNumbersRight == null) {
			return false;
		}

		// check for too large gaps in left file
		for (int line = Math.max(countStartingLines(pathToLeftFile), FULLCLONE_FILEBEGIN_TOLERANCE); line <= numberOfLinesLeft; line++) {
			if (lineNumbersLeft.contains(line)) {
				currentGapSize = 0;
			} else {
				currentGapSize++;
			}
			if (currentGapSize > FULLCLONE_BETWEENFUNC_TOLERANCE) {
				// too large gap, clone is not full
				return false;
			}
		}
		// check for too large gaps in left file
		currentGapSize = 0;
		for (int line = Math.max(countStartingLines(pathToRightFile), FULLCLONE_FILEBEGIN_TOLERANCE); line <= numberOfLinesRight; line++) {
			if (lineNumbersRight.contains(line)) {
				currentGapSize = 0;
			} else {
				currentGapSize++;
			}
			if (currentGapSize > FULLCLONE_BETWEENFUNC_TOLERANCE) {
				// too large gap, clone is not full
				return false;
			}
		}
		return true;
	}

	// remember line number of that clone and add the information to the two cloneLineNumbers maps
	private void rememberCloneLineNumbers(Clone clone) {
		String key = getKey(clone.getSolutionSetNumber(), clone.getLeftFile(), clone.getRightFile());
		// initialize sets if needed
		if (clonedLineNumbersLeft.get(key) == null) {
			clonedLineNumbersLeft.put(key, new HashSet<Integer>());
		}
		if (clonedLineNumbersRight.get(key) == null) {
			clonedLineNumbersRight.put(key, new HashSet<Integer>());
		}
		// add all lines to the maps
		for (int line = clone.getLeftStartline(); line <= clone.getLeftEndline(); line++) {
			clonedLineNumbersLeft.get(key).add(line);
		}
		for (int line = clone.getRightStartline(); line <= clone.getRightEndline(); line++) {
			clonedLineNumbersRight.get(key).add(line);
		}
	}

	// analyze a single clone pair and add its result to the table
	private Clone analyzeClonePair(String line, int solutionSet) {

		// ignore first line (header)
		if (line.startsWith("Study")) {
			return null;
		}

		// split the line into its elements
		line = line.replace('.', ',');
		line = line.replace('-', ',');
		String[] parts = line.split(",");
		// special case: strange result line
		if (parts.length < 9) {
			Log.info("ERROR: Strange cccd result line: " + line);
			return null;
		}

		// ignore clone with to high levenshtein distance
		if (Integer.parseInt(parts[8]) > MAXLEVENSHTEIN) {
			//log ("line ignored, levenshtein " + parts[8] + " is higher than " + MAXLEVENSHTEIN);
			return null;
		}

		// example line: "8,52,work.txt-8,67,main.txt,58,"
		//               "8 52 work txt 8 67 main txt 58"
		// (Study-Object[0],File[1],Method[2],File[5],Method[6],LevenDistance[8])
		int cloneLeftFileName = Integer.parseInt(parts[1]);
		int cloneRightFileName = Integer.parseInt(parts[5]);
		String cloneLeftMethodName = parts[2];
		String cloneRightMethodName = parts[6];

		// get start and endlines of the methods
		Log.debug("trying to get lines within file " + cloneLeftFileName + " in method " + cloneLeftMethodName);
		Log.debug("trying to get lines within file " + cloneRightFileName + " in method " + cloneRightMethodName);

		//if (line.equals("1,90,ispoweroftwo,txt,1,38,g,txt,35")) {
		//	log ("BREAKPOINT");
		//}

		int cloneLeftStartLine = 0;
		int cloneLeftEndLine = 0;
		int cloneRightStartLine = 0;
		int cloneRightEndLine = 0;
		int type = 4;
		boolean isFull = false;

		// generate paths (needed for methodparser)
		String pathToFolder = SOURCEFOLDER + File.separator + "c" + File.separator
				+ solutionSet + File.separator + "src" + File.separator;
		String pathToLeftFile =  pathToFolder + String.format("%03d", cloneLeftFileName) + ".c";
		String pathToRightFile = pathToFolder + String.format("%03d", cloneRightFileName) + ".c";

		// get line numbers from methodparser and line counter
		MethodParser methodParserL = new MethodParser();
		MethodParser methodParserR = new MethodParser();
		methodParserL.calcFunctionLines(cloneLeftMethodName, pathToLeftFile);
		methodParserR.calcFunctionLines(cloneRightMethodName, pathToRightFile);
		cloneLeftStartLine = methodParserL.getStartLineOfFunc();
		cloneLeftEndLine = methodParserL.getEndLineOfFunc();
		cloneRightStartLine = methodParserR.getStartLineOfFunc();
		cloneRightEndLine = methodParserR.getEndLineOfFunc();

		// search for a lower clone type in existing data (from other tools)
		for (Clone clone : cloneList) {
			// regard only clones within same solution set and the same file combination
			if (solutionSet != clone.getSolutionSetNumber()
					|| cloneLeftFileName != clone.getLeftFile()
					|| cloneRightFileName != clone.getRightFile()) {
				continue;
			}
			Log.debug("found same file combination in database");

			// take new number if clone is within both methods
			// parser that finds the line number of the methods
			MethodParser methodParser1 = new MethodParser();
			MethodParser methodParser2 = new MethodParser();
			boolean isInside1 = methodParser1.isInsideBounds(cloneLeftMethodName, pathToLeftFile, clone.getLeftStartline() - 2,
					clone.getLeftEndline() + 2);
			boolean isInside2 = methodParser2.isInsideBounds(cloneRightMethodName, pathToRightFile, clone.getRightStartline() - 2,
					clone.getRightEndline() + 2);
			if (isInside1 && isInside2) {
				type = Math.min(type, clone.getType());
				Log.debug("found a smaller clone type in database");
				// remember also new line information
				cloneLeftStartLine = methodParser1.getStartLineOfFunc();
				cloneLeftEndLine = methodParser1.getEndLineOfFunc();
				cloneRightStartLine = methodParser2.getStartLineOfFunc();
				cloneRightEndLine = methodParser2.getEndLineOfFunc();
			}
		}

		// check whether both files of the clones are fully covered
		isFull = isFullClone(solutionSet, cloneLeftFileName, pathToLeftFile, cloneRightFileName, pathToRightFile);

		// add result to table
		Clone clone = new Clone("c", solutionSet, cloneLeftFileName,
				cloneLeftStartLine, cloneLeftEndLine, cloneRightFileName, cloneRightStartLine,
				cloneRightEndLine, isFull, type);
		Clone unifiedClone = addUnifiedCloneToTable(clone);

		// remember clone also for later full/partial rating
		if (unifiedClone != null) {
			rememberCloneLineNumbers(unifiedClone);
		}

		return unifiedClone;
	}

	@Override
	// read a cccd report and analyze the results
	public List<Clone> analyzeSolutionSetReport(String language, int solutionSet) {

		// the list of clones for a specific tool, language and solution set
		List<Clone> localCloneList = new ArrayList<Clone>();

		// the local path to the cccd result csv
		String inputPath = REPORTFOLDER + File.separator + getToolName() + File.separator
				+ "c" + File.separator + solutionSet + "_comparisonReport.csv";

		// show progress
		Log.star();		
		// parse the file
		File inputFile = new File(inputPath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;
			while ((line = reader.readLine()) != null) {
				Log.debug("analyzing clone " + line);
				Clone clone = analyzeClonePair(line, solutionSet);
				if (clone != null) {
					// remember clone in local clone list (added later to the global one)
					localCloneList.add(clone);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return localCloneList;
	}

	@Override
	public String getToolName() {
		return "cccd";
	}

}