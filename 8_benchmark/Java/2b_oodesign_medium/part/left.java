import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class left {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int T = in.nextInt();
		for (int cn = 1 ; cn <= T ; cn++) {
			int N = in.nextInt();
			int[] a = new int[N];
			for (int i = 0 ; i < N ; i++) {
				a[i] = in.nextInt();
			}
			out.println(String.format("Case #%d: %d", cn, solve(a)));
		}
		out.flush();
	}
	
	private static int solve(int[] a) {
		int n = a.length;
		int cn = 0;
		int[] left = new int[n];
		int[] right = new int[n];
		for (int i = 0 ; i < n ; i++) {
			for (int j = 0 ; j < i ; j++) {
				if (a[j] > a[i]) {
					left[i]++;
				}
			}
			for (int j = i+1 ; j < n ; j++) {
				if (a[j] > a[i]) {
					right[i]++;
				}
			}
			cn += Math.min(left[i], right[i]);
		}
		return cn;
	}


	public static void debug(Object... o) {
		System.err.println(Arrays.deepToString(o));
	}
}
