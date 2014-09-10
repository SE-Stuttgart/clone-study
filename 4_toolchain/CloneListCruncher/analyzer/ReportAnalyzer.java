package analyzer;

import static control.CloneListCruncher.LANGUAGES;
import static control.CloneListCruncher.MAXSOLUTIONSET;
import static control.CloneListCruncher.cloneList;
import static control.CloneListCruncher.cloneTables;

import java.util.List;

import util.Log;
import util.Helper;
import data.Clone;
import data.CloneTable;

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

	// add a clone to the clonetable and swap left and right file if needed (unify)
	// returns the added clone or null if clone not added as new one
	public Clone addUnifiedCloneToTable(Clone clone) {
		if (clone.getLeftFile() == clone.getRightFile()) {
			// ignore clone within same file
			return null;
		}
		// swap indices if needed
		if (clone.getLeftFile() > clone.getRightFile()) {
			// swap indices
			int temp = clone.getRightFile();
			clone.setRightFile(clone.getLeftFile());
			clone.setLeftFile(temp);
			// swap start lines
			temp = clone.getRightStartline();
			clone.setRightStartline(clone.getLeftStartline());
			clone.setLeftStartline(temp);
			// swap end lines
			temp = clone.getRightEndline();
			clone.setRightEndline(clone.getLeftEndline());
			clone.setLeftEndline(temp);
		}
		// get the correct clone table
		CloneTable ct = cloneTables.get(Helper.combineKeys(getToolName(), clone.getLanguage(),
				clone.getSolutionSetNumber(), clone.isFull()));
		// overwrite old value correctly by taking the smaller one (2&2->2; 2&3->2; 3&2->2; 3&3->3)
		int oldType = ct.table[clone.getLeftFile()][clone.getRightFile()];
		int newType;
		if (oldType == 0) {
			// no old clone on that position
			newType = clone.getType();
		} else {
			// take smaller clone type
			newType = Math.min(oldType, clone.getType());
		}
		// write back new value
		clone.setType(newType);
		ct.table[clone.getLeftFile()][clone.getRightFile()] = newType;
		Log.debug("added result table[" + clone.getLeftFile() + "][" + clone.getRightFile() + "]=\"" + newType + "\"");
		// check if clone shall be ignored
		if (oldType == newType) {
			// ignore the clone
			return null;
		}
		return clone;
	}

	public abstract List<Clone> analyzeSolutionSetReport(String language, int solutionSet);

	public abstract String getToolName();

}
