/**
 * Data structure for a clone
 * @author Ivan
 */
package data;
import java.io.Serializable;

public class Clone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2709203572650627631L;


	@SuppressWarnings("unused")
	private Clone() {
		
	}
	
	// language can be "JAVA" or "C"
	private String language;
	
	private int solutionSetNumber;

	// data of first file in clone 
	private int firstCloneFileNumber;
	private int firstCloneStartline;
	private int firstCloneEndline;

	// data of second file in clone
	private int secondCloneFileNumber;
	private int secondCloneStartline;
	private int secondCloneEndline;

	// coverage can be "FULL" or "PART"
	private String cloneCoverage;
	
	private int cloneType;
	
	
	public int getSolutionSetNumber() {
		return solutionSetNumber;
	}

	public int getFirstCloneFileNumber() {
		return firstCloneFileNumber;
	}

	public int getFirstCloneStartline() {
		return firstCloneStartline;
	}

	public int getFirstCloneEndline() {
		return firstCloneEndline;
	}

	public int getSecondCloneFileNumber() {
		return secondCloneFileNumber;
	}

	public int getSecondCloneStartline() {
		return secondCloneStartline;
	}

	public int getSecondCloneEndline() {
		return secondCloneEndline;
	}

	public int getCloneType() {
		return cloneType;
	}
	
	// constructor
	public Clone(String language, int solutionSetNumber, int firstCloneFileNumber, int firstCloneStartline,
		int firstCloneEndline, int secondCloneFileNumber, int secondCloneStartline,
		int secondCloneEndline, String cloneCoverage, int cloneType) {
		
		this.language = language;
		this.solutionSetNumber = solutionSetNumber;
		this.firstCloneFileNumber = firstCloneFileNumber;
		this.firstCloneStartline = firstCloneStartline;
		this.firstCloneEndline = firstCloneEndline;
		this.secondCloneFileNumber = secondCloneFileNumber;
		this.secondCloneStartline = secondCloneStartline;
		this.secondCloneEndline = secondCloneEndline;
		this.cloneCoverage = cloneCoverage;
		this.cloneType = cloneType;
		
	}

	public String getLanguage() {
		return language;
	}

	public String getCloneCoverage() {
		return cloneCoverage;
	}
}
