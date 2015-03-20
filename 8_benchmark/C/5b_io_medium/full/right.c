#include<stdio.h>

int main()
{
   FILE *fin,*fout;
   fin=fopen("in.txt", "r");
                
	short i,t,n,input2,input3,y,pi;
	fscanf(fin,"%hd",&t);
	
	for(i=1;i<=t;i++)
	{       
		y=0;
		fscanf(fin,"%hd%hd%hd",&n,&input2,&input3);
		    
		while(n--)
		{   
			fscanf(fin,"%hd",&pi);
			
			if(pi%3==0)
			{
				if (pi / 3 >= input3) {
					y++;
				}
				else if(input2>0 && (pi/3)+1>=input3 && pi>=3) {
					input2--;
					y++;
				}
				continue;
			}
			
			if(pi%3==1)
			{
				if ((pi / 3) + 1 >= input3 && pi >= 4) {
					y++;
				}
				continue;
			}
			
			if(pi%3==2)
			{
				if ((pi / 3) + 1 >= input3) {
					y++;
				}
				else if(input2>0 && (pi/3)+2>=input3 ) {
					input2--;
					y++;
				}
				continue;
			}			
		}
		
		printf("Case #%hd: %hd\n",i,y);
			
   }
	
	return 1;	
}
