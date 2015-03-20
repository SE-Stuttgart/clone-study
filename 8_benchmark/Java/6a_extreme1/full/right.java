import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class right {
	static final String filename = "C";

	public static void main(String[] args) throws IOException {
		String finput = filename+".in";
		String foutput = filename+".out";
		Scanner s = new Scanner(new FileInputStream(finput));
		Writer w = new OutputStreamWriter(new FileOutputStream(foutput));
		right solver = new right();
		solver.solve(s, w);
		w.close();
	}

	void solve(Scanner s, Writer w) throws IOException {
		int T = s.nextInt();
		for (int t = 0; t < T; t++) {
			int N=s.nextInt();
			int M=s.nextInt();
			Queue<Long>a=new LinkedList<Long>();
			Queue<Long>b=new LinkedList<Long>();
			Queue<Long>A=new LinkedList<Long>();
			Queue<Long>B=new LinkedList<Long>();
			for(int i=0;i<N;i++){
				a.offer(s.nextLong());
				A.offer(s.nextLong());
			}
			for(int i=0;i<M;i++){
				b.offer(s.nextLong());
				B.offer(s.nextLong());
			}
			
			System.out.println(N);
			System.out.println(M);
			
			Map<What, Long>mem=new HashMap<What, Long>();
			String r=solve(N,M,a,A,b,B,mem)+"";
			
			w.write("Case #" + (t + 1) + ": ");
			w.write(r);

			String nlsymbol = System.getProperty("line.separator");
			w.write(nlsymbol);
		}

	}
	
	long solve(int N,int M,Queue<Long>a,Queue<Long>A,Queue<Long>b,Queue<Long>B,Map<What, Long>mem){
		
		if(a.isEmpty()||b.isEmpty())
			return 0;
		
		What w=new What();
		w.a=a;
		w.A=A;
		w.b=b;
		w.B=B;
		Long lng=mem.get(w);
		if(lng!=null)
			return lng;
		
		long ai=a.element();
		long Ai=A.element();
		long bi=b.element();
		long Bi=B.element();
		
		Queue<Long>na=makeCopy(a);
		Queue<Long>nA=makeCopy(A);
		Queue<Long>nb=makeCopy(b);
		Queue<Long>nB=makeCopy(B);
		
		long pack=0;
		if(Ai==Bi&&ai>0&&bi>0){
			pack=Math.min(ai, bi);
			a=makeCopy(a,pack);
			b=makeCopy(b,pack);
			pack+=solve(N,M,a,A,b,B,mem);
		}
		
		long x=solve(N,M,na,nA,b,B,mem);
		long y=solve(N,M,a,A,nb,nB,mem);
		
		long max=Math.max(x, y);
		max=Math.max(max, pack);
		
		mem.put(w, max);
		return max;
	}
	
	Queue<Long> makeCopy(Queue<Long>q,Long p){
		boolean flag=false;
		Queue<Long>r=new LinkedList<Long>();
		for(Long l:q){
			if(!flag)
				r.offer(l-p);
			else
				r.offer(l);
			flag=true;
		}
		return r;
	}
	
	Queue<Long> makeCopy(Queue<Long>q){
		boolean flag=false;
		Queue<Long>r=new LinkedList<Long>();
		for(Long l:q){
			if(flag)
				r.offer(l);
			flag=true;
		}
		return r;
	}
	static class What{
		Queue<Long>a,A,b,B;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((A == null) ? 0 : A.hashCode());
			result = prime * result + ((B == null) ? 0 : B.hashCode());
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			What other = (What) obj;
			if (A == null) {
				if (other.A != null)
					return false;
			} else if (!A.equals(other.A))
				return false;
			if (B == null) {
				if (other.B != null)
					return false;
			} else if (!B.equals(other.B))
				return false;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (b == null) {
				if (other.b != null)
					return false;
			} else if (!b.equals(other.b))
				return false;
			return true;
		}
		
	}
}
