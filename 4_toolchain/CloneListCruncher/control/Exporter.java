package control;

import static control.CloneListCruncher.*;
import static util.Helper.combineKeys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import util.Log;
import data.Clone;
import data.CloneTable;

public class Exporter {

	private void exportSingleCloneList(String fileName, String language, boolean isFull) {
		String exportPath = EXPORTFOLDER + File.separator + fileName; 
		File exportFile = new File(exportPath);
		try {
			BufferedWriter exportWriter = new BufferedWriter(new FileWriter(exportFile));
			for (Clone clone : cloneList) {
				if (clone.isFull() == isFull
						&& (language.equalsIgnoreCase("ALL") || clone.getLanguage().equalsIgnoreCase(language))) {
					String line = "";
					line += clone.getLanguage().toLowerCase() + ",";
					line += clone.getSolutionSet() + ",";
					line += clone.getLeftFile() + ",";
					line += clone.getRightFile() + ",";
					line += clone.getLeftStartline() + ",";
					line += clone.getLeftEndline() + ",";
					line += clone.getRightStartline() + ",";
					line += clone.getRightEndline() + ",";					
					line += "\n";
					exportWriter.write(line);
				}
			}
			exportWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportCloneLists() {
		exportSingleCloneList("allClonePairsFull.csv", "ALL", true);
		Log.info("exported to allClonePairsFull.csv");

		for (String language : LANGUAGES) {
			exportSingleCloneList("allClonePairsPartial" + language + ".csv", language, false);
			Log.info("exported to allClonePairsPartial" + language + ".csv");
		}
	}

	private void exportSingleCloneTable(String fileNameSuffix, String tool, String language,
			int solutionSet, boolean isFull) throws IOException {
		Log.debug("starting csv export");

		// get correct clone table
		CloneTable ct = cloneTables.get(combineKeys(tool, language, solutionSet, isFull));

		// file init
		String exportPath = EXPORTFOLDER + File.separator + tool + File.separator
				+ language + "_" + solutionSet + "_" + fileNameSuffix; 
		File csvFile = new File(exportPath);
		BufferedWriter csv = new BufferedWriter(new FileWriter(csvFile));

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
				int val;
				val = ct.table[line][column];
				if (val != 0) {
					csv.write("" + val);
				}
				if (column < SAMPLESIZE) {
					csv.write(",");
				}
			}
			csv.write("\n");
		}

		// close writer
		csv.close();

		Log.debug("exported to " + csvFile.getAbsolutePath());
	}

	// save the results as csv
	private void exportCloneTables() {
		Log.info("exporting csv-files");
		for (String tool : TOOLS) {
			for (String language : LANGUAGES) {
				// abort if combination has to be excluded
				if (tool.equals("cccd") && language.equals("java")) {
					continue;
				}
				Log.star();
				for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {
					// export table
					try {
						exportSingleCloneTable("clone-analysis-full.csv", tool, language, solutionSet, true);
						exportSingleCloneTable("clone-analysis-part.csv", tool, language, solutionSet, false);
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	// save all recall values to a single csv-file
	private void exportRecallValues() {

		// create csv-file for recall metrices
		File csvRecallFile = new File(EXPORTFOLDER + File.separator + "allRecalls.csv");
		BufferedWriter csvRecall = null;
		try {
			csvRecall = new BufferedWriter(new FileWriter(csvRecallFile));

			// write header of csv-file
			csvRecall.write("tool;lang;solset;type;recall\n");

			// write the recall values to the recall cvs-file;
			for (String tool : TOOLS) {
				for (String language : LANGUAGES) {
					for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {
						for (int fullVar = 0; fullVar <= 1; fullVar++) {
							// inner loop changes between full=false (partial) and full=true (full)
							boolean full = (fullVar == 1);
							// get the clonetable for this combination
							String key = combineKeys(tool, language, solutionSet, full);
							CloneTable ct = cloneTables.get(key);
							for (int metricNumber = 2; metricNumber <= 4; metricNumber++) {
								String line = key;
								double metricValue = -1;
								switch (metricNumber) {
								case 2:
									line += "12; ";
									metricValue = ct.recall12;
									break;
								case 3:
									line += "123; ";
									metricValue = ct.recall123;
									break;
								case 4:
									line += "1234; ";
									metricValue = ct.recall1234;
									break;
								}
								line += String.format(Locale.ENGLISH, "%f", metricValue) + "\n";
								csvRecall.write(line);
							}						
						}
					}
				}
			}

			csvRecall.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exportAll() {
		Log.info("--- Exporter startet ---");
		exportCloneLists();
		exportCloneTables();
		exportRecallValues();
		Log.info("\n--- Exporter ended ---");
	}

}


