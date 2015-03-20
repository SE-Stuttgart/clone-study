import java.io.*;
import java.util.*;
import java.math.*;

public class left
{
	public static void main(String[] args) throws Exception
	{
	    int c;	
		RandomAccessFile in = new RandomAccessFile(args[0],"r");
		c=Integer.parseInt(in.readLine());

		for (int i=1; i<=c; i++)
		{
		    int R=Integer.parseInt(in.readLine());
		    int[][] a=new int[100][100];
		    for (int k=0; k<R; k++)
		    {
                String[] temp=in.readLine().split(" ");
                int x1=Integer.parseInt(temp[0]);
                int y1=Integer.parseInt(temp[1]);
                int x2=Integer.parseInt(temp[2]);
                int y2=Integer.parseInt(temp[3]);
                for (int x=x1-1; x<=x2-1; x++)
                    for (int y=y1-1; y<=y2-1; y++)
                        a[x][y]=1;  
            }                                     
            String ans=solve(a);
            System.out.println("Case #"+i+": "+ans);
        }

		in.close();
	}

    public static String solve(int[][] b)
    {
        int[] dx={-1,0,1,0};
        int[] dy={0,-1,0,1};
        int count=0;
        for (int i=0; i<b.length; i++)
            for (int k=0; k<b[0].length; k++)
                count+=b[i][k];
    
        int time=0;
        while(count>0)
        {
            int[][] a=new int[b.length][b.length];
            for (int i=0; i<b.length; i++)
                for (int k=0; k<b.length; k++)
                    a[i][k]=b[i][k];
        
            for (int i=0; i<a.length; i++)
            {
                for (int k=0; k<a[0].length; k++)
                {
                    int north=-1;
                    if (i-1>=0) north=b[i-1][k];
                    int west=-1;
                    if (k-1>=0) west=b[i][k-1];
                    if (b[i][k]==1 && north!=1 && west!=1)
                    {
                        a[i][k]=0;
                        count--;
                    }
                    else if (b[i][k]==0 && north==1 && west==1)
                    {
                        a[i][k]=1;
                        count++;
                    }
                }
            }
            
            for (int i=0; i<b.length; i++)
                for (int k=0; k<b.length; k++)
                    b[i][k]=a[i][k];            
            
            time++;
        }
        
        return ""+time;
 	}
}

