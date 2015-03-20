#include <stdio.h>

const int powers[10]={1,10,100,1000,10000,100000,1000000,10000000,100000000,1000000000};

static int next(int returnVal, int length)
{
	int lastbit=returnVal%10;
	returnVal/=10;
	if(lastbit==0){
		return returnVal;
	}
	returnVal+=powers[length-1]*lastbit;
	return returnVal;
}

static int count(int val, int a, int b)
{
	int i;
	int ans=0;
	int j=0;
	if(b<a) return 0;
	for(j=0,i=val; i; i/=10,j++);
	for(i=next(val,j); i!=-1 && i!=val; i=next(i,j))
	{
		if(i>val && i>=a && i<=b){
			ans++;
		}
	}
	return ans;
}

int main()
{
	int t_In;
	int cnt=0;
	for(scanf("%d", &t_In); t_In; t_In--,cnt++)
	{
		int userInput1,userInput2;
		int i;
		int ans=0;
		scanf("%d%d", &userInput1, &userInput2);
		for(i=userInput1; i<=userInput2; i++)
		{
			ans+=count(i, userInput1, userInput2);
		}
		printf("Case #%d: %d\n", cnt+1, ans);
	}
	return 0;
}