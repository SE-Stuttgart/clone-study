import java.util.*;
import java.io.*;

public class right implements Runnable {
	private final String pid = getClass().getName().toUpperCase();
	private final String pt = "small";
	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;
	
	private void solveSingleTest(int testNumber) throws IOException {
		int r = nextInt();
		boolean[][] field = new boolean[100][100];
		for (int i = 0; i < r; i++) {
			int x1 = nextInt() - 1;
			int y1 = nextInt() - 1;
			int x2 = nextInt() - 1;
			int y2 = nextInt() - 1;
			for (int u = x1; u <= x2; u++) {
				for (int v = y1; v <= y2; v++) {
					field[u][v] = true;
				}
			}
		}
		
		boolean[][] newField = new boolean[100][100];
		int steps = 1;
		for ( ; ; steps++) {
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					newField[i][j] = field[i][j];
				}
			}
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					if (i >= 1 && j >= 1 && field[i - 1][j] && field[i][j - 1]) {
						newField[i][j] = true;
					}
					if ((i == 0 || !field[i - 1][j]) && (j == 0 || !field[i][j - 1])) {
						newField[i][j] = false;
					}
				}
			}
			boolean alive = false;
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					field[i][j] = newField[i][j];
					if (field[i][j]) {
						alive = true;
					}
				}
			}
			if (!alive) {
				break;
			}
		}
		out.println("Case #" + testNumber + ": " + steps);
	}
	
	private String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine());
		}
		return st.nextToken();
	}
	
	private int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
	
	public static void main(String[] args) {
		new Thread(new right()).start();
	}

	public void run() {
		try {
			solve();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void solve() throws IOException {
		in = new BufferedReader(new FileReader(new File("c.in")));
		out = new PrintWriter(new File("c.out"));
		
		int testsNumber = nextInt();
		for (int i = 0; i < testsNumber; i++) {
			solveSingleTest(i + 1);
		}
		
		in.close();
		out.close();
	}
}
