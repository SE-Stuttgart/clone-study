import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class left
{
  public static int T;
  public static PrintWriter writer;

  public static BigInteger t;
  public static int L, N, C;
  public static BigInteger[] dist;
  public static BigInteger[] Cs;

  public static void Solve()
  {
    BigInteger firstLength = t.divide(new BigInteger("2"));    
    
    int start = 0;
    for (start = 0; start < N; ++start)
    {
      firstLength = firstLength.subtract(dist[start]);
      if (firstLength.compareTo(BigInteger.ZERO) < 0)
        break;      
    }    

    ArrayList<BigInteger> rest = new ArrayList<BigInteger>();

    if (firstLength.compareTo(BigInteger.ZERO) < 0)
      rest.add(firstLength.abs());

    for (start = start + 1; start < N; ++start)
    {
      rest.add(dist[start]);
    }

    Comparator comparator = Collections.reverseOrder();
    Collections.sort(rest, comparator);

    BigInteger total = BigInteger.ZERO;

    for (int i = 0; i < N; ++i)
      total = total.add(dist[i].multiply(new BigInteger("2")));

    if (total.compareTo(t) > 0)
      total = t;    

    if (!rest.isEmpty())
    {
      for (int i = 0; i < L && i < rest.size(); ++i)
        total = total.add(rest.get(i));

      for (int i = L; i < rest.size(); ++i)
        total = total.add(rest.get(i).multiply(new BigInteger("2")));
    }



    writer.println(total);
  }

  public static void main(String[] args) throws Exception
  {
    Scanner sc = new Scanner(new File("B-large.in"));
    writer = new PrintWriter(new File("output.txt"));

    T = sc.nextInt();

    dist = new BigInteger[1000010];
    Cs = new BigInteger[1000010];
    
    for (int tt = 1; tt <= T; ++tt)
    {
      L = sc.nextInt();
      t = new BigInteger(sc.next());     
      N = sc.nextInt();
      C = sc.nextInt();

      for (int c = 0; c < C; ++c)
      {
        Cs[c] = new BigInteger(sc.next());
      }

      for (int n = 0; n < N; ++n)
      {
        dist[n] = Cs[n % C];
      }

      writer.print("Case #" + tt + ": ");
      Solve();
    }
    
    writer.close();
  }
}
