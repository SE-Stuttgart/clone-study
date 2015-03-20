import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class right {	
	BufferedReader reader;
    StringTokenizer tok;
    PrintWriter out;
    
    class Segment{
    	int l;
    	int r;
    	public Segment(int l, int r){
    		this.l = l;
    		this.r = r;
    	}
    }
    
    long N;
    public void task() throws IOException {
    	N = nextLong();
    	int M = nextInt();
    	
    	ArrayList<Segment> segments = new ArrayList<Segment>();
    	for(int i = 0; i < M; i++){
    		int l = nextInt();
    		int r = nextInt();
    		int p = nextInt();
    		for(int j = 0; j < p; j++){
    			segments.add( new Segment(l, r) );
    		}
    	}
    	long origin = totalcost(segments);
    	
    	Collections.sort(segments, new Comparator<Segment>(){
			public int compare(Segment arg0, Segment arg1) {
				if( arg0.l < arg1.l ) return -1;
				if( arg0.l == arg1.l && arg0.r < arg1.r ) return -1;
				return 1;
			}    		
    	});
    	
    	for(int i = 0; i < segments.size(); i++){
    		for(int j = i+1; j < segments.size(); j++ ){
    			extend( segments.get(i), segments.get(j) );
    		}
    	}
    	
    	
    	out.println( origin - totalcost(segments) );
    }
    
    public void extend(Segment seg1, Segment seg2){
    	if( seg1.l < seg2.l && seg1.r < seg2.r ){
    		if( seg1.r >= seg2.l ){
    			int tmp = seg1.r;
    			seg1.r = seg2.r;
    			seg2.r = tmp;
    		}
    	}
    }
    
    public long totalcost(ArrayList<Segment> segments){
    	long ret = 0;
    	for(Segment seg: segments)
    		ret = ret + cost(seg);
    	return ret;    	
    }
    public long cost(Segment seg){
    	int diff = seg.r - seg.l;
    	return (2*N-diff+1)*diff/2;
    }
        
	public void solve() throws IOException {
		int T = nextInt();
		for(int t = 1; t <= T; t++){
			out.print("Case #" + (t) + ": ");
			task();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new right().run();
	}
	
	public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tok = null;
            out = new PrintWriter(System.out);
            solve();
            reader.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    String nextToken() throws IOException {
        while (tok == null || !tok.hasMoreTokens()) {
            tok = new StringTokenizer(reader.readLine());
        }
        return tok.nextToken();
    }

}
