import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;


public class left {
  static long mod = 1000002013L;
  
  static long n;
  static long[] location;
  
  public static void main(String[] args) throws IOException {
    // Scanner in = new Scanner(System.in);
    Scanner in = new Scanner(new File("a.in"));
    PrintWriter out = new PrintWriter(new File("a.out"));
    int cases = in.nextInt();
    int caseOn = 1;
    while (cases-- != 0) {
      n = in.nextInt();
      int m = in.nextInt();
      TreeSet<Long> cc = new TreeSet<Long>();
      long[] start = new long[m];
      long[] end = new long[m];
      long[] count = new long[m];
      long original = 0;
      for (int i = 0; i < m; i++) {
        start[i] = in.nextLong();
        end[i] = in.nextLong();
        cc.add(start[i]);
        cc.add(end[i]);
        count[i] = in.nextLong();
        long tcost = cost(end[i] - start[i]);
        tcost *= count[i];
        tcost %= mod;
        original -= tcost;
        original %= mod;
      }
      
      location = new long[cc.size() * 2];
      HashMap<Long, Integer> ccMap = new HashMap<Long, Integer>();
      int index = 0;
      for (long a : cc) {
        ccMap.put(a, index);
        location[index * 2] = location[index * 2 + 1] = a;
        index++;
      }
      
      int[] ccStart = new int[m];
      int[] ccEnd = new int[m];
      
      IntervalTree it = new IntervalTree(0, cc.size() * 2);
      
      for (int i = 0; i < m; i++) {
        ccStart[i] = ccMap.get(start[i]);
        ccEnd[i] = ccMap.get(end[i]);
        it.increment(ccStart[i] * 2, ccEnd[i] * 2, count[i]);
      }
      
      Arrays.sort(ccStart);
      Arrays.sort(ccEnd);
      
      for (int i = 0; i < ccStart.length; i++) {
        for (int j = ccEnd.length - 1; j >= 0; j--) {
          if (ccEnd[j] < ccStart[i]) break;
          
          long min = it.f(ccStart[i] * 2, ccEnd[j] * 2, it.MIN);
          
          if (min != 0) {
            it.increment(ccStart[i] * 2, ccEnd[j] * 2, -min);
            long tcost = cost(ccStart[i] * 2, ccEnd[j] * 2);
            
            min %= mod;
            tcost *= min;
            tcost %= mod;
            
            original += tcost;
            original %= mod;
          }
        }
      }
      
      if (original < 0) original += mod;
      
      System.out.printf("Case #%d: %d\n", caseOn, original);
      out.printf("Case #%d: %d\n", caseOn, original);
      
      caseOn++;
    }
    
    out.close();
  }
  
  static long cost(int i, int j) {
    // System.out.printf("Cost %d %d is cost %d\n", i, j, location[j]
    // - location[i]);
    return cost(location[j] - location[i]);
  }
  
  static long cost(long distance) {
    long ans = (n * distance) % mod;
    ans += ((distance - 1) * distance) / 2;
    ans %= mod;
    return ans;
  }
  
  static class IntervalTree {
    final static int MIN = 1, MAX = 2, SUM = 3;
    final static long inf = (long) 1e15;
    final static long[] bad = {0, inf, -inf, 0};
    int low, high;
    long[] vals; // ADD, MIN, MAX, SUM
    IntervalTree left, right;
    
    public IntervalTree(int l, int h) {
      this.low = l;
      this.high = h;
      int mid = (l + h) / 2;
      vals = new long[4];
      if (l != h) {
        left = new IntervalTree(l, mid);
        right = new IntervalTree(mid + 1, h);
      }
    }
    
    public void push() {
      if (vals[0] != 0) {
        right.increment(right.low, right.high, vals[0]);
        left.increment(left.low, left.high, vals[0]);
        vals[0] = 0;
      }
    }
    
    public void increment(int l, int h, long x) {
      if (h < l || l > high || h < low) return;
      l = Math.max(l, low);
      h = Math.min(h, high);
      if (l == low && h == high) {
        for (int i = 0; i < vals.length; i++)
          if (i != SUM) vals[i] += x;
        vals[SUM] += (high - low + 1) * x;
        return;
      }
      push();
      left.increment(l, h, x);
      right.increment(l, h, x);
      vals[MIN] = Math.min(left.vals[MIN], right.vals[MIN]);
      vals[MAX] = Math.max(left.vals[MAX], right.vals[MAX]);
      vals[SUM] = left.vals[SUM] + right.vals[SUM];
    }
    
    public long f(int l, int h, int i) {
      if (h < l || l > high || h < low) return bad[i];
      l = Math.max(l, low);
      h = Math.min(h, high);
      if (l == low && h == high) return vals[i];
      push();
      if (i == MIN) return Math.min(left.f(l, h, i), right.f(l, h, i));
      if (i == MAX) return Math.max(left.f(l, h, i), right.f(l, h, i));
      return left.f(l, h, i) + right.f(l, h, i);
    }
  }
}
