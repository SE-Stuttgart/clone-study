import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class right {

	static BufferedWriter out;
	static int tC;
	static final int D = 0;
	static final int N = 1;
	static final int W = 2;
	static final int E = 3;
	static final int S = 4;
	static final int D_D = 5;
	static final int D_P = 6;
	static final int D_S = 7;
	
	static int[] wall = new int[401];
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("A.in"));
		out = new BufferedWriter(new FileWriter("B.out"));

		String line;
		
		line = br.readLine();
		tC = Integer.parseInt(line);
		
		for (int i = 0; i < tC; i++) {
			if (i > 0) {
				out.newLine();
			}
			
			int tribeCount = Integer.parseInt(br.readLine());
			
			int[][] tribes = new int[tribeCount][8];
			ArrayList<Attack> attacks = new ArrayList<Attack>();
			
			for (int j = 0; j < tribeCount; j++) {
				String[] data = br.readLine().split(" ");
				tribes[j][D] = Integer.parseInt(data[D]);
				tribes[j][N] = Integer.parseInt(data[N]);
				tribes[j][W] = Integer.parseInt(data[W]);
				tribes[j][E] = Integer.parseInt(data[E]);
				tribes[j][S] = Integer.parseInt(data[S]);
				tribes[j][D_D] = Integer.parseInt(data[D_D]);
				tribes[j][D_P] = Integer.parseInt(data[D_P]);
				tribes[j][D_S] = Integer.parseInt(data[D_S]);
				
				eA(tribes[j], attacks);
			}
			
			wall = new int[401];
			Collections.sort(attacks);
			int successfulAttacks = simulateHistory(attacks);
			
			out.write("Case #" + (i + 1) + ": " + successfulAttacks);
			System.out.println("Case #" + (i + 1) + ": " + successfulAttacks);
		}

		br.close();
		out.close();
	}

	private static void eA(int[] tribe, ArrayList<Attack> att) {
		int day = tribe[D];
		int west = tribe[W];
		int east = tribe[E];
		int strength = tribe[S];
		int delta_dist = tribe[D_P];
		int delta_day = tribe[D_D];
		int delta_s = tribe[D_S];
		
		att.add(new Attack(day, east, west, strength));
		
		for (int i = 1; i < tribe[N]; i++) {
			day += delta_day;
			west += delta_dist;
			east += delta_dist;
			strength += delta_s;
			
			att.add(new Attack(day, east, west, strength));
		}
	}

	private static int simulateHistory(ArrayList<Attack> attacks) {
		int successfulAttacks = 0;
		ArrayList<Breach> breaches = new ArrayList<Breach>();
		for (int i = 0; i < attacks.size(); i++) {
			Attack attack = attacks.get(i);
			ArrayList<Breach> outcome = willBreach(attack.west, attack.east, attack.strength);
			
			if (outcome.size() != 0) {
				successfulAttacks++;
			}
			
			breaches.addAll(outcome);
			if (attacks.size() <= i + 1 || attacks.get(i + 1).day != attack.day) {
				fixWall(breaches);
				breaches.clear();
			}
		}
		return successfulAttacks;
	}

	private static void fixWall(ArrayList<Breach> breaches) {
		for (Breach breach : breaches) {
			if (wall[breach.position] < breach.height) {
				wall[breach.position] = breach.height;
			}
		}
	}

	private static ArrayList<Breach> willBreach(int w, int e, int strenght) {
		w += 200;
		e += 200;
		ArrayList<Breach> breaches = new ArrayList<Breach>();
		for (int i = w; i < e; i++) {
			if (wall[i] < strenght) {
				breaches.add(new Breach(i, strenght));
			}
		}
		return breaches;
	}
}

class Breach {
	int position, height;
	public Breach(int position, int height) {
		this.position = position;
		this.height = height;
	}
}

class Attack implements Comparable<Attack> {
	
	public int day, east, west, strength;
	
	public Attack(int day, int east, int west, int strength) {
		this.day = day;
		this.east = east;
		this.west = west;
		this.strength = strength;
	}
	
	@Override
	public int compareTo(Attack other) {
		if (other.day < this.day) {
			return 1;
		} else if (other.day > this.day) {
			return -1;
		} else {
			return 0;
		}
	}
}
