import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class right {
	FastScanner in;
	PrintWriter out;

	final long mod = 1000002013;
	
	class In implements Comparable<In> {
		long inTime = 0;
		long count = 0;
		
		public In(long inTime, long count) {
			super();
			this.inTime = inTime;
			this.count = count;
		}

		@Override
		public int compareTo(In arg0) {
			return Long.compare(arg0.inTime, inTime);
		}
		
	}
	
	public void solve() throws IOException {
		long n = in.nextInt();
		int m = in.nextInt();
		long[] st = new long[m];
		long[] en = new long[m];
		long[] p = new long[m];
		long sh = 0;
		TreeSet<Long> events = new TreeSet<Long>();
		for (int i = 0; i < m; i++) {
			st[i] = in.nextInt();
			en[i] = in.nextInt();
			p[i] = in.nextLong();
			long dist = en[i] - st[i];
			sh = (sh + p[i] * ((2 * n - dist + 1) * dist / 2) % mod) % mod;
			
			events.add(st[i]);
			events.add(en[i]);
		}
		long ans = 0;
		TreeSet<In> cur = new TreeSet<In>();
		for (long time : events) {
			long countNow = 0;
			for (int i = 0; i < m; i++) {
				if (st[i] == time) {
					countNow += p[i];
				}
			}
			cur.add(new In(time, countNow));
			for (int i = 0; i < m; i++) {
				if (en[i] == time) {
					long need = p[i];
					while (need > 0) {
						In x = cur.pollFirst();
						long y = Math.min(need, x.count);
						long dist = time - x.inTime;
						ans = (ans + y * (((2 * n - dist + 1) * dist) / 2) % mod) % mod;
						x.count -= y;
						need -= y;
						if (x.count != 0) cur.add(x);
					}
				}
			}
		}
		out.println((sh - ans + mod) % mod);
	}

	public void run() {
		try {
			in = new FastScanner(new File("in.txt"));
			out = new PrintWriter(new File("out.txt"));

			int countTests = in.nextInt();
			for (int i = 0; i < countTests; i++) {
				System.out.println(i);
				out.print("Case #" + (i + 1) + ": ");
				solve();
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		FastScanner(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		boolean hasNext() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					String s = br.readLine();
					if (s == null) {
						return false;
					}
					st = new StringTokenizer(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}

	public static void main(String[] arg) {
		new right().run();
	}
}