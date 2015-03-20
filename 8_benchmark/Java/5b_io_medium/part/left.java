import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class left {

	private static File in = new File("B-large.in");
	private static File out = new File("B-large.out");
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(in);
		BufferedWriter out = new BufferedWriter(new FileWriter(out));
		int tc = sc.nextInt();
		for(int tt=1; tt<=tc; tt++) {
			out.write("Case #"+tt+": ");
			
			int n=sc.nextInt();
			int[] dat = new int[n];
			for(int i=0; i<n; i++) {
				dat[i] = sc.nextInt();
			}
			
			int p=0; int q=n-1;
			int ans = 0;
			while(p<q) {
				int k = p;
				for(int i=p; i<=q; i++) {
					if(dat[i] < dat[k]) {
						k=i;
					}
				}
				
				if(k-p < q-k) {
					ans += k-p;
					for(int i=k; i>p; i--) {
						dat[i] = dat[i-1];
					}
					p++;
				} else {
					ans += q-k;
					for(int i=k; i<q; i++) {
						dat[i] = dat[i+1];
					}
					q--;
				}
			}
			out.write(Integer.toString(ans));
			
			out.newLine();
		}
		sc.close();
		out.flush();
		out.close();
	}

}
