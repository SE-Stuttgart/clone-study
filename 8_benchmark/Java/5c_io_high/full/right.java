import java.util.Arrays;
import java.util.Scanner;


public class right {

	static int[] array, bw;
	static int L, t, N, C, total, max, max2;
	static int stage, ex, best;
	static boolean ok;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		for (int index = 1; index <= T; index++) {
			L = in.nextInt();
			t = in.nextInt();
			N = in.nextInt();
			C = in.nextInt();
			array = new int[C];
			for (int i = 0; i < C; i++)
				array[i] = 2 * in.nextInt();
			
			bw = new int[C];
			total = L;
			max = N / C;
			max2 = N - (C * max);
			stage = 0;
			ex = t;
			while (ex > 0) {
				if (stage == N) {
					System.out.println("Case #" + index + ": " + (ex));
				}
				if (array[stage % C] <= ex) {
					stage++;
					ex -= array[stage % C];
				} else {
					break;
				}	
			}
			best = 100000000;
			recur(0, L);
			
			System.out.println("Case #" + index + ": " + best);
		}
	}
	
	static void recur(int n, int left) {
		if (left == 0) {
			for (int i = n; i < C; i++)
				bw[i] = 0;
			analyse();
			return;
		}
		int maxforn = max;
		if (n < max2)
			maxforn++;
		if (n == C - 1) {
			bw[n] = Math.min(left, maxforn);
			analyse();
			return;
		}
		int reallymax = Math.min(left, maxforn);
		for (int i = 0; i <= reallymax; i++) {
			bw[n] = i;
			recur(n + 1, left - i);
		}
	}

	private static void analyse() {
		int time = 0;
		int halfhour = 0;
		if (stage % C < max2) {
			if (max + 1 - bw[stage % C] <= (stage / C)) {//get midway
				time += (array[stage % C] - ex) / 2;
				halfhour += (array[stage % C] - ex) % 2;
			} else {
				time += (array[stage % C] - ex);
			}
		} else {
			if (max - bw[stage % C] <= (stage / C)) {//get midway
				time += (array[stage % C] - ex) / 2;
				halfhour += (array[stage % C] - ex) % 2;
			} else {
				time += (array[stage % C] - ex);
			}
		}
		System.out.println("time for stage " + stage + " is " + time);
		for (int i = stage + 1; i < N; i++) {
			if (i % C < max2) {
				if (max + 1 - bw[i % C] <= (i / C)) {//get midway
					time += array[i % C] / 2;
					halfhour += array[i % C] % 2;
				} else {
					time += array[i % C];
				}
			} else {
				if (max - bw[i % C] <= (i / C)) {//get midway
					time += array[i % C] / 2;
					halfhour += array[i % C] % 2;
				} else {
					time += array[i % C];
				}
			}
		}
		if (halfhour > 2)
			time += (halfhour / 2);
		System.out.println(Arrays.toString(bw) + " " + time);
		if (time < best)
			best = time;
	}
}