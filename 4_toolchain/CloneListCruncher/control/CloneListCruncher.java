package control;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analyzer.*;
import data.*;

public class CloneListCruncher {

	// path to local folders
	public final static String SOURCEFOLDER = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects";
	public final static String REPORTFOLDER = "/Users/ivan/Documents/Projekte/2014_GitHub_CloneStudy/3_cloning-reports";
	public final static String EXPORTFOLDER = "/Users/ivan/Documents/Projekte/2014_GitHub_CloneStudy/5_toolchain-output";

	// size parameters
	public final static int MAXSOLUTIONSET = 14; // maximum number of solution sets
	public final static int SAMPLESIZE = 100; // number of solutions per study object
	public final static String[] LANGUAGES = {"c", "java"};
	public final static String[] TOOLS = {"conqat", "deckard", "cccd"};
	
	// information about solution files and clones
	public static SourceData sourceData = new SourceData(); // data about the source files (solution)
	public static List<Clone> cloneList = new ArrayList<Clone>(); // a full list of all detected clones
	public static Map<String, CloneTable> cloneTables = new HashMap<String, CloneTable>(); // with combined key

	// initialize report analyzers and exporter
	private ReportAnalyzer conqat = new ConqatReportAnalyzer();
	//private ReportAnalyzer deckard = new DeckardReportAnalyzer();
	//private ReportAnalyzer cccd = new CccdReportAnalyzer();
	private Exporter exporter = new Exporter();

	// reads the study objects and the cloning reports and exports accumulated reports
	public void run() {
		
		// 1. analyze solutions (source code files)
		sourceData.calcFileLengths();
		
		// 2. initialize clone tables
		CloneTable.init();
		
		// 3. run analysis and add clone data to global clone list and clone tables
		conqat.analyzeReports();
		//deckard.analyzeReports();
		//cccd.analyzeReports();
		
		// 4. calculate the recall values
		CloneTable.calcRecalls();
		
		// 5. generate reports
		exporter.exportAll();
	}
	
	public static void main(String[] args) {
		CloneListCruncher clc = new CloneListCruncher();		
		clc.run();
	}

}
