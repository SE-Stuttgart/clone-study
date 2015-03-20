# include <stdio.h>
# include <string.h>

int n, L, H;
int list[10000];

int min( int a, int b){ return a < b ? a :b;}

main(){
  int for_count1, for_count2, cont, sol, cases, ncases;

  for( scanf("%d", &ncases), cases = 1; cases <= ncases; cases++){
     scanf("%d %d %d", &n, &L, &H);	

     for( for_count1 = 0; for_count1 < n; for_count1++){
		 scanf("%d", &list[ for_count1 ]);
	 }

     sol = (1<<22);
     for( for_count1 = L; for_count1 <= H; for_count1++){
        cont = 0;
        for( for_count2 = 0; for_count2 < n; for_count2++)	{
           if( (for_count1 % list[for_count2]) == 0 ||( list[for_count2] %  for_count1 ) == 0 ){
			   cont++;
		   }
        }
        if( cont == n ){
        	sol = min( sol, for_count1 );
        }
     }
     printf("Case #%d: ", cases);
     if( sol == (1<<22)){
		 printf("NO\n");
	 } else {
		 printf("%d\n", sol);
	 }
  }
  return 0;	
}