import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * CJExtractor - (Google) Code Jam Extractor
 * This is a small helper tool to download many solutions of a Google Code Jam contest.
 * The website must be saved as text (only tested with firefox).
 * (c) 2014 Ivan Bogicevic
 * 
 * @author ivan
 */
public class CJExtractor {

	// local path to the textfile of the webpage
	//final String WEBPAGE = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/gcjExtractor/testWebsite.txt";
	//String WEBPAGEPATH = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamJAVA/";
	String WEBPAGEPATH = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamC/";
	String FILESUFFIX = ".c";
	String WEBPAGEFILENAME = "1.txt";
	String WEBPAGE = "";

	// local path to the folder where the solution shall be saved
	//final String TARGETFOLDER = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/gcjExtractor";
	//String TARGETFOLDERPATH = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamJAVA/";
	String TARGETFOLDERPATH = "/Users/ivan/Documents/Projekte/2014_ICSE15_Study/study-objects/GoogleCodeJamC/";
	String TARGETFOLDER = "";

	// number of solution to select by random
	final int SAMPLESIZE = 110;

	// list of links to the zip-folder
	private List<String> ziplinks = new ArrayList<String>();

	// prints a message to the standard output
	private void log(String msg) {
		System.out.println(msg);
	}

	// prints a single star to the standard output (to show progress)
	private void logStar() {
		System.out.print("*");
	}

	// parse the webpage file and extract the zip-links 
	private List<String> parsePageFile(String path) {

		// random number generator used for sampling
		Random randomizer = new Random();

		// local storage of all lines, all links and selected links
		List<String> lines = new ArrayList<String>();
		List<String> linksAll = new ArrayList<String>();
		List<String> linksSel = new ArrayList<String>();

		// read all lines of webpage file
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// select lines that are solution links
		for (String line : lines) {
			if (line.startsWith("<http://code.google.com/codejam")) {
				// cut "<" and ">"
				String lineCut = line.substring(1,line.length()-1);
				linksAll.add(lineCut);
			}
		}
		log("found " + linksAll.size() + " solutions");

		// select solutions
		if (linksAll.size() < SAMPLESIZE) {
			// less solutions than demanded, select all
			linksSel.addAll(linksAll);
		} else {
			// enogh solutions, so select randomly SAMPLESIZE links
			for (int i = 1; i <= SAMPLESIZE; i++) {
				// choose a random element
				int randomIndex = randomizer.nextInt(linksAll.size());
				// move the element
				linksSel.add(linksAll.get(randomIndex));
				linksAll.remove(randomIndex);
			}
		}

		log("sampled " + linksSel.size() + " solutions");

		return linksSel;
	}

	// download a specific solution file (zip)
	public void downloadZip(String link, String targetFileName) {
		try {
			URL url = new URL(link);
			// open streams
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// copy data
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf))) {
				out.write(buf, 0, n);
			}
			// close streams
			out.close();
			in.close();
			// write output stream to disk
			byte[] response = out.toByteArray();
			FileOutputStream fos;
			new File(TARGETFOLDER + File.separator + "zip").mkdirs();
			fos = new FileOutputStream(TARGETFOLDER + File.separator + "zip" + File.separator + targetFileName);
			fos.write(response);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// unzip a specific zip-file
	public void unzip(String zipFileName, String targetFileName) {
		String zipFilePath = TARGETFOLDER + File.separator + "zip" + File.separator + zipFileName;
		byte[] buffer = new byte[1024];

		try{
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath));

			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze!=null) {

				// prepare filenames and streams
				//String fileName = ze.getName();
				//String fileSuffix;
				//if (fileName.contains(".")) {
				//	fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				//} else {
				//	fileSuffix = ".x";
				//}
				String newFileName = targetFileName + FILESUFFIX;
				File newFile = new File(TARGETFOLDER + File.separator + "src" + File.separator + newFileName);
				new File(TARGETFOLDER + File.separator + "src").mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);             

				// write source code file
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				// close stream
				fos.close();   
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex){
			ex.printStackTrace(); 
		}
	}

	// extract the links from the webpage file, download and unzip the solutions
	public void extract() {

		// parse the webpage file and remember the links to the zip-files
		log("extracting from " + WEBPAGE);
		ziplinks.addAll(parsePageFile(WEBPAGE));

		// download zip-files
		int i = 1;
		log("downloading...");
		for (String link : ziplinks) {
			downloadZip(link, String.format("%03d", i) + ".zip");
			i++;
			logStar();
		}
		log("\ndownloaded " + ziplinks.size() + " solutions");

		// unzip zip-files
		for (i = 1; i <= ziplinks.size(); i++) {
			unzip(String.format("%03d", i) + ".zip", String.format("%03d", i));
		}
		log("unzipped " + ziplinks.size() + " solutions");


	}    

	// print list of ziplinks
	/*log("list of ziplinks (" + ziplinks.size() + " items):");
	for (String link : ziplinks) {
		log(link);
	}*/

	public static void main(String[] args) {

		// iterate through all folder of one language
		for (int studyObjectNumber = 1; studyObjectNumber <= 14; studyObjectNumber++) {

			// initialize
			CJExtractor jce = new CJExtractor();
			jce.WEBPAGE = jce.WEBPAGEPATH + studyObjectNumber + File.separator + jce.WEBPAGEFILENAME;
			jce.TARGETFOLDER = jce.TARGETFOLDERPATH + studyObjectNumber;
			jce.log("--- CJExtractor started for studyObject no. " + studyObjectNumber + " ---");

			// extract the links from the html-files, download and unzip the solutions		
			jce.extract();

			// finalize
			jce.log("--- CJExtractor ended ---\n");
		}
	}

}