#include<stdio.h>
#include<string.h>

int absolutValue(int a)
{
	if(a<0)
		return (a*-1);
	return a;
}

int main()
{
	int i,j,k,test_case,count,count1,count2,t,temp,flag1[102],flag2[102],lenghtOfA,lenghtOfB;
	char a[102],b[102];
	scanf("%d",&test_case);
	for(t=0;t<test_case;t++)
	{
		count=0;

		scanf("%d",&temp);
		scanf("%s",a);
		scanf("%s",b);

		lenghtOfA = strlen(a);
		lenghtOfB = strlen(b);
		for(i=0;i<101;i++)
		{
			flag1[i]=0;
			flag2[i]=0;
		}
		count1 = 0;
		for(i=0;i<lenghtOfA;i++)
		{
			a[count1]=a[i];
			flag1[count1]=1;
			while(i<lenghtOfA-1 && (a[i]==a[i+1]))
			{
				flag1[count1]++;
				i++;	
			}
			count1++;
		}
		count2 = 0;
		for(i=0;i<lenghtOfB;i++)
		{
			b[count2]=b[i];
			flag2[count2]=1;
			while(i<lenghtOfB-1 && (b[i]==b[i+1]))
			{
				flag2[count2]++;
				i++;	
			}
			count2++;
		}
		if(count1!=count2)
		{
			printf("Case #%d: Fegla Won\n",t+1);	
		}
		else
		{
			for(i=0;i<count1;i++)
			{
				if(a[i]!=b[i])
					break;
				count = count + absolutValue(flag1[i]-flag2[i]);	
			}
			if(i==count1)
				printf("Case #%d: %d\n",t+1,count);
			else
				printf("Case #%d: Fegla Won\n",t+1);
			
		}
	}
	return 0;
}
