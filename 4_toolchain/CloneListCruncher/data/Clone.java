/**
 * Data structure for a clone
 * @author Ivan
 */
package data;

public class Clone {

	// language can be "JAVA" or "C"
	private String language;
	
	private int solutionSetNumber;

	// data of first (left) file in clone 
	private int leftCloneFileNumber;
	private int leftCloneStartline;
	private int leftCloneEndline;

	// data of second (right) file in clone
	private int rightCloneFileNumber;
	private int rightCloneStartline;
	private int rightCloneEndline;

	// whether this clone covers both files totally
	private boolean isFull;
	
	// number of clone type (1-4)
	private int cloneType;
	
	// getter
	
	public String getLanguage() {
		return language;
	}

	public int getSolutionSetNumber() {
		return solutionSetNumber;
	}

	public int getLeftCloneFileNumber() {
		return leftCloneFileNumber;
	}

	public int getLeftCloneStartline() {
		return leftCloneStartline;
	}

	public int getLeftCloneEndline() {
		return leftCloneEndline;
	}

	public int getRightCloneFileNumber() {
		return rightCloneFileNumber;
	}

	public int getRightCloneStartline() {
		return rightCloneStartline;
	}

	public int getRightCloneEndline() {
		return rightCloneEndline;
	}

	public boolean isFull() {
		return isFull;
	}

	public int getCloneType() {
		return cloneType;
	}
	
	// private empty constructor
	@SuppressWarnings("unused")
	private Clone() {
		
	}
	
	// constructor
	public Clone(String language, int solutionSetNumber, int leftCloneFileNumber, int leftCloneStartline,
		int leftCloneEndline, int rightCloneFileNumber, int rightCloneStartline,
		int rightCloneEndline, boolean isFull, int cloneType) {
		
		this.language = language;
		this.solutionSetNumber = solutionSetNumber;
		this.leftCloneFileNumber = leftCloneFileNumber;
		this.leftCloneStartline = leftCloneStartline;
		this.leftCloneEndline = leftCloneEndline;
		this.rightCloneFileNumber = rightCloneFileNumber;
		this.rightCloneStartline = rightCloneStartline;
		this.rightCloneEndline = rightCloneEndline;
		this.isFull = isFull;
		this.cloneType = cloneType;
		
	}
}
