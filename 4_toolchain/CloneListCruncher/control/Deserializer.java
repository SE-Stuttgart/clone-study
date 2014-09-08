package control;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import data.Clone;
import data.CloneData;


public class Deserializer {

	// local paths to the serialization files
	static String inputFile1 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/conqat/conqatDataC.ser";
	static String inputFile2 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/conqat/conqatDataJAVA.ser";
	static String inputFile3 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/CruncherResults/deckardDataC.ser";
	static String inputFile4 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/CruncherResults/deckardDataJAVA.ser";
	static String inputFile5 = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/CCCD/cccdDataC.ser";

	static String exportFolder = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-results/";


	private static CloneData cloneData = new CloneData();

	// prints a message to the standard output
	private static void log(String msg) {
		System.out.println(msg);
	}

	// prints a single star to the standard output (to show progress)
	private static void logStar() {
		System.out.print("*");
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
			cloneData.cloneList.addAll(localCloneData.cloneList);
			
		} catch(IOException i) {
			i.printStackTrace();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
		logStar();

	}

	public static void export(String filePath, boolean isFull) {
		File exportFile = new File(filePath);
		try {
			BufferedWriter exportWriter = new BufferedWriter(new FileWriter(exportFile));
			for (Clone clone : cloneData.cloneList) {
				if (clone.getCloneCoverage().equalsIgnoreCase("FULL") == isFull) {
					String line = "";
					line += clone.getLanguage().toLowerCase() + ",";
					line += clone.getSolutionSetNumber() + ",";
					line += clone.getFirstCloneFileNumber() + ",";
					line += clone.getSecondCloneFileNumber() + ",";
					line += clone.getFirstCloneStartline() + ",";
					line += clone.getFirstCloneEndline() + ",";
					line += clone.getSecondCloneStartline() + ",";
					line += clone.getSecondCloneEndline() + ",";					
					line += "\n";
					exportWriter.write(line);
				}
			}
			exportWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		//deserialize(inputFile1);
		//deserialize(inputFile2);
		//deserialize(inputFile3);
		//deserialize(inputFile4);
		deserialize(inputFile5);
		log("\ndeserializing finished");
		
		export(exportFolder + "allClonePairsFull.csv", true);
		log("exported to allClonePairsFull.csv");

		export(exportFolder + "allClonePairsPartial.csv", false);
		log("exported to allClonePairsPartial.csv");
	}

}
