import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class right {

	public void run() {
		try {
			Scanner in = new Scanner(new File("B.in"));
			PrintWriter out = new PrintWriter(new File("B.out"));
			int tests = in.nextInt();

			for (int i = 1; i <= tests; i++) {
				int n = in.nextInt();
				int k = in.nextInt();
				int b = in.nextInt();
				int t = in.nextInt();

				int[] x = new int[n];
				int[] v = new int[n];

				for (int j = 0; j < x.length; j++) {
					x[j] = in.nextInt();
				}

				for (int j = 0; j < v.length; j++) {
					v[j] = in.nextInt();
				}

				int counter = 0;
				int swaps = 0;

				for (int j = x.length-1; j >= 0; j--) {
					if (counter == k)
						break;
					if (getTime(x[j], v[j], b) <= t) {
						counter++;

						for (int kk = j + 1; kk < x.length; kk++) {
							if (swap(x[j], x[kk], v[j], v[kk], b, t)) {
								swaps++;
							}
						}
					}
				}

				
				out.print("Case #" + i + ": ");
				if (counter < k) {
					out.println("IMPOSSIBLE");
				} else if (counter == k) {
					out.println(swaps);
				}
			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean swap(int xi, int xj, int vi, int vj, int b, int t) {
		if (vi <= vj)
			return false;

		int tj = getTime(xj, vj, b);
		if (tj <= t)
			return false;

		int ti = getTime(xi, vi, b);
		if (ti < tj)
			return true;

		return false;
	}

	public int getTime(int x, int v, int b) {
		double val = (double) (b - x) / v;
		return (int) Math.ceil(val);
	}

	public static void main(String[] args) {
		new right().run();
	}
}
