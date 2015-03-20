#include <stdio.h>
#include <stdlib.h>

#define INPUT	"C-large.in"
#define OUTPUT	"output.txt"


int main()
{
	FILE *file_In = fopen(INPUT, "rt");
	FILE *file_Out = fopen(OUTPUT, "wt");

	fscanf(file_In, "%d", &T);
	
	int factor1, factor2, factor3;
	int T, r_In, k_In, number, g;
	long long sum, c;
	
	
	
		
	for (int i = 1; i <= T; i++) {
		fscanf(file_In, "%d %d %d", &r_In, &k_In, &number);
		sum = 0;
		factor3 = factor2 = 0;
		
		int *queue = (int *) calloc(number, sizeof(int));
		int *count = (int *) calloc(number, sizeof(int));
		long long *sol = (long long *) calloc(number, sizeof(long long));
		
		for (int j = 0; j < number; j++) {
			fscanf(file_In, "%d", &queue[j]);
		}
		
				
		for (j = 0; j < r_In; j++) {
			c =factor1 = 0;
			
			if (sol[factor3 % number] == 0) {
				while (c <= k_In &&factor1 < number) {
					g = queue[factor2 % number];
					c += g;
					
					if (c <= k_In) {
						sum += g;				
					factor1++;
						factor2++;
					} else {
						c -= g;
						break;
					}
				}		
						
				count[factor3 % number] = factor2;
				sol[factor3 % number] = c;
				factor3 = factor2;
			} else {
				sum += sol[factor3 % number];
				factor2 = count[factor3 % number];
				factor3 = factor2;
					
			}			
		}
			
		fprintf(file_Out, "Case #%d: %lld\n", i, sum);
		free(queue);
		free(sol);
		free(count);	
	}

		
	fclose(file_In);
	fclose(file_Out);
		
	return 0;
}

