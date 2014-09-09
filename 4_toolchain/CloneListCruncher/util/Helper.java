package util;

/**
 * small helper class for conversions
 * @author ivan
 *
 */
public class Helper {

	// combines values to one string that can be used as a key for a clonetable map
	public static String combineKeys(String tool, String language, int solutionSet, boolean isFull) {
		return tool + ";" + language + ";" + Integer.toString(solutionSet) + ";" + (isFull?"f":"p");
	}
	
}