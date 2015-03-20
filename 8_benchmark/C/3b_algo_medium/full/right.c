#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int main()
{
	int t_In,n,len,cnt,flag,cas,i,j,k,l;
	long long score;
	FILE *fileInput,*fileOutput;
	char vocalArray[110];

	fileInput = fopen("input.in","r");
	fileOutput = fopen("out.txt","w");

	fscanf(fileInput,"%d",&t_In);
	cas = 1;
	while(t_In--)
	{
		fscanf(fileInput,"%s%d",vocalArray,&n);
		len = strlen(vocalArray);
		score = 0;
		for(i=n;i<=len;i++)
		{
			for(j=0;j<=(len-i);j++)
			{
				cnt = flag = 0;
				for(l=0,k=j;l<i;l++,k++)
				{
					if((vocalArray[k] == 'a')||(vocalArray[k] == 'e')||(vocalArray[k] == 'i')||(vocalArray[k] == 'o')||(vocalArray[k] == 'u'))
						cnt = 0;
					else
						cnt++;
					if(cnt >= n&&flag == 0)
					{
						score++;
						flag = 1;
					}
				}
			}
		}
		fprintf(fileOutput,"Case #%d: %lld\n",cas++,score);
	}
	return 0;
}