package reportAnalyzer;
/*
* @author Jan
*/

import java.util.List;
import java.util.Locale;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import data.Clone;
import data.CloneData;
import data.Pair;
import data.PairFile;

public class DeckardReportAnalyzer extends ReportAnalyzer {

	static final String TOPFOLDER = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamC/";
	static final String filenameType1and2 = "post_cluster_vdb_23_0_allg_1.0_30";
	static final String filenameType3 = "post_cluster_vdb_23_0_allg_0.95_30";
	static boolean isJava = false;

	// number of solutions per study object
	static final int SAMPLESIZE = 100;

	static final float MAXRECALL = ((float) SAMPLESIZE * (float) (SAMPLESIZE - 1)) / 2.0f;

	static int countPairsPartT2;
	static int countPairsFullT2;
	static int countPairsPartT3;
	static int countPairsFullT3;
	// clone table: rows and columns are solutions (files);
	private static String[][] tableFull;
	private static String[][] tablePart;

	private static List<Float> t12RecallsPart = new ArrayList<Float>();
	private static List<Float> t123RecallsFull = new ArrayList<Float>();
	private static List<Float> t12RecallsFull = new ArrayList<Float>();
	private static List<Float> t123RecallsPart = new ArrayList<Float>();

	// local path to the conqat results folder
	final static String OUTPUTFOLDER = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/CruncherResults";

	// Minimal required length of clone
	final static int THRESHOLD = 6;

	static List<Pair> pairsT2 = new ArrayList<Pair>();
	static List<Pair> pairsT3 = new ArrayList<Pair>();
	static CloneData clones = new CloneData();

	public static void main(String[] args) {
		if (TOPFOLDER.endsWith("C/")) {
			isJava = false;
		}
		for (int i = 1; i <= 14; i++) {
			String currentFolder = TOPFOLDER + "/" + i + "/results_Deckard/";
			File fileT2 = new File(currentFolder + filenameType1and2);
			File fileT3 = new File(currentFolder + filenameType3);
			try {
				System.out.println("@" + i);
				pairsT2 = getClonePairs(fileT2, true, i);

				pairsT3 = getClonePairs(fileT3, false, i);

				tableFull = new String[SAMPLESIZE + 1][SAMPLESIZE + 1];
				tablePart = new String[SAMPLESIZE + 1][SAMPLESIZE + 1];
				intTables();
				countPairsFullT2 = 0;
				countPairsPartT2 = 0;
				countPairsFullT3 = 0;
				countPairsPartT3 = 0;
				fillTables();
				exportCsv(i, tableFull, true);
				exportCsv(i, tablePart, false);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		writeAllRecall();
		writeSerData();

	}

	private static int comparePairFiles(PairFile first, PairFile sec) {
		int comp = 0;

		if (first.subObjektnumber > sec.subObjektnumber) {
			comp = 1;
		}
		if (first.subObjektnumber < sec.subObjektnumber) {
			comp = -1;
		}

		return comp;
	}

	private static void fillTables() {
		String lang = "C";
		if (isJava) {
			lang = "JAVA";
		}

		for (Pair allClonePairs : pairsT2) {
			for (int index = 0; (index + 1) < allClonePairs.clonePairs.size(); index++) {
				PairFile first = allClonePairs.clonePairs.get(index);
				PairFile sec = allClonePairs.clonePairs.get(index + 1);
				String coverage = "PART";
				if (comparePairFiles(first, sec) > 0) { // Make sure the first
														// instance is allays
														// less or equal the
														// second
					PairFile temp = sec;
					sec = first;
					first = temp;
				}
				if (first.cloneLength > THRESHOLD) {
					if (first.isFull && sec.isFull) {
						coverage = "FULL";

						if (tableFull[first.subObjektnumber][sec.subObjektnumber]
								.isEmpty()) {
							countPairsFullT2++;

							tableFull[first.subObjektnumber][sec.subObjektnumber] = "2";
							Clone currentClone = new Clone(lang,
									allClonePairs.solutionSetNumber,
									first.subObjektnumber, first.startLine,
									first.endLine, sec.subObjektnumber,
									sec.startLine, sec.endLine, coverage, 2);
							clones.add(currentClone);
						}

					} else {
						if (tablePart[first.subObjektnumber][sec.subObjektnumber]
								.isEmpty()) {
							countPairsPartT2++;

							tablePart[first.subObjektnumber][sec.subObjektnumber] = "2";
							Clone currentClone = new Clone(lang,
									allClonePairs.solutionSetNumber,
									first.subObjektnumber, first.startLine,
									first.endLine, sec.subObjektnumber,
									sec.startLine, sec.endLine, coverage, 2);
							clones.cloneList.add(currentClone);
						}

					}

				}

			}

		}
		// Iterate through all Type3 findings. Only write to table if not
		// already covered by T2
		for (Pair allClonePairs : pairsT3) {
			for (int index = 0; (index + 1) < allClonePairs.clonePairs.size(); index++) {
				PairFile first = allClonePairs.clonePairs.get(index);
				PairFile sec = allClonePairs.clonePairs.get(index + 1);

				String coverage = "PART";
				if (comparePairFiles(first, sec) > 0) { // Make sure the first
														// instance is allays
														// less or equal the
														// second
					PairFile temp = sec;
					sec = first;
					first = temp;
				}
				if (first.cloneLength > THRESHOLD) {
					if (first.isFull && sec.isFull) {
						coverage = "FULL";
						if (tableFull[first.subObjektnumber][sec.subObjektnumber]
								.isEmpty()) {
							countPairsFullT3++;
							System.out.println("Full in tables");
							tableFull[first.subObjektnumber][sec.subObjektnumber] = "3";
							Clone currentClone = new Clone(lang,
									allClonePairs.solutionSetNumber,
									first.subObjektnumber, first.startLine,
									first.endLine, sec.subObjektnumber,
									sec.startLine, sec.endLine, coverage, 2);
							clones.cloneList.add(currentClone);
						}

					} else {

						
						if (tablePart[first.subObjektnumber][sec.subObjektnumber]
								.isEmpty()) {
							countPairsPartT3++;

							tablePart[first.subObjektnumber][sec.subObjektnumber] = "3";
							Clone currentClone = new Clone(lang,
									allClonePairs.solutionSetNumber,
									first.subObjektnumber, first.startLine,
									first.endLine, sec.subObjektnumber,
									sec.startLine, sec.endLine, coverage, 2);
							clones.cloneList.add(currentClone);
						}

					}
				}
			}

		}

	}

	public static ArrayList<Pair> getClonePairs(File file, boolean isType2,
			int solutionNumber) throws FileNotFoundException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		String fileName = null;
		ArrayList<Pair> pairs = new ArrayList<Pair>();
		PairFile pFile;
		Pair clonePair = new Pair();
		int startingLine = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					int startingIndexFN = line.indexOf("src/") + 4;
					int endingIndexFN = startingIndexFN + 5;
					if (isJava) {
						endingIndexFN = endingIndexFN + 3; // because .java is
															// longer then .c
					}
					fileName = line.substring(startingIndexFN, endingIndexFN);
					String[] fullString = line.split(":");
					int cloneLength = Integer
							.parseInt(fullString[3].split(" ")[0]);
					startingLine = Integer.parseInt(fullString[2]);
					// System.out.println("clonel.: "+cloneLength+" startl.: "+startingLine);

					int linesInFile = getLinesOfFile(file, fileName);
					pFile = new PairFile(fileName, cloneLength, linesInFile);
					if (!isType2) {
						pFile.isType2 = false;
					}
					pFile.startLine = startingLine;
					pFile.endLine = startingLine + cloneLength;
					clonePair.clonePairs.add(pFile);
					// System.out.println(fileName + " " + cloneLength + " "
					// + linesInFile);
				} else {
					clonePair.solutionSetNumber = solutionNumber;
					pairs.add(clonePair);
					clonePair = new Pair();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pairs;
	}

	private static int getLinesOfFile(File file, String fileName) {
		int lines = 0;
		boolean skip = false;
		String read;
		String path = file.getAbsolutePath();
		int cutIndex = path.indexOf("results_Deckard");
		path = path.substring(0, cutIndex) + "src/" + fileName;
		File file2Count = new File(path.trim());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					file2Count));
			while ((read = reader.readLine()) != null) {
				if (skip) {
					read.endsWith("*/");
					skip = false;
				}
				if (read.startsWith("/*") && lines == 0) {
					skip = true;
				}
				if (!(lines == 0 && (read.startsWith("//") || read
						.startsWith("#"))))
					lines++;
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lines;
	}

	// save the results as csv
	private static void exportCsv(int studyObjectNumber, String[][] table,
			boolean isFull) throws IOException {
		String targetFolder;
		// file init
		if (isJava) {
			new File(OUTPUTFOLDER + File.separator + "Java" + File.separator
					+ studyObjectNumber + File.separator).mkdirs();
			targetFolder = OUTPUTFOLDER + File.separator + "Java"
					+ File.separator + studyObjectNumber + File.separator;
		} else {
			new File(OUTPUTFOLDER + File.separator + "C" + File.separator
					+ studyObjectNumber + File.separator).mkdirs();
			targetFolder = OUTPUTFOLDER + File.separator + "C" + File.separator
					+ studyObjectNumber + File.separator;
		}
		File csvFile;
		BufferedWriter csv;
		if (isFull) {
			csvFile = new File(targetFolder + "clone-analysisFull.csv");
			csv = new BufferedWriter(new FileWriter(csvFile));
		} else {
			csvFile = new File(targetFolder + "clone-analysisPart.csv");
			csv = new BufferedWriter(new FileWriter(csvFile));
		}

		// write column label (right file)
		csv.write(",");
		for (int column = 1; column <= SAMPLESIZE; column++) {
			csv.write(Integer.toString(column));
			if (column < SAMPLESIZE) {
				csv.write(",");
			}
		}
		csv.write("\n");

		// fill csv line by line
		for (int line = 1; line <= SAMPLESIZE; line++) {
			// write line label (left file)
			csv.write(line + ",");
			// fill line
			for (int column = 1; column <= SAMPLESIZE; column++) {
				if (table != null) {
					csv.write(table[line][column]);
				} else {
					System.out.println("Table null!");
				}
				if (column < SAMPLESIZE) {
					csv.write(",");
				}
			}
			csv.write("\n");

		}
		float recallT2;
		float recallT3; // is the found T2 plus the additional T3 which not have
						// been T2 already
		if (isFull) {
			recallT2 = (float) countPairsFullT2 / (float) MAXRECALL;
			recallT3 = ((float) countPairsFullT2 + (float) countPairsFullT3)
					/ (float) MAXRECALL;
			t12RecallsFull.add(recallT2);
			t123RecallsFull.add(recallT3);
		} else {
			recallT2 = (float) countPairsPartT2 / (float) MAXRECALL;
			recallT3 = ((float) countPairsPartT2 + (float) countPairsPartT3)
					/ (float) MAXRECALL;
			t12RecallsPart.add(recallT2);
			t123RecallsPart.add(recallT3);
		}

		csv.write("Recall T1/2: ");
		csv.write(String.format("%f", recallT2));
		csv.write("\n");

		csv.write("Recall T1/2/3: ");
		csv.write(String.format("%f", recallT3));
		csv.write("\n");

		// close writer

		csv.close();

	}

	private static void intTables() {
		for (int i = 0; i < SAMPLESIZE + 1; i++) {
			for (int j = 0; j < SAMPLESIZE + 1; j++) {
				tableFull[i][j] = "";
				tablePart[i][j] = "";
			}
		}
	}

	private static void writeAllRecall() {
		String targetFolder;
		String lang;
		// file init
		if (isJava) {
			new File(OUTPUTFOLDER + File.separator + "JavaRecall"
					+ File.separator).mkdirs();
			targetFolder = OUTPUTFOLDER + File.separator + "JavaRecall"
					+ File.separator;
			lang = "JAVA";
		} else {
			new File(OUTPUTFOLDER + File.separator + "CRecall" + File.separator)
					.mkdirs();
			targetFolder = OUTPUTFOLDER + File.separator + "CRecall"
					+ File.separator;
			lang = "C";
		}
		File csvRecallFile = new File(targetFolder
				+ "clone-analysis-summary.csv");
		BufferedWriter csvRecall = null;
		System.out
				.println("Writing TOTAL RECALL to " + csvRecallFile.getPath());
		try {
			csvRecall = new BufferedWriter(new FileWriter(csvRecallFile));

			for (int index = 0; index <= 13; index++) {
				int i = index + 1;
				// write the recall values to the recall cvs file; SO = study
				// object

				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "p12; "
						+ String.format(Locale.ENGLISH, "%f",
								t12RecallsPart.get(index)) + "\n");
				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "p123; "
						+ String.format(Locale.ENGLISH, "%f",
								t123RecallsPart.get(index)) + "\n");
				// Note: write entry into csv-file for f1234 with same value as f123
				// because we have no type 4 detection
				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "p1234; "
						+ String.format(Locale.ENGLISH, "%f",
								t123RecallsPart.get(index)) + "\n");
				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "f12; "
						+ String.format(Locale.ENGLISH, "%f",
								t12RecallsFull.get(index)) + "\n");
				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "f123; "
						+ String.format(Locale.ENGLISH, "%f",
								t123RecallsFull.get(index)) + "\n");
				// Note: write entry into csv-file for f1234 with same value as f123
				// because we have no type 4 detection
				csvRecall.write("deckard;"
						+ lang.toLowerCase()
						+ ";"
						+ i
						+ ";"
						+ "f1234; "
						+ String.format(Locale.ENGLISH, "%f",
								t123RecallsFull.get(index)) + "\n");
			}
			csvRecall.flush();
			csvRecall.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void writeSerData() {
		System.out.println("Serializing Data...");

		String outputName = "deckardDataC.ser";

		if (isJava) {
			outputName = "deckardDataJAVA.ser";
		}

		try (OutputStream file = new FileOutputStream(OUTPUTFOLDER + "/"
				+ outputName);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);) {
			output.writeObject(clones);
			output.close();
		} catch (IOException ex) {
			System.out.println("SERIAL KILLER: Failed to write serial file.");
		}
		System.out.println("Done.");
	}



}
