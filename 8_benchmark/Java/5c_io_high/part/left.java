import java.lang.Math;
import java.util.*;
import java.math.BigInteger;

public class left {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){

	int NumOfTest = sc.nextInt();
	for (int testId = 1; testId <= NumOfTest; testId++) {
	   
	    int N = sc.nextInt();
	    int K = sc.nextInt();
	    int B = sc.nextInt();
	    int T = sc.nextInt();

	    int[] pos = new int[N];
	    int[] speeds = new int[N];
	    int[] c = new int[N];
	    boolean[] possible = new boolean[N];

	    for (int counter = 0; counter < N; counter++) {
		pos[counter] = sc.nextInt();
	    }
	    for (int counter = 0; counter < N; counter++) {
		speeds[counter] = sc.nextInt();
	    }

	    for (int counter = 0; counter < N-1; counter++) {
		if (pos[counter] + speeds[counter] * T >= B) {
		    possible[counter] = true;
		} else {
		    possible[counter] = false;
		}
		if (speeds[counter] <= speeds[counter+1]) {
		    c[counter] = Integer.MAX_VALUE;
		} else {
		    c[counter] = (pos[counter+1]-pos[counter]+(speeds[counter]-speeds[counter+1]-1)) / (speeds[counter]-speeds[counter+1]);
		}
	    }
	    if (pos[N-1] + speeds[N-1] * T >= B) {
		possible[N-1] = true;
	    } else {
		possible[N-1] = false;
	    }
	    c[N-1] = Integer.MAX_VALUE;

	    int numOfSwap = 0;
	    int numOfPass = 0;
	    int backCounter = N-1;
	    while (backCounter >= 0 && numOfPass < K) {
		if (possible[backCounter]) {
		    numOfPass++;
		    backCounter--;
		} else {
		    numOfSwap += K - numOfPass;
		    backCounter--;
		}
	    }
	    if (numOfPass < K) {
		System.out.printf("Case #%d: IMPOSSIBLE\n", testId);
	    } else {
		System.out.printf("Case #%d: %d\n", testId, numOfSwap);
	    }
	}
    }
}
