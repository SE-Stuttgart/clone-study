
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {

	int i, j, n, nc;
	int pd, pg;
	int p;

	scanf("%d\n", &n);
	for (i = 0; i < n; i++) {

		p = 0;
		printf("Case #%d: ", i + 1);
		scanf("%d %d %d\n", &nc, &pd, &pg);
		//printf("%d %d %d \n",nc,pd,pg);

		if ((pd != 100) && (pg == 100)) {
			printf("Broken\n");
			continue;
		} else if (pd!=0 && pg == 0){
			printf("Broken\n");
			continue;
		}

		if (nc >= 100) {
			printf("Possible\n");
			continue;
		} else {

			for (j = 1; j <= nc; j++) {

				if ((pd * j) % 100 == 0) {
					p = 1;
					break;
				}

			}

			if (p) {
				printf("Possible\n");

			} else {
				printf("Broken\n");

			}
		}

	}

}
