#include <stdio.h>

#define SIZE 2000 

int frequency[SIZE];

int init()
{
	int i = 0;

	for (i = 0; i < SIZE; i++)
	{
		frequency[i] = 0;
	}
}

int compare(void *compare1, void *compare2)
{
	int elem1 = *(int*)compare1;
	int elem2 = *(int*)compare2;

	return elem2 - elem1;
}

int calculate_minimum_key_presses(int max_keys, int num_keys, int num_alphabets)
{
	int output = 0;
	int i = 0;

	for (i = 0; i < num_alphabets; i++)
	{
		output += (1 + (i / num_keys)) * frequency[i];
	}
	return output;
}


int main()
{
	int num_cases = 0, i = 0;

	scanf("%d", &num_cases);
	for (i = 0; i < num_cases; i++)
	{
		int max_keys = 0, num_keys = 0, num_alphabets = 0, j = 0, output = 0;

		init();

		scanf("%d %d %d", &max_keys, &num_keys, &num_alphabets);
		for (j = 0; j < num_alphabets; j++)
		{
			scanf("%d", &frequency[j]);
		}

		if (max_keys * num_keys < num_alphabets)
		{

			printf("Case #%d: Impossible\n", i + 1);
		}
		else
		{
			qsort(frequency, num_alphabets, sizeof(int), compare);
			output = calculate_minimum_key_presses(max_keys, num_keys, num_alphabets);
			printf("Case #%d: %d\n", i + 1, output);
		}
	}

	return 0;
}
