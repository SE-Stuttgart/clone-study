import java.util.*;
import static java.lang.Math.*;
import java.io.*;

public class left {
	public static void p(Object... args) { System.out.println(Arrays.toString(args));}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int zz = 1; zz <= T; zz++) {
			int N = in.nextInt();
			long P = in.nextLong();
			System.out.format("Case #%d: %d %d \n", zz, mustWin(N, P), cW(N, P));
		}
	}
	static long cW(int N, long P) {
		long low = 0;
		long high = (1L<<N)-1;
		while (low < high) {
			long mid = (low + high + 1) / 2;
			if (couldWin(N, P, mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
	static boolean cW(int N, long P, long K) {
		long B = (1L << N) - 1 - K;
		long numBeaten = 0;
		long numLeftEqual = 1L << N;
		while (B > 0) {
			numLeftEqual /= 2;
			numBeaten += numLeftEqual;
			B = (B - 1) / 2;
		}
		long numLostTo = (1L << N) - numBeaten - 1;
		return numLostTo < P;
	}
	static long mustWin(int N, long P) {
		long low = 0;
		long high = (1L<<N)-1;
		// low must win, don't know about high
		while (low < high) {
			long mid = (low + high + 1) / 2;
			if (mustWin(N, P, mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
	static boolean mustWin(int N, long P, long K) {
		long A = K;
		long numLostTo = 0;
		long numLeftEqual = 1L << N;
		while (A > 0) {
			numLeftEqual /= 2;
			numLostTo += numLeftEqual;
			A = (A - 1) / 2;
		}
		return numLostTo < P;
	}
}
