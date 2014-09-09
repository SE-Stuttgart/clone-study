package util;

public class Log {

	// level of logging (can be INFO or DEBUG)
	public static final String LOGLEVEL = "INFO";
	//public static final String LOGLEVEL = "DEBUG";
	
	// prints a message to the standard output
	public static void info(String msg) {
		System.out.println(msg);
	}

	// prints a message to the standard output
	public static void debug(String msg) {
		if (LOGLEVEL.equals("DEBUG")) {
			System.out.println(msg);
		}
	}

	// prints a single star to the standard output (to show progress)
	public static void star() {
		System.out.print("*");
	}

}
