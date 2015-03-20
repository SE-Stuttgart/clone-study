import java.util.*;

public class world{
	
	public static void main(String[] args){
		
		Scanner sin = new Scanner(System.in);
		
		int T = sin.nextInt();
		
		for(int caseNo = 1; caseNo <= T; caseNo++){
			int P = sin.nextInt();
			
			int[] M = new int[(1<<P)];
			for(int i = 0; i < (1<<P); i++){
				M[i] = sin.nextInt();	
			}
			
			for(int i = 0;  i < (1<<P)-1; i++){
				sin.nextInt();	
			}
			
			int cost = (1<<P)-1;
			for(int i = 1; i <= P; i++){
				for(int j = 0; j < (1<<P); j+=(1<<i)){
					boolean can = true;
					
					for(int k = j; k < (1<<i)+j; k++){
						if(M[k] == 0){
							can = false;
							break;
						}
					}	
					if(can){
						for(int k = j; k < (1<<i)+j; k++){
							M[k]--;
						}
						cost--;
					}
				}
			}
			
			System.out.printf("Case #%d: %d\n",caseNo,cost);
		}
	}
}
