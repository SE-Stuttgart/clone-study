import java.io.*;
import java.util.*;

public class left {

    // system
    public static String charset = "UTF-8";
    public static Locale locale = new Locale("en", "US");
    public static PrintWriter stdOut, fileOut;
    public static Scanner stdIn, fileIn;

    // do
    public static class Attack implements Comparable<Attack>{
        int d, w, e, s;

        public int compareTo(Attack a) {
            return Integer.compare(d, a.d);
        }
    }

    public static void main(String[] args) {
        setIO("C.in", "D.out");
        Scanner input = fileIn;
        PrintWriter output = fileOut;


        int T = input.nextInt();
        for (int i = 1; i <= T; i++) {
            int N = input.nextInt();
            ArrayList<Attack> list = new ArrayList<Attack>();
            for (int j = 0; j < N; j++) {
                int d = input.nextInt();
                int n = input.nextInt();
                int w = input.nextInt();
                int b = input.nextInt();
                int s = input.nextInt();
                int delta_d = input.nextInt();
                int delta_p = input.nextInt();
                int delta_s = input.nextInt();
                Attack a;
                for (int k = 0; k < n; k++) {
                    a = new Attack();
                    a.d = d;
                    a.w = w;
                    a.e = b;
                    a.s = s;
                    list.add(a);
                    d += delta_d;
                    w += delta_p;
                    b += delta_p;
                    s += delta_s;
                }
            }
            Collections.sort(list);

            int total = 0;
            int[] arrW = new int[1001*2];
            ArrayList<Attack> oneday = new ArrayList<Attack>();
            for (Attack a : list) {
                if (oneday.size() > 0 && oneday.get(0).d < a.d) {
                    for (Attack at : oneday) {
                        int iw = at.w*2 + 1000;
                        int ie = at.e*2 + 1000;
                        for (int j = iw; j <= ie; j++) {
                            if (arrW[j] < at.s) {
                                arrW[j] = at.s;
                            }
                        }
                    }
                    oneday.clear();
                }
                int iw = a.w*2 + 1000;
                int ie = a.e*2 + 1000;
                for (int j = iw; j <= ie; j++) {
                    if (arrW[j] < a.s) {
                        total++;
                        oneday.add(a);
                        break;
                    }
                }
            }

            String resStr = "Case #" + i + ": " + total;
            output.println(resStr);
            stdOut.println(resStr);
        }

        input.close();
        output.close();
    }

    // task
    public static String solve() {
        return "";
    }

    // system
    public static void setIO(String fileInName, String fileOutName) {
        stdIn = new Scanner(new BufferedInputStream(System.in), charset);
        stdIn.useLocale(locale);

        try {
            stdOut = new PrintWriter(new OutputStreamWriter(System.out, charset), true);
        }
        catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }

        if (fileInName != null) {
            try {
                File file = new File(fileInName);
                if (file.exists()) {
                    fileIn = new Scanner(file, charset);
                    fileIn.useLocale(locale);
                }
            }
            catch (IOException ioe) {
                System.err.println("Could not open " + fileInName);
            }
        }

        if (fileOutName != null) {
            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileOutName), charset);
                fileOut = new PrintWriter(osw, true);
            }
            catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
