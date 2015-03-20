import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class B {

    int[] tab = new int[1000009];

    private void solve() throws IOException {
        int n = nextInt();
        for (int testCase = 1; testCase <= n; testCase++) {
            out.println("Case #" + testCase + ": " + solveOne());
        }
    }

    private double solveOne() throws IOException {
        int c = nextInt();
        int d = nextInt();
        int n = 0;
        for (int i = 0; i < c; i++) {
            int p = nextInt();
            int v = nextInt();
            for (int j = 0; j < v; j++) {
                tab[n++] = p;
            }
        }
        double l = 0, p = 1000000000000.0;
        int count = 0;
       // while (Math.abs(p-l) > 0.00001) {
       for (int z = 0; z < 100; z++) {
            count++;
            double time = (l + p) / 2;
            double currPos = tab[0] - time;
            boolean ok = true;
            for (int i = 1; i < n; i++) {
                currPos += d;
                if (Math.abs(tab[i] - currPos) > time) {
                    if (tab[i] > currPos) {
                        currPos = tab[i] - time;
                    } else {
                        ok = false;
                        break;
                    }
                }
            }
            if (ok) {
                p = time;
            } else {
                l = time;
            }
        }
        System.err.println(count);
        return (l + p) / 2;
    }

   //
    
    private BufferedReader in;
    private PrintWriter out;
    private StringTokenizer strTok;

    private String nextLine() throws IOException {
        return in.readLine();
    }

    private String nextToken() throws IOException {
        while (strTok == null || !strTok.hasMoreTokens()) {
            String line = nextLine();
            if (line != null) {
                strTok = new StringTokenizer(line);
            } else {
                return null;
            }
        }
        return strTok.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    private long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    private double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    private BigInteger nextBig() throws IOException {
        return new BigInteger(nextToken());
    }

    public static void main(String[] args) {
        new B().run();
    }

    private long getMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    private void run() {
        Locale.setDefault(Locale.US);
        try {
            boolean ideaIDE = System.getProperty("IDEA") != null;
            if (ideaIDE) {
                in = new BufferedReader(new FileReader("src/" + B.class.getSimpleName() + ".in"));
                out = new PrintWriter("src/" + B.class.getSimpleName() + ".out");
            } else {
                in = new BufferedReader(new InputStreamReader(System.in));
                out = new PrintWriter(System.out);
            }
            long startMem = getMemory();
            long startTime = System.currentTimeMillis();
            solve();
            long endTime = System.currentTimeMillis();
            long endMem = getMemory();
            if (ideaIDE) {
                System.out.println("Running time = " + (endTime - startTime) + "ms");
                System.out.println("Memory used = " + (endMem - startMem) + "MB");
                System.out.println("Total memory = " + getMemory() + "MB");
            }
            in.close();
            out.close();
            System.exit(0);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }
    }


}
