import java.io.*;
import java.util.*;

public class right {
	

	public static void main (String [] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("B.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("B.out")));
		StringTokenizer st;
		int C = Integer.parseInt(in.readLine());
		for(int n = 1; n<=C; n++) {
			st = new StringTokenizer(in.readLine());
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int T = Integer.parseInt(st.nextToken());
			
			String[] X = in.readLine().split(" ");
			String[] V = in.readLine().split(" ");
			int[] loc = new int[N];
			int[] vel = new int[N];
			for(int i = 0; i<N; i++) {
				loc[i] = Integer.parseInt(X[i]);
				vel[i] = Integer.parseInt(V[i]);
			}
			
			boolean[] cmi = new boolean[N];
			for(int i = 0; i<N; i++) {
				cmi[i] = ((B-loc[i])/vel[i] < T) || ((B-loc[i])/vel[i] == T && (B-loc[i])%vel[i] == 0);
			}
			
			int num = 0;
			int count = 0;
			int numAhead = 0;
			for(int i = N-1; i>=0 && num < K; i--) {
				if(cmi[i]) {
					num++;
					count += numAhead;
				}
				else
					numAhead++;
			}
			
			if(num < K) {
				System.out.println("Case #" + n + ": IMPOSSIBLE");
				out.println("Case #" + n + ": IMPOSSIBLE");
			}
			else {
				System.out.println("Case #" + n + ": " + count);
				out.println("Case #" + n + ": " + count);
			}
		}
		out.close();
		System.exit(0);
	}

}