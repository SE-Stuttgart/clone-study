import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class right {

	public static void main(String[] args) throws IOException{
		Scanner k = new Scanner(System.in);
		
		int T = k.nextInt(); k.nextLine();
		
		String[] solution = new String[T];
		
		for (int a = 1; a <= T; a++)
		{
			int x = k.nextInt(); int y = k.nextInt();
			k.nextLine();
			
			String horizont, vertical;
			if (x > 0)
			{
				horizont = "";
				for (int b = 0; b < x; b++)
				{
					horizont += "WE";
				}
			}
			else if (x==0)
			{
				horizont = "";
			}
			else
			{
				horizont = "";
				for (int b = 0; b > x; b--)
				{
					horizont += "EW";
				}
			}
			
			if (y > 0)
			{
				vertical = "";
				for (int b = 0; b < y; b++)
				{
					vertical += "SN";
				}
			}
			else if (y==0)
			{
				vertical = "";
			}
			else
			{
				vertical = "";
				for (int b = 0; b > y; b--)
				{
					vertical += "NS";
				}
			}
			String total = horizont + vertical;
			String ans = "Case #" + a + ": " + total;
			solution[a-1] = ans;
		}
		printOutAnswers(solution);
	}
	public static void printOutAnswers(String[] solution) throws IOException
	{
		PrintWriter out = new PrintWriter(new FileWriter("/Users/carl"+
		"/Desktop/javaoutputs/B.txt"));
		
		for (int x = 0; x < solution.length; x++)
		{
			out.println(solution[x]);
			System.out.println(solution[x]);
		}
		
		out.close();
	}
}
