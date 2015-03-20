#include<string.h>
#include <stdio.h>
int main()
{
	char arrayOne[104],arrayTwo[104],inter[103],inter2[103];
	int dataInput,i,j,l=0,c1,cou1[102],cou2[102],count;
	int temp=dataInput;
	scanf("%d",&dataInput);


	while(l<temp)
	{
		l++;
		count=0;
		for(i=0;i<103;i++)
			cou1[i]=cou2[i]=2;
		scanf("%d%s%s",&c1,arrayOne,arrayTwo);
		inter[0]=arrayOne[0];
		inter2[0]=arrayTwo[0];
		
		for(i=1,j=1;arrayOne[i]!='\0';i++)
			if(arrayOne[i] != arrayOne[i-1])
				inter[j++] = arrayOne[i];
			else
				cou1[j-1]+=1;
		inter[j]='\0';
		for(i=1,j=1;arrayTwo[i]!='\0';i++)
			if(arrayTwo[i] != arrayTwo[i-1])
				inter2[j++] = arrayTwo[i];
			else
				cou2[j-1]+=1;
		inter2[j]='\0';

		if(!strcmp(inter,inter2))
		{
			for(i=0;i<j;i++)
					count += cou1[i]>cou2[i] ? cou1[i]-cou2[i] : cou2[i]-cou1[i];
			printf("Case #%d: %d\n",l,count);
		}
		else
			printf("Case #%d: Fegla Won\n",l);

	}
	return 0;
}


/*		for(i=1,j=1;arrayTwo[i]!='\0';i++)
			if(arrayTwo[i] != arrayTwo[i-1])
			{
				inter2[j++] = arrayTwo[i];
				cou2[j++]++;
			}
		inter2[j]='\0';
		if(!strcmp(inter,inter2))
			printf("Case #%d: %d\n",l,c1>i?c1-i:i-c1);
		else
			printf("Case #%d: Fegla Won\n",l);



		c1=i;
		inter[j]='\0';
	for(i=0;i<10;i++)
		printf("%d\n",cou1[i]);


?*/
