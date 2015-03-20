import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class left {

	static Scanner sc = new Scanner(new BufferedInputStream(System.in));
	static PrintWriter pw = new PrintWriter(
			new BufferedOutputStream(System.out));

	static long[][] b, t;
	static int n, m;

	public static void main(String[] args) {
		int test = sc.nextInt();
		for (int ti = 1; ti <= test; ti++) {
			n = sc.nextInt();
			m = sc.nextInt();
			b = new long[n][2];
			t = new long[m][2];

			for (int i = 0; i < n; i++) {
				b[i][0] = sc.nextLong();
				b[i][1] = sc.nextLong();
			}
			for (int j = 0; j < m; j++) {
				t[j][0] = sc.nextLong();
				t[j][1] = sc.nextLong();
			}

			pw.println("Case #" + ti + ": " + _try(0, 0));
		}
		pw.flush();
	}

	static long _try(int i, int j) {
		if (i == n || j == m)
			return 0;

		if (b[i][1] == t[j][1]) {
			
			if (b[i][0] == t[j][0])
				return _try(i + 1, j + 1) + b[i][0];
			else if (b[i][0] < t[j][0]) {
				t[j][0] -= b[i][0];
				long res = _try(i + 1, j);
				t[j][0] += b[i][0];
				return res + b[i][0];
			} else {				
				b[i][0] -= t[j][0];
				long res = _try(i, j + 1);
				b[i][0] += t[j][0];
				return res + t[j][0];
			}
			
		} else {
			long tmp1 = 0, tmp2 = 0;
			tmp1 = _try(i + 1, j);
			tmp2 = _try(i, j + 1);

			return Math.max(tmp1, tmp2);
		}
	}
}
