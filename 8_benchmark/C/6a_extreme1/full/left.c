#include <stdio.h>

main()
{
	int number_of_cases, input1, input2;
	int i, j, k;
	double doubleArray[1000000], minnum, typenum, rate;

	scanf("%d", &number_of_cases);

	for (i = 1; i <= number_of_cases; i++)
	{
		scanf("%d %d", &input1, &input2);
		rate = 1;
		minnum = input2 + 2;
		for (j = 1; j <= input1; j++)
		{
			scanf("%lf", doubleArray + j - 1);

			rate *= doubleArray[j - 1];
			typenum = rate*((input1 - j) + input2 - j + 1) +
				(1 - rate)*((input1 - j) + input2 - j + 1 + input2 + 1);
			if (typenum < minnum)
			{
				minnum = typenum;
			}
		}
		printf("Case #%d: %lf\n", i, minnum);
	}
}







