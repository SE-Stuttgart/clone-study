import java.util.*;
import static java.lang.Math.*;

public class right {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int T = in.nextInt();
    for (int zz = 1; zz <= T; zz++) {
      int N = in.nextInt();
      long[] S = new long[N];
      long mx = Long.MIN_VALUE;
      for (int i = 0; i < N; i++) {
        S[i] = in.nextInt();
        mx = max(mx, S[i]);
      }
      long ans = 0;
      for (int i = 0; i < N; i++) {
        long opt1 = 0; // S[i]
        long opt2 = 0; // 2 max - S[i]
        for (int j = 0; j < N; j++) {
          if (i == j) {
            continue;
          }
          if (S[j] > S[i]) {
            if (i < j) {
              opt1++;
            } else {
              opt2++;
            }
          }
        }
        ans += min(opt1, opt2);
      }
      System.out.format("Case #%d: %d\n", zz, ans);
    }
  }
}
