/**
 * Data structure for a clone
 * @author Ivan
 */
package data;

public class Clone {

	// language can be "JAVA" or "C"
	private String language;
	
	private int solutionSet;

	// data of first (left) file in clone 
	private int leftFile;
	private int leftStartline;
	private int leftEndline;

	// data of second (right) file in clone
	private int rightFile;
	private int rightStartline;
	private int rightEndline;

	// whether this clone covers both files totally
	private boolean isFull;
	
	// number of clone type (1-4)
	private int type;
	
	// getter and setter
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getSolutionSet() {
		return solutionSet;
	}

	public void setSolutionSet(int solutionSet) {
		this.solutionSet = solutionSet;
	}

	public int getLeftFile() {
		return leftFile;
	}

	public void setLeftFile(int leftFile) {
		this.leftFile = leftFile;
	}

	public int getLeftStartline() {
		return leftStartline;
	}

	public void setLeftStartline(int leftStartline) {
		this.leftStartline = leftStartline;
	}

	public int getLeftEndline() {
		return leftEndline;
	}

	public void setLeftEndline(int leftEndline) {
		this.leftEndline = leftEndline;
	}

	public int getRightFile() {
		return rightFile;
	}

	public void setRightFile(int rightFile) {
		this.rightFile = rightFile;
	}

	public int getRightStartline() {
		return rightStartline;
	}

	public void setRightStartline(int rightStartline) {
		this.rightStartline = rightStartline;
	}

	public int getRightEndline() {
		return rightEndline;
	}

	public void setRightEndline(int rightEndline) {
		this.rightEndline = rightEndline;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// private empty constructor
	@SuppressWarnings("unused")
	private Clone() {
		
	}
	
	// constructor
	public Clone(String language, int solutionSet, int leftCloneFileNumber, int leftCloneStartline,
		int leftCloneEndline, int rightCloneFileNumber, int rightCloneStartline,
		int rightCloneEndline, boolean isFull, int cloneType) {
		
		this.language = language;
		this.solutionSet = solutionSet;
		this.leftFile = leftCloneFileNumber;
		this.leftStartline = leftCloneStartline;
		this.leftEndline = leftCloneEndline;
		this.rightFile = rightCloneFileNumber;
		this.rightStartline = rightCloneStartline;
		this.rightEndline = rightCloneEndline;
		this.isFull = isFull;
		this.type = cloneType;
		
	}

	
}
