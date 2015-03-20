#include<stdio.h>

int main()
{
	int input1,caseNumber;
	scanf("%d",&input1);

	for(caseNumber=1;caseNumber<=input1;caseNumber++)
	{
		int i,j;
		printf("Case #%d: ",caseNumber);
		int m,n,arraySize,x;
		scanf("%d%d%d",&arraySize,&m,&n);
		int inputArray[arraySize];

		for(i=0;i<arraySize;i++)
		{
			scanf("%d",&inputArray[i]);
		}
		int f;

		for(i=m;i<=n;i++)
		{
			f=1;

			for (j = 0; j < arraySize; j++)
			{
				if (i%inputArray[j] == 0 || inputArray[j] % i == 0)
				{
					continue;
				}
				f = 0;
				break;
			}

			if(f==1)
				break;
		}

		if(f==1)
		{
			printf("%d\n",i);
		}
		else
			printf("NO\n");
	}
	return 0;
}
