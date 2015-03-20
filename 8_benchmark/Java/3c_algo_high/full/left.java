import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class left {
    public static final String FILE = "B.in";

    public static long p2[], d2;
    public static int v[], c;

    public static boolean check(long t2) {
	long pos2 = Integer.MIN_VALUE;
	for (int i = 0; i < c; i++) {
	    for (int j = 0; j < v[i]; j++) {
		pos2 = Math.max(pos2 + d2, p2[i] - t2);
		if (pos2 > p2[i] + t2)
		    return false;
	    }
	}
	return true;
    }

    public static void main(String args[]) {
	try {
	    Scanner in = new Scanner(new File(FILE + ".in"));
	    PrintStream out = new PrintStream(new FileOutputStream(FILE
		    + ".out"));
	    int t = in.nextInt();
	    for (int testN = 1; testN <= t; testN++) {
		c = in.nextInt();
		d2 = 2 * in.nextLong();
		p2 = new long[c];
		v = new int[c];
		for (int i = 0; i < c; i++) {
		    p2[i] = 2 * in.nextLong();
		    v[i] = in.nextInt();
		}
		long min = 0, max = Long.MAX_VALUE;
		while (min < max) {
		    long mid = (min + max) / 2;
		    if (check(mid))
			max = mid;
		    else
			min = mid + 1;
		}
		out.println("Case #" + testN + ": " + ((double) min / 2));
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }
}
