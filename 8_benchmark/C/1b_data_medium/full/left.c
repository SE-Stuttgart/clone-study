#include<stdio.h>
#include<stdlib.h>

int main()
{
int xres=0;
long int n_In,i;
int T,pd,pg;
int all,arr[2000]={0};
scanf("%d",&T);all=T;

while (T!=0)
{
	scanf("%ld %d %d",&n_In,&pd,&pg);
	if((pg==100&&pd!=100) || (pg==0&&pd!=0))
	{
	arr[all-T]=1;
	}
	else
	{
		for (i=1;i<=n_In;i++)
			{
				if ((pd*i)%100==0){
					xres=1;break;
				}
			}
		if(xres==0){
			arr[all-T]=1;
		}
	}
	T--;
	xres=0;
}

for (i=0;i<all;i++)
	if(arr[i]!=0){
		printf ("Case #%ld is Broken\n",i+1);
	}
	else{
		printf ("Case #%ld is Possible\n",i+1);
	}
}
