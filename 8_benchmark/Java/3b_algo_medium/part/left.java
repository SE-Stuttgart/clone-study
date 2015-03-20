import java.io.PrintWriter;
import java.util.*;

public class left {

    public void test(Scanner sc,PrintWriter output) throws Exception {
        final int count=sc.nextInt();
        for(int i=1;i<=count;i++) {
            int n=sc.nextInt();
            int t=sc.nextInt();
            int[] d1=new int[n];
            int[] d2=new int[n];
            for(int j=0;j<n;j++) {
                d1[j]=sc.nextInt();
                d2[j]=sc.nextInt();
            }
            double d=test(d1,d2,t);
            output.printf("Case #%d: %s\n",i,String.valueOf(d));
        }
    }

    public double test(int[] d1,int[] d2,int t) {
        List<Long> l1 = new ArrayList<Long>();
        List<Long> l2 = new ArrayList<Long>();
        int size=d1.length;
        long index=0;
        for(int i=0;i<size;i++) {
            l1.add((long)d1[i]);
            l2.add(index);
            if(d2[i]>1) {
                l1.add((long)d1[i]);
                l2.add(index+d2[i]-1);
            }
            index+=d2[i];
        }
        size=l1.size();
        long[] dataA=new long[size];
        long[] dataB=new long[size];
        for(int i=0;i<size;i++) {
            dataA[i]=l1.get(i);
            dataB[i]=l2.get(i);
        }
        double max=-1;
        for(int i=0;i<size-1;i++) {
            for(int j=i+1;j<size;j++) {
                double m=Math.abs(dataB[i]-dataB[j])*1.0*t-Math.abs(dataA[i]-dataA[j]);
                if(m>max) {
                    max=m;
                }
            }
        }
        assert(max>=0);
        return max/2;
    }

}
