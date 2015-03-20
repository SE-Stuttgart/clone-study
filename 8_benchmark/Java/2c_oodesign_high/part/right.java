import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class right {
    private static final String FILE = "C-small-attempt1";

    private static BufferedReader in;
    private static PrintStream out;

    static {
        try {
            in = new BufferedReader(new FileReader(FILE + ".in"));
            out = new PrintStream(FILE + ".out");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        int T = nextInt();

        for (int t = 0; t < T; t++) {

            int p = nextInt();

            int[][] a;
            int[][] b = new int[200][200];
            for (int i = 0; i < p; i++) {
                int y1 = nextInt();
                int x1 = nextInt();
                int y2 = nextInt();
                int x2 = nextInt();
                for (int x = x1; x <= x2; x++)
                    for (int y = y1; y <= y2; y++)
                        b[x][y] = 1;
            }

            int result = 0;
            while (true) {

                a = b;
                b = new int[200][200];

                boolean ok = true;
                LOOP:
                for (int x = 0; x < 200; x++)
                    for (int y = 0; y < 200; y++)
                        if (a[x][y] > 0) {
                            ok = false;
                            break LOOP;
                        }

                if (ok) break;

                result++;

                for (int x = 0; x < 200; x++) {
                    for (int y = 0; y < 200; y++) {
                        int q = 0;
                        if (x > 0) q += a[x-1][y];
                        if (y > 0) q += a[x][y-1];
                        if (q == 0)
                            b[x][y] = 0;
                        if (q == 1)
                            b[x][y] = a[x][y];
                        if (q == 2)
                            b[x][y] = 1;
                    }
                }
            }

            out.println("Case #" + (t + 1) + ": " + result);
        }

        out.flush();
        in.close();
        out.close();
    }

    private static String line;
    private static StringTokenizer tokenizer;

    public static String nextToken() throws IOException {
        while (line == null || tokenizer == null || !tokenizer.hasMoreTokens()) {
            line = nextLine();
            if (line == null)
                return null;
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    public static String nextLine() throws IOException {
        line = null;
        tokenizer = null;
        return in.readLine();
    }

    public static int nextInt() throws IOException {
        return (int) nextLong();
    }

    public static long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    public static double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

}
