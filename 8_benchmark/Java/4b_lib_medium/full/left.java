import java.lang.Math;
import java.util.*;
import java.math.BigInteger;

public class left {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){

	int no = sc.nextInt();
	for (int testId = 1; testId <= no; testId++) {
	   
	    int N = sc.nextInt();
	    int K = sc.nextInt();
	    int B = sc.nextInt();
	    int T = sc.nextInt();

	    int[] positions = new int[N];
	    int[] speeds = new int[N];
	    int[] catchups = new int[N];
	    boolean[] possible = new boolean[N];

	    for (int counter = 0; counter < N; counter++) {
		positions[counter] = sc.nextInt();
	    }
	    for (int counter = 0; counter < N; counter++) {
		speeds[counter] = sc.nextInt();
	    }

	    for (int cnt = 0; cnt < N-1; cnt++) {
		if (positions[cnt] + speeds[cnt] * T >= B) {
		    possible[cnt] = true;
		} else {
		    possible[cnt] = false;
		}
		if (speeds[cnt] <= speeds[cnt+1]) {
		    catchups[cnt] = Integer.MAX_VALUE;
		} else {
		    catchups[cnt] = (positions[cnt+1]-positions[cnt]+(speeds[cnt]-speeds[cnt+1]-1)) / (speeds[cnt]-speeds[cnt+1]);
		}
	    }
	    if (positions[N-1] + speeds[N-1] * T >= B) {
		possible[N-1] = true;
	    } else {
		possible[N-1] = false;
	    }
	    catchups[N-1] = Integer.MAX_VALUE;

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
