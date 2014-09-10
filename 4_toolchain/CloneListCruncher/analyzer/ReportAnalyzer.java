package analyzer;

import static control.CloneListCruncher.LANGUAGES;
import static control.CloneListCruncher.MAXSOLUTIONSET;
import static control.CloneListCruncher.cloneList;

import java.util.List;
import util.Log;
import data.Clone;

public abstract class ReportAnalyzer {

	// call the specific analyzer for all languages and solution sets
	public void analyzeReports() {
		// initialize
		Log.info("--- ReportAnalyzer for " + getToolName() + " started ---\n");

		// read the reports, analyze and remember the results
		for (String language : LANGUAGES) {
			for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {
				cloneList.addAll(analyzeSolutionSetReport(language, solutionSet));
			}
		}

		// finalize
		Log.info("\n--- ReportAnalyzer for " + getToolName() + " ended ---\n");
	}

	public abstract List<Clone> analyzeSolutionSetReport(String language, int solutionSet);

	public abstract String getToolName();

}
