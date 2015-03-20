import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 */
public class right {

    static Scanner input;
    static BufferedWriter output;

    public static void main(String[] args) throws Exception {
        input = new Scanner(new File("A.txt"));
        output = new BufferedWriter(new FileWriter("B.txt"));
        int T = input.nextInt();
        for (int i = 1; i <= T; i++) {
            String result = getR();
            System.out.println("Case #" + i + ": " + result);
            output.write("Case #" + i + ": " + result);
            output.newLine();
        }
        output.close();
    }

    public static String getR() {
        long L = input.nextLong();
        long t = input.nextLong();
        int N = input.nextInt();
        int C = input.nextInt();
        long[] distances = new long[N];
        for (int i = 0; i < C; i++) {
            long a = input.nextLong();
            for (int k = 0; k < N; k++) {
                if (k * C + i >= N) break;
                distances[k * C + i] = a;
            }
        }
        long[] absDist = new long[N];
        for (int i = 0; i < N; i++) {
            if (i == 0) absDist[i] = distances[i];
            else absDist[i] = absDist[i-1] + distances[i];
        }
        long shipPos = t/2;
        if (shipPos >= absDist[N-1]) {
            return Long.toString(absDist[N-1] * 2);
        }
        int previousStar = 0;
        for (int i = 0; i < N - 1; i++) {
            if (absDist[i] <= shipPos && absDist[i+1] > shipPos) {
                previousStar = i + 1;
                break;
            }
        }
        long[] segLeft;
        if (previousStar != 0) {
            segLeft = new long[N - previousStar];
            int previousStarIndex = previousStar - 1;
            for (int i = 0; i < segLeft.length; i++) {
                if (i == 0) segLeft[i] = absDist[i + previousStarIndex + 1] - shipPos;
                else segLeft[i] = distances[i + previousStarIndex + 1];
            }
        } else {
            segLeft = new long[N];
            for (int i = 0; i < segLeft.length; i++) {
                if (i == 0) segLeft[i] = distances[0] - shipPos;
                else segLeft[i] = distances[i];
            }
        }

        Arrays.sort(segLeft);
        long totalTime = absDist[N-1] * 2;
        if (L > segLeft.length) L = segLeft.length;
        for (int i = 0; i < L; i++) {
            totalTime -= segLeft[segLeft.length - i - 1];
        }
        assert(totalTime <= absDist[N-1] * 2);
        return Long.toString(totalTime);
    }

}
