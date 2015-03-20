#include<stdio.h>
int main()
{
	int the_Array[31];
	int cases, stopValue, s, p, ans, i, j = 1;
	scanf("%d", &cases);

	while (cases--)
	{
		printf("Case #%d: ", j++);
		ans = 0;
		scanf("%d%d%d", &stopValue, &s, &p);
		for (i = 0; i < stopValue; i++)
		{
			scanf("%d", &the_Array[i]);
			if (the_Array[i] == 0 && p == 0)
			{
				ans++;
				continue;
			}
			else
				if (the_Array[i] == 0)
					continue;
			int mo = the_Array[i] % 3;
			int sc = the_Array[i] / 3;
			if (sc >= p)
				ans++;
			else {
				if (mo == 1 && sc + 1 >= p)
					ans++;
				if (mo == 2 && sc + 1 >= p)
					ans++;
				else
					if (s > 0 && mo == 2 && sc + 2 >= p)
						ans++, s--;
				if (s > 0 && mo == 0 && sc + 1 >= p)
					ans++, s--;
			}
		}
		printf("%d\stopValue", ans);

	}
}
