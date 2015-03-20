import java.io.*;
import java.util.*;

public class right {

	PrintWriter out;
	BufferedReader br;
	StringTokenizer st;

	String nextToken() throws IOException {
		while ((st == null) || (!st.hasMoreTokens()))
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	public long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	public void solve() throws IOException {
		int n = nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt();
		}
		int l = 0;
		int r = n;
		int ans = 0;
		while (l < r) {
			int min = l;
			for (int i = l; i < r; i++) {
				if (a[min] > a[i])
					min = i;
			}
			if (min - l < r - min - 1) {
				ans += min - l;
				for (int i = min; i > l; i--) {
					a[i] = a[i - 1];
				}
				l++;
			} else {
				ans += r - min - 1;
				r--;
				for (int i = min; i < r; i++)
					a[i] = a[i + 1];
			}
		}
		out.println(ans);
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);

			br = new BufferedReader(new FileReader("B.in"));
			out = new PrintWriter("B.out");
			int n = nextInt();
			for (int i = 0; i < n; i++) {

				out.print("Case #" + (i + 1) + ": ");
				solve();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new right().run();
	}
}
