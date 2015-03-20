import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class left {
	public static final String FILENAME = "B-attempt1";
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("src/"+FILENAME+".in"));
		BufferedWriter out = new BufferedWriter(new FileWriter("src/"+FILENAME+".out"));
		Scanner scanner = new Scanner(in);
		int T = scanner.nextInt();
		for(int i = 1; i <= T; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();

			String ret = "";
			int n = 1;
			int tx = 0;
			int ty = 0;

			while(x != tx) {
				if(Math.abs(x-tx) > n) {
					if(tx < x) {
						tx += n;
						ret += "E";
						n++;
					}
					else {
						tx -= n;
						ret += "W";
						n++;
					}
				}
				else if(x > tx) {
					int dist = x-tx+1;
					int d = 0;
					while(dist > 1) {
						dist /= 2;
						d++;
					}
					for (int j = 0; j < d; j++) {
						tx -= n;
						ret += "W";
						n++;
					}
					for (int j = 0; j < d; j++) {
						tx += n;
						ret += "E";
						n++;
					}
				}
				else {
					int dist = tx-x+1;
					int d = 0;
					while(dist > 1) {
						dist /= 2;
						d++;
					}
					for (int j = 0; j < d; j++) {
						tx += n;
						ret += "E";
						n++;
					}
					for (int j = 0; j < d; j++) {
						tx -= n;
						ret += "W";
						n++;
					}
				}
			}
			
			System.out.println(ret);

			while(y != ty) {
				if(Math.abs(y-ty) > n) {
					if(ty < y) {
						ty += n;
						ret += "N";
						n++;
					}
					else {
						ty -= n;
						ret += "S";
						n++;
					}
				}
				else if(y > ty) {
					int dist = y-ty+1;
					int d = 0;
					while(dist > 1) {
						dist /= 2;
						d++;
					}
					for (int j = 0; j < d; j++) {
						ty -= n;
						ret += "S";
						n++;
					}
					for (int j = 0; j < d; j++) {
						ty += n;
						ret += "N";
						n++;
					}
				}
				else {
					int dist = ty-y+1;
					int d = 0;
					while(dist > 1) {
						dist /= 2;
						d++;
					}
					for (int j = 0; j < d; j++) {
						ty += n;
						ret += "N";
						n++;
					}
					for (int j = 0; j < d; j++) {
						ty -= n;
						ret += "S";
						n++;
					}
				}
			}
			
			System.out.println(ret);
			
			out.write("Case #"+i+": "+ret+"\n");
		}
		in.close();
		out.close();
	}
}
