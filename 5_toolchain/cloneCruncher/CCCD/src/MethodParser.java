import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MethodParser {

	static int THRESHOLD = 6;

	private static final boolean DEBUG = false;

	private Map<Integer, LineAndCharInfo> lineCharMapping = new TreeMap<Integer, LineAndCharInfo>();

	private int lastLineNumber = 0;

	private int endLineOfFunc = -1;
	private int startLineOfFunc = -1;

	public int getEndLineOfFunc() {
		return endLineOfFunc;
	}

	public int getStartLineOfFunc() {
		return startLineOfFunc;
	}

	public void calcFunctionLines(String methodName, String pathToFile) {
		isInsideBounds(methodName, pathToFile, 0, 0, true);
	}

	public boolean isInsideBounds(String methodName, String pathToFile,
			int startIndex, int endIndex) {
		return isInsideBounds(methodName, pathToFile, startIndex, endIndex,
				false);
	}

	// Input: String CCCDMethodennamen, String Path, int start, int end
	// Output: boolean isOverlapping
	/**
	 * 
	 * @param methodName
	 *            Name of method to be found
	 * @param pathToFile
	 *            Path to source file containing the method
	 * @param startIndex
	 *            Beginning of overlapping area
	 * @param endIndex
	 *            End of overlapping area
	 * @return
	 */
	private boolean isInsideBounds(String methodName, String pathToFile,
			int startIndex, int endIndex, boolean noCheck) {

		lineCharMapping.clear();
		mapLines(pathToFile); // Parse and map the whole file

		boolean isInside = false;
		boolean found = false;
		int MAXINPUTLINES = 3;
		int lines = 1;

		List<LineAndCharInfo> candidats = new ArrayList<LineAndCharInfo>();

		java.util.Iterator<Integer> iterator = lineCharMapping.keySet()
				.iterator();
		while (iterator.hasNext()) {
			int key = (Integer) iterator.next();
			LineAndCharInfo value = lineCharMapping.get(key);
			if (value.contents.contains(methodName)) {
				candidats.add(value);
				if (checkFunction(value.contents, methodName)) {
					if (DEBUG) {
						System.out.println("At Line " + value.lineNumber
								+ " starting at " + value.startIndex
								+ " ending at " + value.endIndex
								+ " is the String " + key);
					}
					startLineOfFunc = value.lineNumber;
					endLineOfFunc = findEndOfFunction(value.lineNumber);
					if (DEBUG) {
						String endline = " no end found";
						if (endLineOfFunc != -1) {
							endline = getLineToLineNumber(endLineOfFunc);
						}
						System.out.println("Function ends at " + endLineOfFunc
								+ " with " + endline);
					}
					if (endLineOfFunc != -1) {
						found = true;
					}
				}
			}
		}

		if (!found) {
			 
			int tempStartLineOfFunc;
			while (lines <= MAXINPUTLINES && !found) {

				for (LineAndCharInfo current : candidats) {
					int temp = 0;
					String functionSpace = current.contents;
					while (temp < lines) {
						temp++;
						int i = current.lineNumber + temp;
						
						functionSpace += getLineToLineNumber(i);
					}
					if (checkFunction(functionSpace, methodName)) {
						startLineOfFunc = current.lineNumber;
						tempStartLineOfFunc = current.lineNumber + lines;
						endLineOfFunc = findEndOfFunction(tempStartLineOfFunc);
						if (DEBUG) {
							String endline = " no end found";
							if (endLineOfFunc != -1) {
								endline = getLineToLineNumber(endLineOfFunc);
							}
							System.out.println("Funktion " + methodName
									+ " Starting Line " + current.lineNumber
									+ " ending at Line" + endLineOfFunc
									+ " with " + endline);
						}
						if (endLineOfFunc != -1) {
							found = true;
						}
					}
				}
				lines++;
			}

		}
		if (!noCheck) {
			if ((startIndex <= startLineOfFunc) && (endIndex >= endLineOfFunc)
					&& (endLineOfFunc - startLineOfFunc >= THRESHOLD)) {
				isInside = true;
			}
		}

		return isInside;

	}

	private void mapLines(String filePath) {

		File sourceFile = new File(filePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					sourceFile));
			char charOfLine;
			int lineNumber = 1;
			int charRead = 0;
			String wholeLine = "";
			int start = 0;
			int tempRead;
			// System.out.println("Start reading file: " + filePath);
			while ((tempRead = reader.read()) != -1) {
				charOfLine = (char) tempRead;

				if (charOfLine != '\n') {
					charRead++;
					wholeLine += charOfLine;

				} else {
					// System.out.println("finished line " + lineNumber
					// + " Contains: " + wholeLine);
					LineAndCharInfo newLine = new LineAndCharInfo(lineNumber,
							start, charRead, wholeLine.toLowerCase());
					lineCharMapping.put(lineNumber, newLine);
					start = charRead;

					lineNumber++;
					wholeLine = "";
				}

			}
			// do the last line if EOF
			LineAndCharInfo newLine = new LineAndCharInfo(lineNumber, start,
					charRead, wholeLine.toLowerCase());
			lineCharMapping.put(lineNumber, newLine);
			start = charRead;

			reader.close();
			lastLineNumber = lineNumber;
			System.out.println("Done fileparsing of " + lastLineNumber
					+ " lines.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found@ " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can not read file@ " + filePath);
			e.printStackTrace();
		}

	}

	public void testOutput(String filePath, String functionName) {
		mapLines(filePath);
		int endLineOfFunc = 0;

		java.util.Iterator<Integer> iterator = lineCharMapping.keySet()
				.iterator();
		while (iterator.hasNext()) {
			int key = (Integer) iterator.next();
			LineAndCharInfo value = lineCharMapping.get(key);
			if (value.contents.contains(functionName)) {
				if (checkFunction(value.contents, functionName)) {
					System.out.println("At Line " + value.lineNumber
							+ " starting at " + value.startIndex
							+ " ending at " + value.endIndex
							+ " is the String " + key);

					endLineOfFunc = findEndOfFunction(value.lineNumber);
					if (endLineOfFunc != -1) {
						String endline = getLineToLineNumber(endLineOfFunc);
						System.out.println("Function ends at " + endLineOfFunc
								+ " with " + endline);
					} else {
						System.out.println("No end found");
					}
				}
			}
		}

	}

	private boolean checkFunction(String lineOfCode, String functionName) {
		boolean isDeclaration = false;
		boolean gotOpenRound = false;
		boolean gotClosedRound = false;

		int index = lineOfCode.indexOf(functionName) + functionName.length();
		char nextChar;
		while ((!gotOpenRound || !gotClosedRound || !isDeclaration)
				&& index < lineOfCode.length()) {

			nextChar = lineOfCode.charAt(index);
			if (!gotOpenRound && nextChar == '(') {
				gotOpenRound = true;
			}
			if (gotOpenRound && !gotClosedRound && nextChar == ')') {
				gotClosedRound = true;
			}
			if (gotOpenRound && gotClosedRound && nextChar == '{'
					&& nextChar != ';') {
				isDeclaration = true;
			}
			index++;
		}

		return isDeclaration;
	}

	private int findEndOfFunction(int startOfFunction) {
		int endLine = -1;
		boolean escaped = false;
		boolean nextIsEscaped = false;
		boolean isCommentSection = false;
		boolean gotStar = false;
		boolean isLineComment = false;
		boolean found = false;
		int closingLevel = 0;
		int currentLine = startOfFunction;
		char nextChar;
		int index = 0;
		String line = "Is the Function Head ";
		while (currentLine <= lastLineNumber && !found) {
			line = getLineToLineNumber(currentLine).trim();
			if(DEBUG){
			 System.out.println("Testing " + currentLine + " " + line
			 + "for end conditions at closinglevel of " + closingLevel);
			}
			index = 0; // set index to beginning of a new line
			isLineComment = false;
			while (index < line.length() && !found) {
				nextChar = line.charAt(index);
				if (!isCommentSection) {
					if (!nextIsEscaped) {
						if (nextChar == '/' && isLineComment) {
							// System.out.println("Line " + currentLine
							// + " is linecomment");
							break; // This line is just a comment line
						}
						if (nextChar == '/') { // Could be the start of
												// lineComment or Commentsection
							isLineComment = true;
						}
						if (nextChar == '*' && isLineComment) {
							isCommentSection = true;
						}
						if (nextChar != '/' && isLineComment) {
							isLineComment = false;
						}
						if (!escaped) {
							if (nextChar == '}' && closingLevel == 0) {
								endLine = currentLine;
								found = true;
							}
							if (nextChar == '{') {
								closingLevel++;
								// System.out.println("closing ++");
							}
							if (nextChar == '}' && closingLevel > 0) {
								closingLevel--;

								if (closingLevel == 0) {
									endLine = currentLine;
									// System.out.println("Found at last @"+closingLevel);
									found = true;
								}
							}
							if (nextChar == '"' || nextChar == '\'') {
								escaped = true;
							}
							if (nextChar == '\\') {
								nextIsEscaped = true;
							}
						} else {
							if (nextChar == '"' || nextChar == '\'') {
								escaped = false;
							}
						}
					} else {
						nextIsEscaped = false;
					}
				} else { // inside a comment section need to wait for */
					// System.out.println("Inside a commentsection.");
					if (nextChar == '/' && gotStar) {
						isCommentSection = false;
					}
					if (nextChar == '*') {
						gotStar = true;
					}
					// case that I read a star some time, but there is no
					// following / (eq.*something/)
					if (nextChar != '*' && gotStar) {
						gotStar = false;
					}
				}
				index++;
			}
			currentLine++;
		}
		return endLine;
	}

	private String getLineToLineNumber(int lineNumber) {

		return lineCharMapping.get(lineNumber).contents;
	}

	public void printMap() {
		java.util.Iterator<Integer> iterator = lineCharMapping.keySet()
				.iterator();
		while (iterator.hasNext()) {
			int key = (Integer) iterator.next();
			LineAndCharInfo value = lineCharMapping.get(key);
			System.out.println("Linenumber " + value.lineNumber + " String: "
					+ value.contents);
		}
	}

	class LineAndCharInfo {
		public int lineNumber;
		public int startIndex;
		public int endIndex;
		public String contents;

		public LineAndCharInfo(int lineNumber, int startIndex, int endIndex,
				String contents) {
			this.lineNumber = lineNumber;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.contents = contents;
		}
	}
}
