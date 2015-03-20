import static java.lang.Math.*;
import static java.util.Arrays.*;
import java.util.*;
import java.io.*;
import java.math.BigInteger;

import javax.naming.BinaryRefAddr;

public class right {

	public static void tr(Object... o) {
		System.err.println(Arrays.deepToString(o));
	}

	public static void main(String[] args) throws Throwable {
		String pkg = new Object(){}.getClass().getEnclosingClass().getPackage().getName().replace('.', '/');
		String dir = "src/" + pkg;

		//
		String filename = "";
		int THREAD_NUMBER = 1;

		if (true) { filename = "A-small-attempt0.in"; THREAD_NUMBER = 7; }

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(dir + "/" + filename));
		} catch (FileNotFoundException e) {
			tr(e.getMessage());
			return;
		}
		PrintWriter fout = new PrintWriter(new File(dir + "/" + filename + ".res"));

		tn = scanner.nextInt();
		tno = 0;

		ios = new IO[tn];
		for (int i = 0; i < tn; i++) {
			ios[i] = new IO(i + 1);
			ios[i].read(scanner);
		}

		Thread[] threads = new Thread[THREAD_NUMBER];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Solve());
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}

		tr("finished");
		for (int i = 0; i < tn; i++) {
			// System.out.println(ios[i]);
			fout.println(ios[i]);
			fout.flush();
		}
		fout.close();
	}

	static int tn;
	static Integer tno;
	static IO[] ios;

	static class IO {
		int caseNumber;
		private StringBuilder sb = new StringBuilder();
		IO(int caseNumber) {
			this.caseNumber = caseNumber;
		}
		public <T> void print(T o) {sb.append(o);}
		public <T> void println(T o) {sb.append(o + "\n");}
		public <T> void write(T o) {sb.append(o);}
		public <T> void writeln(T o) {sb.append(o + "\n");}
		public <T> void printArray(int[] as) {sb.append(as[0]);for (int i = 1; i < as.length; i++) { sb.append(" "); sb.append(as[i]); }}
		public <T> void printArray(long[] as) {sb.append(as[0]);for (int i = 1; i < as.length; i++) { sb.append(" "); sb.append(as[i]); }}

		public String toString() {
			String res = "Case #" + caseNumber + ": "; // TODO: use NEW_LINE instead of SPACE.
			res += sb.toString();
			res = res.replaceAll("\\s+$", "");
			return res;
		}

		// 
		long N;
		int M;
		long[] o;
		long[] e;
		long[] P;

		void read(Scanner sc) {
			N = sc.nextLong();
			M = sc.nextInt();
			o = new long[M];
			e = new long[M];
			P = new long[M];
			for (int i = 0; i < M; i++) {
				o[i] = sc.nextLong();
				e[i] = sc.nextLong();
				P[i] = sc.nextLong();
			}
		}

		int[] nextIntArray(Scanner sc, int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) a[i] = sc.nextInt();
			return a;
		}
		long[] nextLongArray(Scanner sc, int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++) a[i] = sc.nextLong();
			return a;
		}
		// finished
}


	final static long MOD = 1000002013L;

	static class Solve implements Runnable {

		IO io;

		@Override
		public void run() {
			for (;;) {
				synchronized (tno) {
					if (tno < tn)
						io = ios[tno++];
					else
						break;
				}
				solve();
				tr(io);
			}
		}

        // main part
		
		long N;
		int M;
		long[] o;
		long[] e;
		long[] P;

		long[] org_x;

		void solve() {
			N = io.N;
			M = io.M;
			o = io.o;
			e = io.e;
			P = io.P;

			int[] temp = new int[2 * M];
			{
				int s = 0;
				for (int i = 0; i < M; i++) {
					temp[s++] = (int)o[i];
					temp[s++] = (int)e[i];
				}
			}
			int[] sup = normalize(temp);

			org_x = new long[2 * M];
			for (int i = 0; i < sup.length; i++) {
				org_x[sup[i]] = temp[i];
			}

			long table[][] = new long[2 * M][2 * M];

			for (int i = 0; i < M; i++) {
				table[ sup[2 * i] ][sup[2 * i + 1]] += P[i];
			}

			BigInteger org_gain = calc_gain(table);

			for (int j = 2 * M - 1; j >= 0; j--) {
				for (int i = j - 1; i >= 0; i--) { // i < j
					if (table[i][j] > 0) {

						for (int jj = j - 1; jj >= 0; jj--) {
							for (int ii = i - 1; ii >= 0; ii--) {
								if (table[ii][jj] > 0) {
									long move = min(table[ii][jj], table[i][j]);

									if (ii <= j && i <= jj) {
										table[ii][jj] -= move;
										table[i][j] -= move;
										table[ii][j] += move;
										table[i][jj] += move;
									}
								}

							}
						}
					}
				}
			}
			BigInteger opt_gain = calc_gain(table);

			BigInteger ans = opt_gain.subtract(org_gain);
			ans = ans.mod(BigInteger.valueOf(MOD));
			io.write(ans.toString());
		}


		BigInteger calc_gain(long[][] table) {
			BigInteger res = BigInteger.ZERO;
			for (int i = 0; i < 2 * M; i++) {
				for (int j = 0; j < 2 * M; j++) {
					if (table[i][j] > 0) {
						long d = org_x[j] - org_x[i];
						res = res.add(BigInteger.valueOf(table[i][j] * d * (d - 1) / 2));
					}
				}
			}


			return res;
		}

		int[] normalize(int[] v) {
			int[] res = new int[v.length];
			int[][] t = new int[v.length][2];
			for (int i = 0; i < v.length; i++) {
				t[i][0] = v[i];
				t[i][1] = i;
			}
			Arrays.sort(t, 0, t.length, new Comparator<int[]>(){
				public int compare(int[] a, int[] b){
					if (a[0] != b[0]) return a[0] < b[0] ? -1 : 1;
					return 0;
				}
			});

			int r = 0;
			for (int i = 0; i < v.length; i++) {
				r += (i > 0 && t[i - 1][0] != t[i][0]) ? 1 : 0;
				res[(int)t[i][1]] = r;
			}
			return res;
		}


	}
}
