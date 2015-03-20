#include <stdio.h>
#include <stdlib.h>

int main() {
	int t_In, t;
	scanf("%d\n", &t_In);
	for (t = 1; t <= t_In; t++) {
		int n_In, i, C, j;
		int A[10000][2];
		
		scanf("%d", &n_In);
		for (i = 0; i < n_In; i++){
			scanf("%d %d", &A[i][0], &A[i][1]);
		}
		
		C = 0;
		for (i = 0; i < n_In - 1; i++) {
			for (j = i+1; j < n_In; j++) {
				if ((A[i][0] < A[j][0] &&
					A[i][1] > A[j][1]) ||
					(A[i][0] > A[j][0] &&
					A[i][1] < A[j][1]))
				{
					C++;
				}
			}
		}
		
		printf("Case #%d: %d\n", t, C);
	}
	
	return 0;
}
