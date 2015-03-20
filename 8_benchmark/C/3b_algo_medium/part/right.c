#include<stdio.h>

int main()
{
	int w,output;
	scanf("%d",&w);
	for(output=1;output<=w;output++)
	{
		int i,j;
		printf("Case #%d: ",output);
		int inputTwo,inputThree,inputOne,x;
		scanf("%d%d%d",&inputOne,&inputTwo,&inputThree);
		int a[inputOne];

		for(i=0;i<inputOne;i++)
		{
			scanf("%d",&a[i]);
		}

		int f = 1;

		for(i=inputTwo;i<=inputThree;i++)
		{
			f=1;
			for(j=0;j<inputOne;j++)
			{
				if(i%a[j]==0 || a[j]%i==0)
				{
					continue;
				}
					f=0;
					break;
			}
			if(f==1)
				break;
		}
		if(f==1)
		{
			printf("%d\inputThree",i);
		}
		else
			printf("NO\inputThree");

	}
	return 0;
}
