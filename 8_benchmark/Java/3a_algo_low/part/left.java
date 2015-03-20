import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class left {
    private FileReader reader;
    private FileWriter writer;
    private BufferedReader in;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        new left().run();
    }

    private void run() throws IOException {
        reader = new FileReader(new File("a.in"));
        writer = new FileWriter(new File("a.out"));
        in = new BufferedReader(reader);
        solve();
        reader.close();
        writer.close();
    }

    private void solve() throws IOException {
        String tStr = in.readLine();
        int C = Integer.parseInt(tStr);
        for (int c = 1; c <= C; c++) {
            String str = in.readLine();
            String[] nkbt = str.split(" ");
            int n = Integer.parseInt(nkbt[0]);
            int k = Integer.parseInt(nkbt[1]);
            long b = Long.parseLong(nkbt[2]);
            int t = Integer.parseInt(nkbt[3]);
            String xStr = in.readLine();
            String[] xx = xStr.split(" ");
            String vStr = in.readLine();
            String[] vv = vStr.split(" ");
            int x[] = new int[n];
            int v[] = new int[n];
            int xt[] = new int[n];
            for (int i = 0; i < n; i++) {
                x[i] = Integer.parseInt(xx[i]);
                v[i] = Integer.parseInt(vv[i]);
            }
            int cost[] = new int[n];
            for (int i = 0; i < n; i++) {
                if (x[i] + t * v[i] < b) {
                    cost[i] = 1000000;
                    continue;
                }
                int cst = 0;
                for (int j = 0; j < n; j++) {
                    if (x[j] + t * v[j] < b) {
                        if (x[i] < x[j]) {
                            cst++;
                        }
                    }
                }
                cost[i] = cst;
            }
            Arrays.sort(cost);
            int total = 0;
            for (int i = 0; i < k; i++) {
                total+=cost[i];
            }
            if (total < 1000000) {
                writer.append("Case #" + String.valueOf(c) + ": " + total + "\r\n");
            } else {
                writer.append("Case #" + String.valueOf(c) + ": IMPOSSIBLE\r\n");
            }
        }
    }
}