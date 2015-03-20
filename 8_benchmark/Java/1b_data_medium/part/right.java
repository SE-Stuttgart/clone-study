import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class right {	

	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(new File("B.in"));
		int T = in.nextInt();
		for(int ca = 0; ca < T; ca++) {	
			int L = in.nextInt();
			long t = in.nextLong();
			int N = in.nextInt();
			int C = in.nextInt();
			int[] a = new int[C];
			for(int i = 0; i < C; i++) {
				a[i] = in.nextInt();
			}
			long[] dist = new long[N];
			for(int i = 0; i < N; i++) {
				dist[i] = a[i%C];
			}
			boolean[] boost = new boolean[N];

			long sum = 0;
			
			int idx = 0;
			while(idx < N) {
				if(sum + 2*dist[idx] > t) {
					break;
				}
				sum = sum + 2*dist[idx++];
			}
			long b = t - sum;

			LinkedList<Space> l = new LinkedList<Space>();
			
			if(idx < N) {
				l.addFirst(new Space(2*dist[idx] - (b/2+dist[idx]), idx));
			}
			
			
			for(int i = idx+1; i < N; i++) {
				l.addFirst(new Space(2*dist[i]-dist[i], i));
			}
			Space[] sp = l.toArray(new Space[0]);
			
			Arrays.sort(sp);

			for(int i = 0; (i < L && i < N && i < sp.length); i++) {
				boost[sp[i].pos] = true;
			}
			
			long time = 0;
			for(int i = 0; i < N; i++) {
				if(boost[i]) {
					b = Math.max(t-time, 0);
					if(b >= 2*dist[i]) {
						time = time + 2*dist[i];
					}else {
						time = time + b;
						long rem = dist[i] - b/2;
						time = time + rem;
					}
				}else {
					time = time + 2*dist[i];
				}
			}

			System.out.println("Case #" + (ca+1) + ": " + time);
		}
		in.close();
	}

}

class Space implements Comparable<Space>{
	
	long dist;
	int pos;
	
	public Space(long dist, int pos) {
		this.dist = dist;
		this.pos = pos;
	}

	@Override
	public int compareTo(Space arg0) {
		return -((Long)this.dist).compareTo(arg0.dist);
	}
}