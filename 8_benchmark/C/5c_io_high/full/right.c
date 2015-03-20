#include<stdio.h>

int main()
{
   FILE *fin,*fout;
   fin=fopen("in.txt", "r");
   fout=fopen("out.txt", "w");
                
	short i,t,n,s,p,y,pi;
	fscanf(fin,"%hd",&t);
	
	for(i=1;i<=t;i++)
	{       
		y=0;
		fscanf(fin,"%hd%hd%hd",&n,&s,&p);
		    
		while(n--)
		{   
			fscanf(fin,"%hd",&pi);
			
			if(pi%3==0)
			{
				if(pi/3>=p) y++;
				else if(s>0 && (pi/3)+1>=p && pi>=3) {
					s--;
					y++;
				}
				continue;
			}
			
			if(pi%3==1)
			{
				if( (pi/3)+1 >= p && pi>=4 ) y++;
				continue;
			}
			
			if(pi%3==2)
			{
				if((pi/3)+1>=p) y++;
				else if(s>0 && (pi/3)+2>=p ) {
					s--;
					y++;
				}
				continue;
			}
			
			
			
		}
		
		fprintf(fout,"Case #%hd: %hd\n",i,y);
			
   }
	
	return 1;	
}
