package control;

import static control.CloneListCruncher.*;
import static util.Helper.combineKeys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
				if (clone.getCloneCoverage().equalsIgnoreCase("FULL") == isFull
						&& (language.equalsIgnoreCase("ALL") || clone.getLanguage().equalsIgnoreCase(language))) {
					String line = "";
					line += clone.getLanguage().toLowerCase() + ",";
					line += clone.getSolutionSetNumber() + ",";
					line += clone.getLeftCloneFileNumber() + ",";
					line += clone.getRightCloneFileNumber() + ",";
					line += clone.getLeftCloneStartline() + ",";
					line += clone.getLeftCloneEndline() + ",";
					line += clone.getRightCloneStartline() + ",";
					line += clone.getRightCloneEndline() + ",";					
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
		Log.info("\nstarting csv export");

		// get correct clone table
		CloneTable ct = cloneTables.get(combineKeys(tool, language, solutionSet, isFull?"f":"p"));

		// file init
		String exportPath = EXPORTFOLDER + File.separator + language + "_" + solutionSet + "_" + fileNameSuffix; 
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

		Log.info("exported to " + csvFile.getAbsolutePath());
	}

	// save the results as csv
	private void exportCloneTables() {
		for (String tool : TOOLS) {
			for (String language : LANGUAGES) {
				for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {

					try {
						exportSingleCloneTable("clone-analysis-full.csv", tool, language, solutionSet, true);
						exportSingleCloneTable("clone-analysis-partial.csv", tool, language, solutionSet, false);
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
			for (String key : cloneTables.keySet()) {
				CloneTable ct = cloneTables.get(key);
				for (int metricNumber = 2; metricNumber <= 4; metricNumber++) {
					String line = key.substring(0, key.length()-3); // "part"->"p", "full"->"f"
					double metricValue = -1;
					switch (metricNumber) {
					case 2:
						key += "12;";
						metricValue = ct.recall12;
						break;
					case 3:
						key += "123;";
						metricValue = ct.recall123;
						break;
					case 4:
						key += "1234;";
						metricValue = ct.recall1234;
						break;
					}
					key += String.format(Locale.ENGLISH, "%f", metricValue) + "\n";
				}
			}

			csvRecall.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void exportAll() {
		exportCloneLists();
		exportCloneTables();
		exportRecallValues();
	}

}


