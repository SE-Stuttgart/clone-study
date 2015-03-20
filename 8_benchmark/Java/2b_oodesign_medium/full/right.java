import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;


public class right {

	Scanner in;
	PrintWriter out;

	/**
	 * @param sc = new Scanner(File(input file name))
	 * @param pw = new PrintWriter(output file name)
	 */
	public right(Scanner sc, PrintWriter pw) {
		in = sc;
		out = pw;
        int nCases = readInt();
        for (int i=1; i<=nCases; i++) {
           processCase(i);
        }
	}

    void processCase(int caseNum) {
    	int P = readInt();
    	int[] M = readInts();
    	for (int i=0; i<P; i++) {
    		readInts();// all prices equals to 1 so ignore
    	}
    	for (int i=0; i<M.length; i++) {
    		M[i] = P - M[i];
    	}
    	int ans = rec(M, M.length);
    	out.println("Case #" + caseNum + ": " + ans);
    }

    int rec(int[] M, int len) {
    	boolean allZeros = true;
    	for (int m: M) if (m > 0) {
    		allZeros = false;
    		break;
    	}
    	if (allZeros) return 0;
    	int[] M1 = new int[len/2];
    	int[] M2 = new int[len/2];
    	for (int i=0; i<len/2; i++) {
    		M1[i] = M[i] - 1;
    		M2[i] = M[i+len/2] - 1;
    	}
    	return 1 + rec(M1, len/2) + rec(M2, len/2);
    }

	   int[] parseIntArray(String s) {
	      StringTokenizer st = new StringTokenizer(s, " ");
	      int[] ret = new int[st.countTokens()];
	      for (int i=0; i<ret.length; i++) {
	         ret[i] = Integer.parseInt(st.nextToken());
	      }
	      return ret;
	   }

	   int nextInt() {
		   return Integer.parseInt(in.next());
	   }

	   int readInt() {
	      return Integer.parseInt(in.nextLine().trim());
	   }

	   int[] readInts() {
	      return parseIntArray(in.nextLine());
	   }

	   String readString() {
	      return in.nextLine();
	   }

	   String[] parseStringArray(String s) {
	      StringTokenizer st = new StringTokenizer(s, " ");
	      String[] ret = new String[st.countTokens()];
	      for (int i=0; i<ret.length; i++) {
	         ret[i] = st.nextToken();
	      }
	      return ret;
	   }

	   String[] readStrings() {
	      StringTokenizer st = new StringTokenizer(in.nextLine(), " ");
	      String[] ret = new String[st.countTokens()];
	      for (int i=0; i<ret.length; i++) {
	         ret[i] = st.nextToken();
	      }
	      return ret;
	   }

}
