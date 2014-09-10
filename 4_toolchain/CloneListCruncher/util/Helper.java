package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * small helper class for conversions
 * @author ivan
 *
 */
public class Helper {

	// combines values to one string that can be used as a key for a clonetable map
	public static String combineKeys(String tool, String language, int solutionSet, boolean full) {
		return tool + ";" + language + ";" + Integer.toString(solutionSet) + ";" + (full?"f":"p");
	}

	// helper function that counts the number of line in a file (source: http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java)
	public static int countLines(String filename) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			is.close();
			return (count == 0 && !empty) ? 1 : count;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// helper function that counts the number of lines before the first method in a file
	public static int countStartingLines(String filename) {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
					if (c[i] == '{') {
						is.close();
						return (count == 0 && !empty) ? 1 : count;
					}
				}
			}
			is.close();
			return (count == 0 && !empty) ? 1 : count;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}