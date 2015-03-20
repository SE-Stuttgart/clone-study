import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class left implements Runnable {

	public static void main(String[] args) {
		(new Thread(new left())).start();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok;

	String nextToken() throws IOException {
		while (tok == null || !tok.hasMoreTokens()) tok = new StringTokenizer(in.readLine());
		return tok.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}
	
	BigInteger nextBigInt() throws IOException {
		return new BigInteger(nextToken());
	}
	
	void solve() throws IOException {
		int r = nextInt();
		int[][] a = new int[101][101];
		int[][] b = new int[101][101];
		for (int i = 0; i < r; i++) {
			int x1 = nextInt();
			int y1 = nextInt();
			int x2 = nextInt();
			int y2 = nextInt();
			for (int q = x1; q <= x2; q++) {
				for (int w = y1; w <= y2; w++) {
					a[q][w] = 1;
				}
			}
		}
		boolean f = true;
		int ans = 0;
		while (f) {
			f = false;
			for (int i = 1; i <= 100; i++) {
				for (int j = 1; j <= 100; j++) {
					b[i][j] = a[i][j];
					if (a[i - 1][j] == 0 && a[i][j - 1] == 0) b[i][j] = 0;
					if (a[i - 1][j] == 1 && a[i][j - 1] == 1) b[i][j] = 1;
				}
			}
			for (int i = 1; i <= 100; i++) {
				for (int j = 1; j <= 100; j++) {
					a[i][j] = b[i][j];
					if (a[i][j] == 1) f = true;
				}
			}
			ans++;
		}
		out.println(ans);		
	}
	
	public void run() {
		Locale.setDefault(Locale.UK);
		try {
			in = new BufferedReader(new FileReader(new File("in.txt")));
			out = new PrintWriter(new FileWriter(new File("out.txt")));
			int t = nextInt();
			for (int nn = 1; nn <= t; nn++) {
				out.print("Case #" + nn + ": ");
				solve();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.flush();
	}

}
