import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;


public class left {
	public static void pl(Object o){System.out.println(o);}
	  
	public static void main(String[] args) throws IOException
	{
	    Scanner sc = new Scanner(System.in);
	    int test_cases = sc.nextInt();
	    for(int k = 0; k < test_cases; k++)
	    {
			problem(sc, k+1);
	    }
	}
  
	public static void problem(Scanner sc, int iteration)
	{
	    System.out.print("Case #" + iteration + ": ");
	    int l = sc.nextInt();
	    double t = sc.nextDouble();
	    int n = sc.nextInt();
	    int c = sc.nextInt();
	    double d[] = new double[c];
	    double td[] = new double[n];
	    for(int i = 0; i < c; i++)
	    {
	    	d[i] = sc.nextDouble();
	    }
	    int first_usable = -1;
	    double time_left_after_usable = -1;
	    double accum = 0;
	    double before = 0;
	    boolean set = false;
	    double before_boost = 0;
	    for(int i = 0; i < n; i++)
	    {
	    	td[i] = d[i%c];
	    	accum+= td[i]*2;
	    	if(accum > t && !set)
	    	{
	    		set = true;
	    		first_usable = i;
	    		time_left_after_usable = accum - t;
	    		before_boost = td[i]*2 - time_left_after_usable;
	    	}
	    	if(!set)
	    	{
	    		before += td[i]*2;
	    	}
	    }
	    if(first_usable != -1)
	    {
			before += before_boost;
	    	int pleft = n-first_usable;
	    	double sub_array[] = new double[pleft];
	    	sub_array[0] = time_left_after_usable;
	    	for(int i = 1; i < pleft; i++)
	    	{
	    		sub_array[i] = td[(n-pleft)+i]*2;
	    	}
	    	Arrays.sort(sub_array);
	    	for(int i = 0; i < pleft-l; i++)
	    	{
	    		before += sub_array[i];
	    	}
	    	for(int i = pleft-l; i < pleft; i++)
	    	{
	    		before += sub_array[i]/2;
	    	}
	    	pl(Math.round(before));
	    }
	    else
	    	pl(Math.round(accum));
	}
}
