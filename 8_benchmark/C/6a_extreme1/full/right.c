#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
#include <math.h>

// --------------- Standard Functions ---------------

void shout(int i);																				//Prints an integer on screen
void show(int *array, int count);																//Prints an array on screen
int* read_int_array(FILE *fp, int *array_size);													//Reads array from file, allocates memory and returns array pointer
float* read_float_array(FILE *fp, int *array_size);											//Reads array from file, allocates memory and returns array pointer
void reverse(int *array, int count);
int compare_int(const void * a, const void * b);
int compare_double(const void * a, const void * b);

char *pack_char_array(char *array, int array_size, char empty_char, int *out_size);
int *pack_int_array(int *array, int array_size, int empty_int, int *out_size);
int sum_product(int *array1, int *array2, int count);
float* calc_mults(float *prob_array, int array_size);
void show_float(float *array, int count);

int main(int argc, char *argv[])
{
	FILE *fin, *fout;
	char line[200];
	int *input_count, x, i, j, k;
	int *input_array, *d;
	int no_of_pairs, array_size = 0;
	float *prob_array;
	int prob_num;
	float *probs, opt;
	int len_typed, len_pass;

	float *exp_counts;

	switch (1) //0 - Sample; 1 - Small Input; 2- Large Input
	{
	case 0:
		fin = fopen("../A-sample.in", "r");
		fout = fopen("../A-sample.out", "w");
		break;
	case 1:
		fin = fopen("../A-small-attempt0.in", "r");
		fout = fopen("../A-small.out", "w");
		break;
	case 2:
		fin = fopen("../A-large.in", "r");
		fout = fopen("../A-large.out", "w");
		break;
	}

	if (fin != -1)
		printf("\nFile opened successfully\n\n");
	else
		printf("\nFile could not be opened !!!!! \n\n");

	input_count = read_int_array(fin, &array_size);
	printf("Detected %d inputs\n", *input_count);


	for (x = 0; x < *input_count; x++)
	{
		fprintf(fout, "Case #%d: ", x + 1);

		printf("Reading %d of %d inputs\n", x, *input_count);
		input_array = read_int_array(fin, &array_size);
		len_typed = *input_array;
		len_pass = *(input_array + 1);
		printf("\nRead an array of %d elements : ", array_size);

		prob_array = read_float_array(fin, &prob_num);
		show_float(prob_array, prob_num);
		printf("\nRead a probability array of %d elements : \n", prob_num);

		probs = calc_mults(prob_array, prob_num);

		show_float(probs, len_typed);

		exp_counts = (float*)calloc(len_typed + 2, sizeof(float));

		*(exp_counts) = (*probs)*(1 + len_pass - len_typed) + (1 - ((*probs)))*(2 + 2 * len_pass - len_typed);
		*(exp_counts + len_typed) = len_typed + len_pass + 1;
		*(exp_counts + len_typed + 1) = 2 + len_pass;

		for (i = 1; i < len_typed; i++)
		{
			*(exp_counts + i) += 2 * i + (len_pass - len_typed) + 1;
			*(exp_counts + i) += (1.0 - (*(probs + i)))*(len_pass + 1);
		}

		opt = *(exp_counts);
		for (i = 0; i <= (len_typed + 1); i++)
			opt = (opt > *(exp_counts + i)) ? (*(exp_counts + i)) : opt;


		show_float(exp_counts, len_typed + 2);
		//	no_of_pairs = count_pairs(*input_array, *(input_array+1));
		fprintf(fout, "%.6f", opt);
		fprintf(fout, "\n");
	}


	fclose(fin);
	fclose(fout);

	return 0;

}

int compare_int(const void * a, const void * b)
{
	const int *compare1 = (const int *)a;
	const int *compare2 = (const int *)b;
	return (*compare1 > *compare2) - (*compare1 < *compare2);
}

int compare_double(const void * a, const void * b)
{
	const double *compare1 = (const double *)a;
	const double *compare2 = (const double *)b;
	return (*compare1 > *compare2) - (*compare1 < *compare2);
}

void shout(int i)
{
	printf("Reached line #%d\n", i);
}

void show(int *array, int count)
{
	int x;
	for (x = 0; x < count; x++)
	{
		printf("%d ", *(array + x));
	}
	printf("\n");

}

void show_float(float *array, int count)
{
	int x;
	for (x = 0; x < count; x++)
	{
		printf("%f ", *(array + x));
	}
	printf("\n");
}

int* read_int_array(FILE *fp, int *array_size) //Reads array from file, allocates memory and returns array pointer
{
	char line[10000];
	char* buffer = (char*)calloc(20, 1);
	int	temp[10000];
	int *array, i, j, x, length;

	fgets(line, 10000, fp);
	// printf("Read Line \"%s\" \n",line);
	length = strlen(line);

	x = 0;
	j = 0;
	for (i = 0; i < length; i++)
	{

		if (!isdigit(line[i]))
		{
			if (j != 0)
			{
				// printf("Converting %s to a number\n",buffer);
				temp[x++] = atoi(buffer);
				strcpy(buffer, "                    ");
				// printf("Buffer = %s",buffer);
				j = 0;
			}
		}
		else
		{

			// printf("%c in %d",line[i],j);
			*(buffer + j++) = line[i];
		}
	}

	array = calloc(x, sizeof(int));

	for (i = 0; i < x; i++)
	{
		array[i] = temp[i];
		// printf("Read %d\n",temp[i]);
	}
	*array_size = x;

	return array;
}


float* read_float_array(FILE *fp, int *array_size) //Reads array from file, allocates memory and returns array pofloater
{
	char line[10000];
	char* buffer = (char*)calloc(20, 1);
	float	temp[10000];
	float *array;
	int i, j, x, length;


	fgets(line, 10000, fp);
	// prfloatf("Read Line \"%s\" \n",line);
	length = strlen(line);

	x = 0;
	j = 0;
	for (i = 0; i < length; i++)
	{

		if ((!isdigit(line[i])) && (line[i] != '.'))
		{
			if (j != 0)
			{
				// prfloatf("Converting %s to a number\n",buffer);
				sscanf(buffer, "%f", &temp[x++]);
				strcpy(buffer, "                    ");
				// prfloatf("Buffer = %s",buffer);
				j = 0;
			}
		}
		else
		{

			// prfloatf("%c in %d",line[i],j);
			*(buffer + j++) = line[i];
		}
	}

	array = calloc(x, sizeof(float));

	for (i = 0; i < x; i++)
	{
		array[i] = temp[i];
		// prfloatf("Read %d\n",temp[i]);
	}
	*array_size = x;

	return array;
}


void reverse(int *array, int count)
{
	int i, temp;

	for (i = 0; i < count / 2; i++)
	{
		temp = *(array + i);
		*(array + i) = *(array + count - i - 1);
		*(array + count - i - 1) = temp;
	}
}

int sum_product(int *array1, int *array2, int count)
{
	int x;
	int sum = 0;
	for (x = 0; x < count; x++)
	{
		sum += (*(array1 + x))*(*(array2 + x));
	}
	return sum;
}

int sum(int *array, int count)
{
	int x;
	int sum = 0;
	for (x = 0; x < count; x++)
	{
		sum += (*(array + x));
	}
	return sum;
}

char *pack_char_array(char *array, int array_size, char empty_char, int *out_size)
{
	int x;
	char *output_array = (char *)calloc(array_size, sizeof(char));
	*out_size = 0;
	for (x = 0; x < array_size; x++)
	{
		if ((*(array + x)) != empty_char)
			*(output_array + ((*out_size)++)) = *(array + x);
	}
	return output_array;
}

int *pack_int_array(int *array, int array_size, int empty_int, int *out_size)
{
	int x, y = 0;
	int *output_array = (int *)calloc(array_size, sizeof(int));
	*out_size = 0;
	for (x = 0; x < array_size; x++)
	{
		if ((*(array + x)) != empty_int)
			*(output_array + ((*out_size)++)) = *(array + x);
	}
	return output_array;
}



float* calc_mults(float *prob_array, int array_size)
{
	int x, y;
	float *output_array = calloc((array_size + 5), sizeof(float));
	for (x = 0; x < array_size; x++)
	{
		*(output_array + x) = 1;
		for (y = 0; y < (array_size - x); y++)
			(*(output_array + x)) *= (*(prob_array + y));
	}
	*(output_array + array_size) = (1.0 - (*prob_array));

	show_float(output_array, array_size + 1);
	return output_array;
}
