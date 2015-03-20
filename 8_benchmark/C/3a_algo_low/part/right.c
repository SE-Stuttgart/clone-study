#include <stdio.h>
#include <string.h>

char charMatrix[110][110];
int win[110];
int lose[110];
double wp[110],owp[110],oowp[110];

int main()
{
	int t_In;
	scanf("%d",&t_In);
	for(int _=1;_<=t_In;_++)
	{
		memset(win,0,sizeof(win));
		memset(lose,0,sizeof(lose));
		memset(wp,0,sizeof(wp));
		memset(owp,0,sizeof(owp));
		memset(oowp,0,sizeof(oowp));

		int i,j,n;
		scanf("%d",&n);

		for(i=0;i<n;i++)
		{
			scanf("%s",charMatrix[i]);
			int k=0,cnt=0;
			for(j=0;j<n;j++)
			{
				if(charMatrix[i][j]=='0'){
					lose[i]++;
				}
				if(charMatrix[i][j]=='1'){
					win[i]++;
				}
			}
			wp[i]=(double)win[i]/(win[i]+lose[i]);
		}
		for(i=0;i<n;i++)
		{
			for(j=0;j<n;j++)
			{
				if(i==j)
					continue;
				if(charMatrix[i][j]=='0')
					owp[i]+=(double)(win[j]-1)/(win[j]+lose[j]-1);
				else if(charMatrix[i][j]=='1')
					owp[i]+=(double)win[j]/(win[j]+lose[j]-1);
			}
			owp[i]/=(win[i]+lose[i]);
		}
		for(i=0;i<n;i++)
		{
			int cnt=0;
			for(j=0;j<n;j++)
			{
				if(i==j)
					continue;
				if(charMatrix[i][j]!='.')
				{
					oowp[i]+=owp[j];
					cnt++;
				}
			}
			oowp[i]/=cnt;
		}
		printf("Case #%d:\n",_);
		for(i=0;i<n;i++)
		{
			printf("%0.12lf\n",0.25*wp[i]+0.5*owp[i]+0.25*oowp[i]);
		}
	}
	return 0;
}