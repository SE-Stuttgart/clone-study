package reportAnalyzer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import control.MethodParser;
import data.Clone;
import data.CloneData;

public class CccdReportAnalyzer {

	// maximum number of solutions
	final static int MAXSOLUTIONS = 14;
	//final static int MAXSOLUTIONS = 1;

	// maximum number of allowed leveshtein distance so that the clone is accepted
	final static int MAXLEVENSHTEIN = 35;

	// number of lines to ignore at the beginning of a source file when rating for full/partial
	// the number of starting lines is additionally calculated dynamically (by method countStartingLines)
	final static int FULLCLONE_FILEBEGIN_TOLERANCE = 8;

	// number of lines to ignore between twi functions of a source file when rating for full/partial 
	final static int FULLCLONE_BETWEENFUNC_TOLERANCE = 8;

	// local path to the cccd results folder
	static String inputFolder = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/CCCD";

	// local path to the study objects
	static String sourceFolder = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamC";

	// local paths to the serialization files (results of the other tools)
	static String inputFile1 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/conqat/conqatDataC.ser";
	static String inputFile3 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/CruncherResults/deckardDataC.ser";

	// number of solutions per study object
	final int SAMPLESIZE = 100;

	// clone table: rows and columns are solutions (files);
	private int[][] tableP = new int[SAMPLESIZE+1][SAMPLESIZE+1]; // for partial clones
	private int[][] tableF = new int[SAMPLESIZE+1][SAMPLESIZE+1]; // for partial clones

	// recall metrices
	private double recallP12 = 0;
	private double recallP123 = 0;
	private double recallP1234 = 0;
	private double recallF12 = 0;
	private double recallF123 = 0;
	private double recallF1234 = 0;

	// list of all clone data of all solution sets (only needed for exported serialization file)
	private static CloneData myCloneData = new CloneData();

	// extended information about clone data (only needed for partial/full rating)
	// every clone file pair is associated with a list if line numbers of all clones between these files
	// the key is the filepair as String "sxf1xf2" with solutions number s, filenumber f1 and filenumber f2
	private static Map<String, Set<Integer>> clonedLineNumbersLeft = new HashMap<String, Set<Integer>>();
	private static Map<String, Set<Integer>> clonedLineNumbersRight = new HashMap<String, Set<Integer>>();

	// list of the clone result of the other tools
	private static CloneData externalCloneData = new CloneData();

	// helper function to generate the key for the clonedLineNumbers map
	private String getKey(int solutionSet, int leftFileNumber, int rightFileNumber) {
		return solutionSet + "x" + leftFileNumber + "x" + rightFileNumber;
	}

	// prints a message to the standard output
	private static void log(String msg) {
		System.out.println(msg);
	}

	// prints a single star to the standard output (to show progress)
	private static void logStar() {
		System.out.print("*");
	}

	// helper function that counts the number of line in a file (source: http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java)
	public static int countLines(String filename) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			is.close();
			return (count == 0 && !empty) ? 1 : count;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// helper function that counts the number of lines before the first method in a file
	public static int countStartingLines(String filename) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
					if (c[i] == '{') {
						is.close();
						return (count == 0 && !empty) ? 1 : count;
					}
				}
			}
			is.close();
			return (count == 0 && !empty) ? 1 : count;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
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
		String key = getKey(clone.getSolutionSetNumber(), clone.getFirstCloneFileNumber(), clone.getSecondCloneFileNumber());
		// initialize sets if needed
		if (clonedLineNumbersLeft.get(key) == null) {
			clonedLineNumbersLeft.put(key, new HashSet<Integer>());
		}
		if (clonedLineNumbersRight.get(key) == null) {
			clonedLineNumbersRight.put(key, new HashSet<Integer>());
		}
		// add all lines to the maps
		for (int line = clone.getFirstCloneStartline(); line <= clone.getFirstCloneEndline(); line++) {
			clonedLineNumbersLeft.get(key).add(line);
		}
		for (int line = clone.getSecondCloneStartline(); line <= clone.getSecondCloneEndline(); line++) {
			clonedLineNumbersRight.get(key).add(line);
		}
	}

	// analyzed a single clone pair and add its result to the table
	private void analyzeClonePair(String line, int solutionSetNumber) {

		// ignore first line (header)
		if (line.startsWith("Study")) {
			return;
		}

		// split the line into its elements
		line = line.replace('.', ',');
		line = line.replace('-', ',');
		String[] parts = line.split(",");
		// special case: strange result line
		if (parts.length < 9) {
			log("ERROR: Strange cccd result line: " + line);
			return;
		}

		// ignore clone with to high levenshtein distance
		if (Integer.parseInt(parts[8]) > MAXLEVENSHTEIN) {
			//log ("line ignored, levenshtein " + parts[8] + " is higher than " + MAXLEVENSHTEIN);
			return;
		}

		// example line: "8,52,work.txt-8,67,main.txt,58,"
		//               "8 52 work txt 8 67 main txt 58"
		// (Study-Object[0],File[1],Method[2],File[5],Method[6],LevenDistance[8])
		int cloneLeftFileName = Integer.parseInt(parts[1]);
		int cloneRightFileName = Integer.parseInt(parts[5]);
		String cloneLeftMethodName = parts[2];
		String cloneRightMethodName = parts[6];

		// get start and endlines of the methods
		//log("trying to get lines within file " + cloneLeftFileName + " in method " + cloneLeftMethodName);
		//log("trying to get lines within file " + cloneRightFileName + " in method " + cloneRightMethodName);

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
		String pathToLeftFile = sourceFolder + File.separator + solutionSetNumber + File.separator
				+ "src" + File.separator + String.format("%03d", cloneLeftFileName) + ".c";
		String pathToRightFile = sourceFolder + File.separator + solutionSetNumber + File.separator
				+ "src" + File.separator + String.format("%03d", cloneRightFileName) + ".c";

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
		for (Clone clone : externalCloneData.cloneList) {
			// regard only clones within same solution set and the same file combination
			if (solutionSetNumber != clone.getSolutionSetNumber()
					|| cloneLeftFileName != clone.getFirstCloneFileNumber()
					|| cloneRightFileName != clone.getSecondCloneFileNumber()) {
				continue;
			}
			log("found same file combination in database");

			// take new number if clone is within both methods
			// parser that finds the line number of the methods
			MethodParser methodParser1 = new MethodParser();
			MethodParser methodParser2 = new MethodParser();
			boolean isInside1 = methodParser1.isInsideBounds(cloneLeftMethodName, pathToLeftFile, clone.getFirstCloneStartline() - 2,
					clone.getFirstCloneEndline() + 2);
			boolean isInside2 = methodParser2.isInsideBounds(cloneRightMethodName, pathToRightFile, clone.getSecondCloneStartline() - 2,
					clone.getSecondCloneEndline() + 2);
			if (isInside1 && isInside2) {
				type = Math.min(type, clone.getCloneType());
				log("found a smaller clone type in database");
				// remember also new line information
				cloneLeftStartLine = methodParser1.getStartLineOfFunc();
				cloneLeftEndLine = methodParser1.getEndLineOfFunc();
				cloneRightStartLine = methodParser2.getStartLineOfFunc();
				cloneRightEndLine = methodParser2.getEndLineOfFunc();
			}
		}

		// add result to table
		if (cloneLeftFileName > cloneRightFileName) {
			// swap indices
			int temp = cloneRightFileName;
			cloneRightFileName = cloneLeftFileName;
			cloneLeftFileName = temp;
			// swap start lines
			temp = cloneRightStartLine;
			cloneRightStartLine = cloneLeftStartLine;
			cloneLeftStartLine = temp;
			// swap end lines
			temp = cloneRightEndLine;
			cloneRightEndLine = cloneLeftEndLine;
			cloneLeftEndLine = temp;

		}
		if (cloneLeftFileName == cloneRightFileName) {
			// ignore clone within same file
			return;
		}
		int oldResult;
		int newResult;

		isFull = isFullClone(solutionSetNumber, cloneLeftFileName, pathToLeftFile, cloneRightFileName, pathToRightFile);
		if (!isFull) {
			oldResult = tableP[cloneLeftFileName][cloneRightFileName];
			// overwrite old value correctly by taking the smaller one (2&2->2; 2&3->2; 3&2->2; 3&3->3)
			if (oldResult == 0) {
				newResult = type;
			} else {
				newResult = Math.min(oldResult, type);
			}
			tableP[cloneLeftFileName][cloneRightFileName] = newResult;
			log("added result tableP[" + cloneLeftFileName + "][" + cloneRightFileName + "]=\"" + newResult + "\"");
		} else {
			oldResult = tableF[cloneLeftFileName][cloneRightFileName];
			// overwrite old value correctly by taking the smaller one (2&2->2; 2&3->2; 3&2->2; 3&3->3)
			if (oldResult == 0) {
				newResult = type;
			} else {
				newResult = Math.min(oldResult, type);
			}
			tableF[cloneLeftFileName][cloneRightFileName] = newResult;
			log("added result tableF[" + cloneLeftFileName + "][" + cloneRightFileName + "]=\"" + newResult + "\"");
		}

		// remember clone in global clone data list (later needed for serialization)
		String cloneKind = "PART";
		if (isFull) {
			cloneKind = "FULL";
		}
		Clone clone = new Clone("C", solutionSetNumber, cloneLeftFileName,
				cloneLeftStartLine, cloneLeftEndLine, cloneRightFileName, cloneRightStartLine,
				cloneRightEndLine, cloneKind, newResult); 
		myCloneData.cloneList.add(clone);

		// remember clone also for later full/partial rating
		rememberCloneLineNumbers(clone);
	}

	// read a cccd report and analyze the results
	private void parseReport(int solutionSetNumber) {
		// the local path to the cccd result csv
		String inputPath = inputFolder + File.separator + solutionSetNumber + "_comparisionReport.csv";

		// parse the file
		File inputFile = new File(inputPath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;

			while ((line = reader.readLine()) != null) {
				//log("analyzing clone " + line);
				analyzeClonePair(line, solutionSetNumber);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// save the results as csv
	private void exportCsv(int solutionSetNumber) throws IOException {

		log("\nstarting csv export");

		// file init
		File csvFileP = new File(inputFolder + File.separator + solutionSetNumber + "_clone-analysis-partial.csv");
		File csvFileF = new File(inputFolder + File.separator + solutionSetNumber + "_clone-analysis-full.csv");
		BufferedWriter csvP = new BufferedWriter(new FileWriter(csvFileP));
		BufferedWriter csvF = new BufferedWriter(new FileWriter(csvFileF));

		// write column label (right file)
		csvP.write(",");
		csvF.write(",");
		for (int column = 1; column <= SAMPLESIZE; column++) {
			csvP.write(Integer.toString(column));
			csvF.write(Integer.toString(column));
			if (column < SAMPLESIZE) {
				csvP.write(",");
				csvF.write(",");
			}
		}
		csvP.write("\n");
		csvF.write("\n");

		// fill csv line by line
		for (int line = 1; line <= SAMPLESIZE; line++) {
			// write line label (left file)
			csvP.write(line + ",");
			csvF.write(line + ",");
			// fill line			
			for (int column = 1; column <= SAMPLESIZE; column++) {
				int p = tableP[line][column];
				if (p != 0) {
					csvP.write("" + p);
				}
				if (column < SAMPLESIZE) {
					csvP.write(",");
				}
				int f = tableF[line][column];
				if (f != 0) {
					csvF.write("" + f);
				}
				if (column < SAMPLESIZE) {
					csvF.write(",");
				}

			}
			csvP.write("\n");
			csvF.write("\n");
		}

		// export recall metrices
		csvP.write("Recall T1/2: " + String.format("%f", recallP12) + "\n");
		csvP.write("Recall T1/2/3: " + String.format("%f", recallP123) + "\n");
		csvP.write("Recall T1/2/3/4: " + String.format("%f", recallP1234) + "\n");
		log("exported recall values p12=" + recallP12 + " p123=" + recallP123 + " p1234=" + recallP1234);
		csvF.write("Recall T1/2: " + String.format("%f", recallF12) + "\n");
		csvF.write("Recall T1/2/3: " + String.format("%f", recallF123) + "\n");
		csvF.write("Recall T1/2/3/4: " + String.format("%f", recallF1234) + "\n");
		log("exported recall values f12=" + recallF12 + " f123=" + recallF123 + " f1234=" + recallF1234);

		// close writer
		csvP.close();
		csvF.close();

		log("exported to " + csvFileP.getAbsolutePath());
		log("exported to " + csvFileF.getAbsolutePath() + "\n");
	}

	// calculates the recall metrices
	private void calcRecall() {
		int p12 = 0;
		int p123 = 0;
		int p1234 = 0;
		int f12 = 0;
		int f123 = 0;
		int f1234 = 0;
		for (int i = 1; i <= SAMPLESIZE; i++) {
			for (int j = 1; j <= SAMPLESIZE; j++) {
				int p = tableP[i][j];
				if (p == 1 || p == 2) {
					p12++;
					p123++;
					p1234++;
				} else if (p == 3) {
					p123++;
					p1234++;
				} else if (p == 4) {
					p1234++;
				}
				int f = tableF[i][j];
				if (f == 1 || f == 2) {
					f12++;
					f123++;
					f1234++;
				} else if (f == 3) {
					f123++;
					f1234++;
				} else if (f == 4) {
					f1234++;
				}
			}
		}
		recallP12  = (double)p12  / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recallP123 = (double)p123 / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recallP1234 = (double)p1234 / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recallF12  = (double)f12  / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recallF123 = (double)f123 / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recallF1234 = (double)f1234 / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
	}

	// read a cccd report, analyze the results and save the results as csv
	private void transformReport(int solutionSetNumber) {

		// read a cccd report, analyze the results
		parseReport(solutionSetNumber);

		// calculate recall
		calcRecall();

		// save the results as csv
		try {
			exportCsv(solutionSetNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// constructor
	public CccdReportAnalyzer() {
		// init table
		for (int i = 0; i < SAMPLESIZE+1; i++) {
			for (int j = 0; j < SAMPLESIZE+1; j++) {
				tableP[i][j] = 0;
				tableF[i][j] = 0;
			}
		}
	}

	// writes all clone data to a serialized file (the CCCD-Analyzer needs that data);
	public static void serializeCloneData() {
		// open stream
		OutputStream file;
		try {
			file = new FileOutputStream(inputFolder + File.separator + "cccdDataC.ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(myCloneData);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deserialize(String filename) {

		try {
			CloneData localCloneData;
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			localCloneData = (CloneData) in.readObject();
			in.close();
			fileIn.close();
			// remember all deserialized clones
			externalCloneData.cloneList.addAll(localCloneData.cloneList);

		} catch(IOException i) {
			i.printStackTrace();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
		logStar();

	}

	public static void main(String[] args) {

		// create csv-file for recall metrices
		File csvRecallFile = new File(inputFolder + File.separator + "clone-analysis-summary.csv");
		BufferedWriter csvRecall = null;
		try {
			csvRecall = new BufferedWriter(new FileWriter(csvRecallFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// deserialize clone data from the other tools
		deserialize(inputFile1);
		deserialize(inputFile3);
		log("\ndeserializing finished");

		// iterate through all folder of one language
		for (int solutionSetNumber = 1; solutionSetNumber <= MAXSOLUTIONS; solutionSetNumber++) {

			// initialize
			CccdReportAnalyzer cra = new CccdReportAnalyzer();
			log("--- cccdReportAnalyzer started for solution no. " + solutionSetNumber + " ---");

			// read a cccd report, analyze the results and save the results as csv
			cra.transformReport(solutionSetNumber);

			// write the recall values to the recall cvs file; SO = study object
			try {
				csvRecall.write("cccd;c;" + solutionSetNumber + ";p12; "+ String.format(Locale.ENGLISH, "%f", cra.recallP12) + "\n");
				csvRecall.write("cccd;c;" + solutionSetNumber + ";p123; "+ String.format(Locale.ENGLISH, "%f", cra.recallP123) + "\n");
				csvRecall.write("cccd;c;" + solutionSetNumber + ";p1234; "+ String.format(Locale.ENGLISH, "%f", cra.recallP1234) + "\n");
				csvRecall.write("cccd;c;" + solutionSetNumber + ";f12; "+ String.format(Locale.ENGLISH, "%f", cra.recallF12) + "\n");
				csvRecall.write("cccd;c;" + solutionSetNumber + ";f123; "+ String.format(Locale.ENGLISH, "%f", cra.recallF123) + "\n");
				csvRecall.write("cccd;c;" + solutionSetNumber + ";f1234; "+ String.format(Locale.ENGLISH, "%f", cra.recallF1234) + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			// finalize
			log("--- cccdReportAnalyzer ended ---\n");
		}

		try {
			csvRecall.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// writes all clone data to a serialized file;
		serializeCloneData();
	}

}