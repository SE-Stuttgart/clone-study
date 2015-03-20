#include<stdio.h>
#include<string.h>

int main()
{
	int t_In, n_In, match[100][100];
	int i, j, k, win, total;
	double rpi[100], owp[100], avg[100], wp[100][2];
	char c;
	
	scanf("%d",&t_In);
	
	for(i=0; i<t_In; i++)
	{
		scanf("%d",&n_In);
		for(j=0; j<n_In; j++)
		{
			rpi[j] = 0;
			scanf("\n");
			for(k=0; k<n_In-1; k++)
			{
				scanf("%c",&c);
				//printf("%c",c);
				if((int)c == 49)
				{
					match[j][k] = 1;
				}
				else if((int)c == 48)
				{
					match[j][k] = 0;
				}
				else if((int)c == 46)
				{
					match[j][k] = -1;
				}
			}
			scanf("%c",&c);
				//printf("%c",c);
				if((int)c == 49)
				{
					match[j][n_In-1] = 1;
				}
				else if((int)c == 48)
				{
					match[j][n_In-1] = 0;
				}
				else if((int)c == 46)
				{
					match[j][n_In-1] = -1;
				}
			wp[j][0]=0;
			wp[j][1]=0;
		}
		
		for(j=0; j<n_In; j++)
		{
			win=0;
			total = 0;
			for(k=0; k<n_In; k++)
			{
				//printf("%d\t",match[j][k]);
				if(match[j][k] == 1)
				{
					win++;
					total++;
				}
				else if(match[j][k] ==0)
				{
					total++;
				}
			}
			wp[j][0] = win;
			wp[j][1] = total;
			//printf("\n%d %d %d\n",j, win, total);
		}
		
		for(j=0; j<n_In; j++)
		{
			owp[j]=0;
			total=0;
			for(k=0; k<n_In; k++)
			{
				if(match[j][k] == 1)
				{
					total++;
					owp[j] = owp[j] + (wp[k][0]/(wp[k][1]-1));
				}
				else if(match[j][k] == 0)
				{
					total++;
					owp[j] = owp[j] + ((wp[k][0]-1)/(wp[k][1]-1));
				}
			}
			owp[j] = (owp[j]/total);
		}
		
		for(j=0; j<n_In; j++)
		{
			avg[j] = 0;
			total = 0;
			for(k=0; k<n_In; k++)
			{
				if(match[j][k] == 1 || match[j][k] == 0)
				{
					total++;
					avg[j] = avg[j] + owp[k];
				}
			}
			avg[j] = avg[j]/total;
		}
		
		printf("Case #%d:\n",i+1);
		for(j=0; j<n_In; j++)
		{
			rpi[j] = 0.25*(wp[j][0]/wp[j][1]) + 0.5*owp[j] + 0.25*avg[j];
			printf("%f\n",rpi[j]);
		}
			
	}
	return 0;
}



/*
Case #1:
0.5
0.5
0.5
Case #2:
0.645833333333
0.368055555556
0.604166666667
0.395833333333

*/
