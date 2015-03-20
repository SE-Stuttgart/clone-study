import java.io.*;
import java.util.*;

public class left implements Runnable {
	public static void main(String[] args) throws IOException {
		new Thread(new left()).start();
	}

	public BufferedReader br;

	public StringTokenizer in;

	public PrintWriter out;

	public String nextToken() throws IOException {
		while (in == null || !in.hasMoreTokens()) {
			in = new StringTokenizer(br.readLine());
		}

		return in.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	public class Event implements Comparable<Event> {
		int x, f, p;

		public Event(int x, int f, int p) {
			this.x = x;
			this.f = f;
			this.p = p;
		}

		public int compareTo(Event e) {
			return x == e.x ? f - e.f : x - e.x;
		}
	}

	public class Card implements Comparable<Card> {
		int id, from, p;

		public Card(int id, int from, int p) {
			this.id = id;
			this.from = from;
			this.p = p;
		}

		public int compareTo(Card c) {
			return from == c.from ? id - c.id : from - c.from;
		}
	}

	long mod = 1000002013;
	long ni;

	public long get_dist(int x) {
		if (x == 0)
			return 0;

		return (ni * (ni + 1) / 2 - (ni - x) * (ni + 1 - x) / 2) % mod;
	}

	public void solve() throws IOException {
		ni = nextInt();
		int m = nextInt();

		Event[] e = new Event[2 * m];

		long ans = 0;
		for (int i = 0; i < m; i++) {
			int s = nextInt();
			int f = nextInt();
			int p = nextInt();

			ans = (ans + ((get_dist(f - s) * p) % mod)) % mod;
			e[2 * i] = new Event(s, 1, p);
			e[2 * i + 1] = new Event(f + 1, -1, p);
		}

		Arrays.sort(e);
		TreeSet<Card> cd = new TreeSet<Card>();

		for (int i = 0; i < e.length; i++) {
			if (e[i].f == 1) {
				cd.add(new Card(i, e[i].x, e[i].p));
			} else {
				int to_get = e[i].p;

				while (to_get > 0) {
					Card near = cd.last();

					int take = Math.min(to_get, near.p);

					near.p -= take;
					to_get -= take;

					if (near.p == 0)
						cd.pollLast();

					ans = ((ans - ((get_dist(e[i].x - 1 - near.from) * take) % mod))
							% mod + mod)
							% mod;
				}
			}
		}
		out.println(ans);
	}

	public void run() {
		try {
			br = new BufferedReader(new FileReader("input.txt"));
			out = new PrintWriter("output.txt");

			int t = nextInt();
			for (int i = 0; i < t; i++) {
				out.print("Case #" + (i + 1) + ": ");
				solve();
				System.err.println("Finished " + (i + 1) + " test!");
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
