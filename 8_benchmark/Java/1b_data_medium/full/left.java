import java.io.*;
import java.util.*;

public class left {

    private static final String FILE_NAME = "file";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(FILE_NAME + ".in"));
        BufferedWriter out = new BufferedWriter(new FileWriter(FILE_NAME + ".out"));

        int t = Integer.parseInt(in.readLine());
        for (int c = 1; c <= t; ++c) {
            int t2 = Integer.parseInt(in.readLine());

            SortedMap<Integer, List<Attack>> attacks = new TreeMap<Integer, List<Attack>>();
            for (int i = 0; i < t2; ++i) {
                Tribe tribe = new Tribe(in.readLine());
                for (Attack attack : tribe.createAttacks()) {
                    List<Attack> dA = attacks.get(attack.d);
                    if (dA == null) {
                        dA = new ArrayList<Attack>();
                        attacks.put(attack.d, dA);
                    }
                    dA.add(attack);
                }
            }

            int successful = 0;
            Wall wall = new Wall();
            for (List<Attack> dayAttacks : attacks.values()) {
                successful += attack(wall, dayAttacks);
                build(wall, dayAttacks);
            }
            write(out, String.format("Case #%d: %d", c, successful));
        }
        out.close();
    }

    private static void build(Wall wall, List<Attack> attacks) {
        for (Attack attack : attacks) {
            wall.buildWall(attack.w, attack.e, attack.s);
        }
    }

    private static int attack(Wall wall, List<Attack> attacks) {
        int successful = 0;
        for (Attack attack : attacks) {
            if (!wall.withstands(attack.w, attack.e, attack.s)) {
                //System.out.println("ok");
                ++successful;
            }
        }
        return successful;
    }

    private static void write(BufferedWriter out, String msg) throws IOException {
        out.write(msg);
        out.write("\n");
        System.out.println(msg);
    }

    private static class Tribe {
        private final int d, n, w, e, s, dd, dp, ds;

        public Tribe (String data) {
            String[] row = data.split(" ");
            d = Integer.parseInt(row[0]);
            n = Integer.parseInt(row[1]);
            w = Integer.parseInt(row[2]);
            e = Integer.parseInt(row[3]);
            s = Integer.parseInt(row[4]);
            dd = Integer.parseInt(row[5]);
            dp = Integer.parseInt(row[6]);
            ds = Integer.parseInt(row[7]);
        }

        public List<Attack> createAttacks() {
            List<Attack> attacks = new ArrayList<Attack>();
            for (int i = 0; i < n; ++i) {
                attacks.add(new Attack(d+dd*i, w+dp*i, e+dp*i, s+ds*i));
            }
            return attacks;
        }
    }

    private static class Attack {
        private final int d, w, e, s;

        private Attack(int d, int w, int e, int s) {
            this.d = d;
            this.w = w;
            this.e = e;
            this.s = s;
        }
    }

    private static class Wall {

        private final List<Height> heights = new ArrayList<Height>();

        public void buildWall(int w, int e, int s) {
            heights.add(new Height(w, e, s));
        }

        public boolean withstands(int w, int e, int s) {
            Collections.sort(heights);
            for (Height height : heights) {
                if (height.s >= s) {
                    if (height.w <= w && height.e >= e) {
                        return true;
                    }
                    if (height.w <= w && height.e >= w) {
                        w = height.e;
                    }
                }
            }
            return false;
        }

        private static class Height implements Comparable<Height> {

            private final int w, e, s;

            private Height(int w, int e, int s) {
                this.w = w;
                this.e = e;
                this.s = s;
            }

            @Override
            public int compareTo(Height height) {
                return w-height.w;
            }
        }
    }
}
