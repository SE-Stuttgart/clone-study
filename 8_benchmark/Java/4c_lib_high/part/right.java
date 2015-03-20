import java.io.*;
import java.util.*;

public class right
{
	String line;
	StringTokenizer inputParser;
	BufferedReader is;
	FileInputStream fstream;
	DataInputStream in;
	
	void openInput(String file)
	{

		try{
			fstream = new FileInputStream(file);
			in = new DataInputStream(fstream);
			is = new BufferedReader(new InputStreamReader(in));
		}catch(Exception e)
		{
			System.err.println(e);
		}

	}
	
	void readNextLine()
	{
		try {
			line = is.readLine();
			inputParser = new StringTokenizer(line, " ");
		} catch (IOException e) {
			System.err.println("Unexpected IO ERROR: " + e);
		}	
		
	}
	
	int NextInt()
	{
		String n = inputParser.nextToken();
		int val = Integer.parseInt(n);
		return val;
	}
	
	String NextString()
	{
		String n = inputParser.nextToken();
		return n;
	}
	
	void closeInput()
	{
		try {
			is.close();
		} catch (IOException e) {
			System.err.println("Unexpected IO ERROR: " + e);
		}
			
	}
	
	public static void main(String [] argv)
	{
		right b = new right(argv[0]);
	}
	
	public right(String inputFile)
	{
		openInput(inputFile);
		readNextLine();

		int TC = NextInt();
		
		for(int t=0; t<TC; t++)
		{	
			int ret=0;
			readNextLine();
			int P=NextInt();
			int N=1<<P;
			int [] M = new int [N];
			readNextLine();
			for(int i=0; i<N; i++)
			{
				M[i]=NextInt();
			}
			int matches=N/2;
			boolean [] teams= new boolean[N];
			for(int i=0; i<N; i++)
				teams[i]=true;
			for(int i=0; i<P; i++)
			{
				
				readNextLine();
				int []p = new int [matches];
				for(int j=0; j<matches; j++)
					p[j]=NextInt();
				
				int team=0;
				
				for(int m=0; m<matches; m++)
				{
					while(!teams[team])team++;
					int team1=team;
					team++;
					while(!teams[team])team++;
					int team2=team;
					team++;
					if(M[team1]>0&&M[team2]>0)
					{
						M[team1]--;
						M[team2]--;
					}
					else ret+=p[m];
					
					if(M[team1]<M[team2])
					{
						teams[team2]=false;
					}
					else teams[team1]=false;
					
				}
				
				matches/=2;
			}
			System.out.println("Case #"+(t+1)+": "+ret);
		}
		closeInput();
	}
	
}

