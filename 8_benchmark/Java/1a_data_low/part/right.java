import java.util.Scanner;

public class B {
	void solve() {
		Scanner sc = new Scanner(System.in);
		int c = sc.nextInt();
		for (int caseNum = 1; caseNum <= c; caseNum++) {
			System.out.print("Case #" + caseNum + ": ");
			int C = sc.nextInt();
			int D = sc.nextInt();

			int[][] v = new int[C][2];
			for (int i = 0; i < C; i++) {
				int P = sc.nextInt();
				int V = sc.nextInt();
				v[i][0] = P;
				v[i][1] = V;
			}

			double maxTime = 0;

			for (int i = 1; i <= C; i++) {
				int nv = 0;
				for (int j = 0; j + i <= C; j++) {
					if (j == 0) {
						for (int k = 0; k < i; k++) {
							nv += v[j + k][1];
						}
					} else {
						nv = nv - v[j - 1][1] + v[j + i - 1][1];
					}
					int distance = v[j + i - 1][0] - v[j][0];
					// 
					double time = ((double) (nv - 1) * (double) D - distance) / 2.0;

					if (time > maxTime) {
						//
						maxTime = time;
					}
				}
			}

			System.out.print(maxTime + "\n");
		}
	}

	public static void main(String[] args) {
		new B().solve();
	}
}
