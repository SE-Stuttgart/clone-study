#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#define LENGTH_OF_BUFFER 8192

char buf[LENGTH_OF_BUFFER];
int notes[10000];

int main() {
	int T, caseNum, n_In, l_In, H, i, x, note, okay;

	scanf(" %d", &T);
	for (caseNum = 1; caseNum <= T; caseNum++) {
		scanf(" %d %d %d", &n_In, &l_In, &H);
		for (i = 0; i < n_In; i++)
			scanf(" %d", &notes[i]);

		for (x = l_In; x <= H; x++) {
			okay = 1;
			for (i = 0; i < n_In; i++) {
				note = notes[i];
				if (x%note == 0 || note%x == 0)
					continue;
				okay = 0;
				break;
			}
			if (okay)
				break;
		}

		printf("Case #%d: ", caseNum);
		if (x <= H)
			printf("%d", x);
		else
			printf("NO");
		printf("\n");
	}
}
