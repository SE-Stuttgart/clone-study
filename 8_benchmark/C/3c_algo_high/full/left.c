#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int userInput;
int N;
char str[1000001];
long long len;

int check(int in,int fi){
	int i,c=0;
	for(i=in;i<fi;i++){
		if(c==N){return 1;}
		switch(str[i]){
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u': c=0;break;
			default: c++;break;
		}
	}
	if(c==N) return 1;
	return 0;
}

int main(void){
	int t,sol,i,j;
	
	scanf("%d",&userInput);

	for(t=1;t<=userInput;t++){
		sol=0;
		scanf("%s",str);
		scanf("%d",&N);
		len = strlen(str);

		for(i=0;i<len;i++){
			for(j=i+N;j<=len;j++){
				if(check(i,j)==1){
					sol++;
				}
			}
		}

		printf("Case #%d: %d\n",t,sol);
	}
	return 0;
}
