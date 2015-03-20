import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class right {
	
	
	static public int solve(int N , int K , int B , int T , int[] loc , int[] v){
		int c = 0;
		
		ArrayList<Integer> cand = new ArrayList<Integer>();
		for(int i = N - 1 ; i >= 0 ; i --){
			if(loc[i] + v[i] * T >= B){
				cand.add(i);
				if(cand.size() >= K)
					break;
			}
		}
		if(cand.size() < K)
			return -1;
		int max = 0;
		for(int i = cand.size() - 1 ; i >= 0 ; i --){
			int curr = cand.get(i);
			max = max + N - 1 - i - curr;
		}
		
		return max;
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(new File("A.in")));
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("B.out")));
		String line = reader.readLine();
		long k = Integer.parseInt(line);
		for(long i = 0 ; i < k ; i ++){
			line = reader.readLine();
			String tokens[] = line.trim().split("\\s+");
			int I = Integer.parseInt(tokens[0]);
			int K = Integer.parseInt(tokens[1]);
			int B = Integer.parseInt(tokens[2]);
			int T = Integer.parseInt(tokens[3]);
			
			int[] loc = new int[I];
			int[] v = new int[I];
			
			line = reader.readLine();
			tokens = line.split("\\s+");
			for(int j = 0 ; j < I ; j ++){
				loc[j] = Integer.parseInt(tokens[j]); 
			}
			
			line = reader.readLine();
			tokens = line.split("\\s+");
			for(int j = 0 ; j < I ; j ++){
				v[j] = Integer.parseInt(tokens[j]);
			}
			
			int r = solve(I , K , B , T , loc , v);
			if(r == -1)
				writer.write("Case " + (i+1) + ": " + "IMPOSSIBLE" + "\r\n");
			else
				writer.write("Case " + (i+1) + ": " + r + "\r\n");
		}
		reader.close();
		writer.close();
	}

}
