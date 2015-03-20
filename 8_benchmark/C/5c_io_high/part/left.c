

#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <math.h>


int main(int argc, char **argv) {

	FILE *fileOpen;
	FILE *fileOutput;
	int points;
	int matrix[1000][2];
	int flag = 0;
	int x, numElms, i, n_pisos, k = 0;
	int valor = 2;


	if (!(fileOpen = fopen("input.txt", "r")))
	{
		printf("Error at file input\n");
		return 0;
	}	if (!(fileOutput = fopen("output.txt", "w")))
	{
		printf("Error at file output\n");
		return 0;
	}

	fscanf(fileOpen, "%d \n", &numElms);
	for (i = 0; i < numElms; i++) {
		points = 0;
		fscanf(fileOpen, "%d ", &n_pisos);
		for (x = 0; x < n_pisos; x++) {
			fscanf(fileOpen, "%d ", &matrix[x][0]);
			fscanf(fileOpen, "%d ", &matrix[x][1]);
		}
		for (x = 0; x < n_pisos; x++) {
			for (k = x + 1; k<n_pisos; k++) {
				if (k != x) {
					if ((matrix[x][0] < matrix[k][0]) && (matrix[x][1] >matrix[k][1]))
						points++;
					else if ((matrix[x][0] > matrix[k][0]) && (matrix[x][1] < matrix[k][1]))
						points++;
				}
			}
		}


		fprintf(fileOutput, "Case #%d: %d \n", i + 1, points);
	}
	fclose(fileOpen);
	fclose(fileOutput);
	return 0;
}

