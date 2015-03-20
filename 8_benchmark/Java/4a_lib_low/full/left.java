import java.io.*;
import java.util.*;

public class left {
	public static void main(String[] args) {
		new left().run(new Scanner(System.in));
	}

	void run(Scanner sc) {
		int n = sc.nextInt();
		for (int i = 1; n-- > 0; i++)
			solve(sc, i);
	}
	static class Pair implements Comparable<Pair> {
		long p, v;
		Pair(int p, int v) {
			this.p = p; this.v = v;
		}
		@Override
		public int compareTo(Pair o) {
			return (int)p - (int)o.p;
		}
	}
	boolean check(List<Pair> ps, long dis, int D) {
		long left = ps.get(0).p*2 - dis;
		for(Pair p : ps) {
			long nLeft = Math.max(p.p*2 - dis, left);
			long right = nLeft + D*2 * p.v;
			if(right-D*2 > p.p*2 + dis) return false;
			left = right;
		}
		return true;
	}
	void solve(Scanner sc, int case_num) {
		int C = sc.nextInt();
		int D = sc.nextInt();
		List<Pair> ps = new ArrayList<left.Pair>();
		for (int i = 0; i < C; i++) {
			ps.add(new Pair(sc.nextInt(), sc.nextInt()));
		}
		Collections.sort(ps);
		long max = (long)(Integer.MAX_VALUE * 100000.0);
		long min = 0;
		if(check(ps, 0, D)) {
			max = 0;
		} else {
			while(true) {
				long mid = (max + min) / 2;
				if(mid == max || mid == min) {
					break;
				}
				boolean ch = check(ps, mid, D);
				if(ch) max = mid;
				else min = mid;
			}
		}

		System.out.printf("Case #%d: %d.%d\n", case_num, max / 2, max % 2 == 1 ? 5: 0);
	}
}
