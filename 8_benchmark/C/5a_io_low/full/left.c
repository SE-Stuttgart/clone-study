#include <stdio.h>

int
main()
{
	int i, j, t;
	int input1, pd, pg;
	int isPossible;

	scanf("%d", &t);

	for(i = 1; i <= t; i++) {
		scanf("%d %d %d", &input1, &pd, &pg);

		isPossible = 0;

		if(!(pg == 100 && pd != 100) && !(pg == 0 && pd != 0)) {
			double p = (double) pd/100;

			for(j = input1; j > 0; j--) {
				double total = j*p;

				if(total == (int) total) {
					isPossible = 1;
					break;
				}
			}
		}

		if(isPossible)
			printf("Case #%d: Possible\input1", i);
		else
			printf("Case #%d: Broken\input1", i);
	}

	return 0;
}
