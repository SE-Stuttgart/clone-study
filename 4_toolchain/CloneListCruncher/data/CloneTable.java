package data;

import static control.CloneListCruncher.TOOLS;
import static control.CloneListCruncher.LANGUAGES;
import static control.CloneListCruncher.MAXSOLUTIONSET;
import static control.CloneListCruncher.SAMPLESIZE;
import static control.CloneListCruncher.cloneTables;
import static util.Helper.*;

public class CloneTable {

	// clone table: rows and columns are solutions (files);
	public int[][] table = new int[SAMPLESIZE+1][SAMPLESIZE+1];

	// recall metrices
	public double recall12 = 0;
	public double recall123 = 0;
	public double recall1234 = 0;

	// calculates the recall metrices for one clonetable
	public void calcRecall() {		
		int r12 = 0;
		int r123 = 0;
		int r1234 = 0;
		for (int i = 1; i <= SAMPLESIZE; i++) {
			for (int j = 1; j <= SAMPLESIZE; j++) {
				int type = table[i][j];
				if (type == 1 || type == 2) {
					r12++;
					r123++;
					r1234++;
				} else if (type == 3) {
					r123++;
					r1234++;
				} else if (type == 4) {
					r1234++;
				}
			}
		}
		recall12   = (double)r12   / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recall123  = (double)r123  / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
		recall1234 = (double)r1234 / (double)(SAMPLESIZE * (SAMPLESIZE-1) / 2);
	}

	// constructor fills this table with zeros
	public CloneTable() {
		// init clone table with 0
		for (int i = 0; i < SAMPLESIZE+1; i++) {
			for (int j = 0; j < SAMPLESIZE+1; j++) {
				this.table[i][j] = 0;
			}
		}
	}
	
	// create all tables
	public static void init() {
		for (String tool : TOOLS) {
			for (String language : LANGUAGES) {
				for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {
					// table for full clones
					cloneTables.put(combineKeys(tool, language, solutionSet, true), new CloneTable());
					// table for partial clones
					cloneTables.put(combineKeys(tool, language, solutionSet, false), new CloneTable());
				}
			}
		}	
	}
	
	// calculate recall values of all tables
	public static void calcRecalls() {
		for (String tool : TOOLS) {
			for (String language : LANGUAGES) {
				for (int solutionSet = 1; solutionSet <= MAXSOLUTIONSET; solutionSet++) {
					CloneTable ct;
					// recalls for full clone table
					ct = cloneTables.get(combineKeys(tool, language, solutionSet, true));
					ct.calcRecall();
					// recalls for partial clone table
					ct = cloneTables.get(combineKeys(tool, language, solutionSet, false));
					ct.calcRecall();
				}
			}
		}
	}
	
}
