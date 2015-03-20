import java.util.Scanner;

public class right {
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		StringBuilder out = new StringBuilder();

		int t = in.nextInt();
		for (int z = 1; z <= t; z++) {
			int n;
			long k, b, T;
			n = in.nextInt();
			k = in.nextLong();
			b = in.nextLong();
			T = in.nextLong();
			
			long[] x = new long[n];
			long[] v = new long[n];
			
			for (int i = 0; i < n; i++) {
				x[i] = in.nextLong();
			}
			for (int i = 0; i < n; i++) {
				v[i] = in.nextLong();
			}
			
			long total = 0;
			long br = 0;
			long winners = 0;
			for (int i = (int)(n-1); i >=0 && winners < k; i--) {
				if (isPossible(x[i], v[i], b, T)) {
					total += br;
					winners++;
				} else {
					br++;
				}
			}
			
			out.append("Case #" + z + ": ");
			if (winners >= k) {
				out.append(total);
			} else {
				out.append("IMPOSSIBLE");
			}
			out.append("\n");
		}

		System.out.print(out.toString());
	}

	private static boolean isPossible(long x, long v, long b, long t) {
		long dist = b - x;
		return (dist) <= (v*t);
	}

}
