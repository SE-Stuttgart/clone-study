#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <math.h>

typedef long double ld;

#define f(i,n) for(i=0;i<n;i++)
#define fa(i,a,n) for(i=a;i<n;i++)
#define mi(p,n) p=(int *)malloc(n*(sizeof(int)))
#define mi2(p,n) p=(int **)malloc(n*(sizeof(int*)))
#define mc(p,n) p=(char *)malloc(n*(sizeof(char)))
#define mc2(p,n) p=(char **)malloc(n*(sizeof(char *)))
#define mld(p,n) p=(ld *)malloc(n*(sizeof(ld)))
#define mld2(p,n) p=(ld **)malloc(n*(sizeof(ld *)))


int cmpIA (const void * a, const void * b){return ( *(int*)a - *(int*)b );} 
int cmpID (const void * a, const void * b){return ( *(int*)b - *(int*)a );}
int cmpCA (const void * a, const void * b){return ( *(char*)a - *(char*)b );} 
int cmpCD (const void * a, const void * b){return ( *(char*)b - *(char*)a );} 
int cmpLdA (const void * a, const void * b){if (*(ld*)a>*(ld*)b) return 1; else if (*(ld*)a<*(ld*)b) return -1; else return 0;} 
int cmpLdD (const void * a, const void * b){if (*(ld*)a>*(ld*)b) return -1; else if (*(ld*)a<*(ld*)b) return 1; else return 0;} 
int cmpSA (const void * a, const void * b){return strcmp(*(char**)a, *(char**)b);} 
int cmpSD (const void * a, const void * b){return -strcmp(*(char**)a, *(char**)b);} 


int maxF(int * F, int n)
{
	int max = -1;
	int indMax = -1;
	int i;

	f(i, n)
	{
		if(F[i]>max)
		{
			indMax = i;
			max = F[i];
		}
	}

	if (indMax != -1)
		F[indMax] = -1;

	return max;
}

int process(int * F, int input1, int input2, int input3)
{
	int res = 0;
	int * integerArray;
	int i;

	if (((ld)input3)/((ld)input2) > ((ld)input1))
		return -1;

	mi(integerArray, input2);

	f(i, input2)
		integerArray[i] = 0;

	f(i, input3)
	{
		integerArray[i%input2] += 1;
		res += (maxF(F, input3)*integerArray[i%input2]);
	}

	free(integerArray);

	return res;
}
 
int main(void)
{
	FILE * in;
	FILE * out;
	int nbCase, i, j, k, c, res;

	const ld PI = 4.0*atan(1.0);
	const ld eps = 1e-8;	

	int input1, input2, input3;
	int * F;

	in = fopen("in.txt", "r");
	out = fopen("out.txt", "w");	

	if (in) 
	{	
		fscanf(in, "%d\n", &nbCase);

		fa(c,1,(nbCase+1))
		{				
			fscanf(in, "%d %d %d\n", &input1, &input2, &input3);
			mi(F, input3);
			
			f(i, input3)
				fscanf(in, "%d ", &(F[i]));

			res = process(F, input1, input2, input3);
			
			if (res == -1)		
				fprintf(out, "Case #%d: Impossible\n", c);
			else
				fprintf(out, "Case #%d: %d\n", c, res);
			
			free(F);
			
		}		
	
		fclose(in);
		fclose(out);
	}
    return 0;
}
