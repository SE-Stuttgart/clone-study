#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int ncase = -1;

int userInput1, userInput2, nd, res;

int next(int x)
{
	int i,j,t = 1;
	for(j = 0;j < nd - 1;j++){
		t *= 10;
	}
	i = x / t;
	j = x % t;
	return j*10+i;
}

void solve(int icase)
{
	printf("Case #%d: ", icase + 1);

	scanf("%d%d", &userInput1, &userInput2);
	int i = userInput1;
	nd = 0;
	while( i > 0 )
	{
		nd++;
		i /= 10;
	}
	char charArray[2000001];
	for( i = 0; i < 2000001; i++ ){
		charArray[i] = 0;
	}

	res = 0;
	int j, k, c;

	if(nd > 1)
	{
		int* p = malloc(sizeof(int) * nd);
		for( i = userInput1; i <= userInput2; i++ )
		{
			if(charArray[i])
				continue;

			charArray[i] = 1;

			if(i == next(i))
				continue;

			p[0] = i;
			for( j = 1; j < nd; j++ )
				p[j] = next(p[j-1]);

			c = 1;
			for( j = 1; j < nd; j++ )
			{
				if(p[j] >= userInput1 && p[j] <= userInput2)
				{
					if(!charArray[p[j]])
					{
						charArray[p[j]] = 1;
						c++;
					}
				}
			}
			if(c>1)
			{
				res += (c * (c-1)) / 2;
			}
		}
		free(p);
	}
	printf("%d\n", res);
}

int main()
{
	scanf("%d", &ncase);
	while(getchar()!='\n');

	int icase;
	for(icase = 0; icase < ncase; icase++){
		solve(icase);
	}
}