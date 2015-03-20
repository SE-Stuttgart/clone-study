#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<math.h>

int main(int argc, char* argv[])
{
	FILE* file;
	char line[10240], number[20];
	int case_nr, nr_cases;
	int input1, input2, input3, counter1, counter2, c, valid;
	long long freqs[1000];
	long long total;

	file = fopen(argv[1], "r");
	if(!file){
		exit(1);
	}

	fgets(line, 1024, file);
	nr_cases = atoi(line);

	for(case_nr=1; case_nr<=nr_cases; case_nr++)
	{
		fgets(line, 1024, file);
		//printf("%s\n", line);
		sscanf(line, "%d %d %d", &input1, &input2, &input3);
		//printf("%d %d %d\n", input1, input2, input3);
		fgets(line, 1024, file);
		//printf("%s\n", line);
		c = 0;

		for(counter1=0; counter1<strlen(line);)
		{
			counter2=0;
			memset(number, '\0', 20);

			while((line[counter1] != '\0') && (line[counter1] != ' '))
			{
				number[counter2] = line[counter1];
				counter2++;
				counter1++;
			}
			freqs[c++] = atoll(number);
			//printf("Number %s\n", number);
			counter1++;
		}
		//for(counter1=0; counter1<input3; counter1++)
		//    printf("Freq[%d]=%lld\n", counter1, freqs[counter1]);
		/* Sort */
		for(counter1=0; counter1<input3; counter1++)
		{
			for(counter2=0; counter2<input3-counter1-1; counter2++)
			{
				long long tmp;

				if(freqs[counter2+1] > freqs[counter2])
				{
					tmp = freqs[counter2+1];
					freqs[counter2+1] = freqs[counter2];
					freqs[counter2] = tmp;
				}
			}
		}
		//for(counter1=0; counter1<input3; counter1++)
		//    printf("Freq[%d]=%lld\n", counter1, freqs[counter1]);

		total = 0;
		counter2 = 1;
		c = input2;
		valid = 1;

		for(counter1=0; counter1<input3; counter1++)
		{
			if(counter2 > input1){
				valid = 0;
			}
			total += counter2 * freqs[counter1];
			c--;

			if(c == 0)
			{
				counter2++;
				c = input2;
			}
		}

		if(valid)
			printf("Case #%d: %lld\n", case_nr, total);
		else
			printf("Case #%d: impossible\n", case_nr);
	}

	return 0;
}