import java.util.ArrayList;
import java.util.List;

public class left extends Problem {

    public left() {
        super("C.in");
    }

    public static void main(String[] args) {
        new left().solve();
    }

    @Override
    protected String run() {
        int count = 0;
        int N = Integer.valueOf(input.nextLine());
        Tribe[] tribes = new Tribe[N];
        for (int i = 0; i < N; i++) {
            String[] s = input.nextLine().split(" ");
            Tribe t = new Tribe();
            t.di = Integer.valueOf(s[0]);
            t.ni = Integer.valueOf(s[1]);
            t.left = Integer.valueOf(s[2]);
            t.right = Integer.valueOf(s[3]);
            t.si = Integer.valueOf(s[4]);
            t.delta_di = Integer.valueOf(s[5]);
            t.delta_pi = Integer.valueOf(s[6]);
            t.delta_si = Integer.valueOf(s[7]);
            tribes[i] = t;
        }

        GreatWall gw = new GreatWall();

        List<Tribe> list = new ArrayList<Tribe>(10);

        while (true) {
            list.clear();
            int min = Integer.MAX_VALUE;
            for (Tribe tribe : tribes) {
                if (tribe.di < min){
                    min = tribe.di;
                }
            }

            if (min == Integer.MAX_VALUE){
                return "" + count;
            }


            for (Tribe tribe : tribes) {
                if (tribe.di == min){
                    if (gw.attack(tribe)){
                        count++;
                        list.add(tribe);
                    } else {
                        tribe.next();
                    }
                }
            }

            for (Tribe tribe : list){
                gw.buildWall(tribe);
                tribe.next();
            }
        }
    }

    class GreatWall{

        ArrayList<Wall> list = new ArrayList<Wall>(100);

        boolean attack(Tribe t) {
            L: for (double i = t.left; i <= t.right; i += 0.5){
                for (Wall w : list) {
                    if (i >= w.left && i <= w.right && w.si >= t.si){
                        continue L;
                    }
                }
                return true;
            }
            return false;
        }

        public void buildWall(Tribe tribe) {
            list.add(new Wall(tribe.left, tribe.right, tribe.si));
        }
    }

    class Wall{
        int left;
        int right;
        int si;

        Wall(int wi, int right, int si) {
            this.left = wi;
            this.right = right;
            this.si = si;
        }
    }


    class Tribe{
        int di,ni, left, right,si,delta_di,delta_pi,delta_si;

        boolean next(){
            di += delta_di;
            left += delta_pi;
            right += delta_pi;
            si += delta_si;
            ni--;
            if (ni > 0){
                return true;
            } else {
                di = Integer.MAX_VALUE;
                return false;
            }
        }
    }


}