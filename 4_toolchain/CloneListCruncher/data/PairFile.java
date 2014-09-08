package data;
/**
 * 
 * @author jan
 */
public class PairFile {

	public String fileName;

	public int cloneLength;

	public int lines;

	public boolean isFull = false;
	
	public boolean isType2 = true;

	public int subObjektnumber;
	
	public int startLine;
	
	public int endLine;

	public PairFile(String fileName, int cloneLength, int lines) {
		this.fileName = fileName;
		this.cloneLength = cloneLength;
		this.lines = lines;
		if (cloneLength == lines) {
			isFull = true;
		}

		String tempString = fileName.substring(0, (fileName.indexOf('.')));
		
		while (tempString.startsWith("0")) {
			tempString = tempString.substring(1);
		}

		subObjektnumber = Integer.parseInt(tempString);
		
		//System.out.println("conversion: " + fileName + "->" + subObjektnumber);

	}

}
