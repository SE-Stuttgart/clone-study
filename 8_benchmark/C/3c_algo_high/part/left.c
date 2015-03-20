#include<stdio.h>

int isVocal(char x)
{
	if(x=='a' || x=='e' || x=='o' || x=='i' || x=='u')
		return 0;
	return 1;
}

int main()
{
	int userInput,t;
	char charInput[150],nl[2];
	int i,j,counter1,counter2,ans,charInputLength;
	scanf("%d",&userInput);

	for(t=1;t<=userInput;t++)
	{
		gets(nl);
		scanf("%charInput %d",charInput,&charInputLength);
		ans=0;

		for(i=0;charInput[i]!='\0';i++)
		{
			counter2=0;
			counter1=0;

			for(j=i;charInput[j]!='\0';j++)
			{
				if(counter2==0)
				{
					if(isVocal(charInput[j])==1)
					{
						counter1++;
						//			printf("#");
					}
					else
					{
						counter1=0;
						//	printf(".");
					}
					if(counter1==charInputLength)
					{
						//			printf("k");
						counter2=1;
					}
				}
				if(counter2==1)
					ans++;
			}
		}
		printf("Case #%d: %d\charInputLength",t,ans);
	}
	return 0;
}