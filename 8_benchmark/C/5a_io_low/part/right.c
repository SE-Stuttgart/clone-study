#include<stdio.h>

int main()
{
	FILE *fpout;
	fpout = fopen("outcome", "w");
	int userInput;
	int a = 1;
	scanf("%d", &userInput);

	while (a <= userInput)
	{
		fprintf(fpout, "Case #%d: ", a);
		int n, l, h;
		scanf("%d%d%d", &n, &l, &h);
		int arr[n], i, flag = 0, flag2 = 0;

		for (i = 0; i < n; i++)
		{
			scanf("%d", &arr[i]);
		}
		flag2 = 0;

		while (l <= h)
		{
			flag = 0;

			for (i = 0; i < n; i++)
			{
				if (arr[i] % l != 0 && l%arr[i] != 0)
				{
					flag = 1;
					break;
				}
			}

			if (flag == 0)
			{
				fprintf(fpout, "%d\n", l);
				flag2 = 1;
				break;
			}
			l++;
		}

		if (flag2 == 0)
		{
			fprintf(fpout, "NO\n");
		}
		a++;
	}
	return 0;
}
