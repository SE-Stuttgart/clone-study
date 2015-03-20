#include <stdio.h>

main()
{
	int t_In,userInput1,userInput2;
	int caseNumber,j,k;
	double doubleArray[1000000],minnum,typenum,rate;

	scanf("%d",&t_In);

	for(caseNumber=1;caseNumber<=t_In;caseNumber++)
	{
		scanf("%d %d",&userInput1,&userInput2);
		rate=1;
		minnum=userInput2+2;

		for(j=1;j<=userInput1;j++)
		{
			scanf("%lf",doubleArray+j-1);

			rate*=doubleArray[j-1];
			typenum=rate*((userInput1-j)+userInput2-j+1)+(1-rate)*((userInput1-j)+userInput2-j+1+userInput2+1);

			if(typenum < minnum)
			{
				minnum = typenum;
			}
		}
		printf("Case #%d: %lf\n",caseNumber,minnum);
	}
}







