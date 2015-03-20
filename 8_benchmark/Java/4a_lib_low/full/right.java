import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class right {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(new FileReader("input.txt"));
		PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

		int numberOfTests = in.nextInt();
		for (int i = 1; i <= numberOfTests; i++) {
			new right().solve(in, out, i);
		}

		in.close();
		out.close();
	}

	static class Group {
		double x1, x2;
		int k;
		double t;
	}

	private void solve(Scanner in, PrintWriter out, int testNumber) {
		int c = in.nextInt();
		double d = in.nextInt();

		int[][] a = new int[c][2];
		for (int i = 0; i < c; i++) {
			a[i][0] = in.nextInt();
			a[i][1] = in.nextInt();
		}

		Arrays.sort(a, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});

		double mT = 0;
		ArrayList<Group> stack = new ArrayList<Group>();
		for (int i = 0; i < c; i++) {
			Group g = new Group();
			g.k = a[i][1];
			double x = a[i][0];
			double w2 = g.k * d / 2;
			g.x1 = x - w2;
			g.x2 = x + w2;
			g.t = (g.k - 1) * d / 2;

			while (!stack.isEmpty()) {
				Group f = stack.get(stack.size() - 1);
				if (f.x2 < g.x1) {
					break;
				}
				stack.remove(stack.size() - 1);
				Group h = new Group();

				h.k = f.k + g.k;

				if (f.t > g.t) {
					double dt = f.t - g.t;
					g.x1 -= dt;
					g.x2 += dt;

					h.t = f.t;
				} else {
					double dt = g.t - f.t;
					f.x1 -= dt;
					f.x2 += dt;

					h.t = g.t;
				}
				h.x1 = Math.min(f.x1, g.x1);
				h.x2 = Math.max(f.x2, g.x2);

				double w = h.k * d;

				double z = Math.max(0, w - h.x2 + h.x1);
				double z2 = z / 2;
				h.x1 -= z2;
				h.x2 += z2;
				h.t += z2;

				g = h;
			}

			stack.add(g);
			mT = Math.max(mT, g.t);
		}

		out.println("Case #" + testNumber + ": " + mT);
	}

}
