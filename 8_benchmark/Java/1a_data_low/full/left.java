import java.io.*;
import java.util.*;

public class left {
    static Scanner in;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new Scanner(new File("A-small.in"));
        out = new PrintWriter(new FileWriter("B.out"));
        input();
        out.close();
        in.close();
    }

    static String r;
    static void process() throws IOException {
        int swaps = 0;
        int inBarn = 0;
        int noSlow = 0;
        for (int n=N-1; n>=0; n--) {
            int loc = T*V[n]+X[n];
            if (loc >= 4) {
                swaps+=noSlow;
                inBarn++;
                if (inBarn==K) {
                    break;
                }
            } else {
                noSlow++;
            }
        }
        if (inBarn == K) {
            r = Integer.toString(swaps);
        }  else {
            r = "IMPOSSIBLE";
        }
        output();
    }

    static int caseNo = 0;
    static void output() throws IOException {
        out.println("Case "+(++caseNo)+": "+r);
    }

    static int N;
    static int K;
    static int R;
    static int T;
    static int[] X;
    static int[] V;
    static void input() throws IOException {
        int C = in.nextInt();
        for (int c=0; c<C; c++) {
            N = in.nextInt();
            K = in.nextInt();
            R = in.nextInt();
            T = in.nextInt();
            X = new int[N];
            V = new int[N];
            for (int n=0; n<N; n++) {
                X[n] = in.nextInt();
            }
            for (int n=0; n<N; n++) {
                V[n] = in.nextInt();
            }
            process();
        }
    }

}
