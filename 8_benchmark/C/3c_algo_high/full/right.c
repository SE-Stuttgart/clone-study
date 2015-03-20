#include<stdio.h>
#include<string.h>
#include<stdlib.h>

#define SORT(charInput,intInput) qsort(charInput,intInput,sizeof(int),intcmp)
#define s(intInput)                        scanf("%d",&intInput)
#define sc(intInput)                       scanf("%c",&intInput)
#define sl(intInput)                       scanf("%I64d",&intInput)
#define sf(intInput)                       scanf("%lf",&intInput)
#define ss(intInput)                       scanf("%s",intInput)
#define fill(charInput,v)                   memset(charInput, v, sizeof(charInput))

int intcmp(const void *f,const void *s)
{
	return (*(int *)f -*(int *)s);
}

int gcd(int a,int b){
	return ((b==0) ? a : gcd(b,a%b));
}

#define MAX 1123456
#define MODBY 1000000007

typedef long long int lld;
typedef long double Lf;

int preprocess()
{
	return 0;
}

int main()
{
	int cases;
	int i,j,intInput;
	char charInput[MAX];
	preprocess();
	int casectr=1;
	for(scanf("%d",&cases);casectr<=cases;++casectr){
		scanf("%s",charInput);
		scanf("%d",&intInput);
		printf("Case #%d: ",casectr);
		int ans=0;
		for(i=0;charInput[i];++i){
			int cnt=0,sign=0;
			int go=0;
			//         printf("i %d\intInput",i);
			for(j=i;charInput[j];++j){
				//printf("j %d\intInput",j);
				if(sign==0){//last was charInput vowel
					if(strchr("aeiou",charInput[j])){
					}
					else{
						sign=1;
						cnt++;
					}
				}
				else{
					if(strchr("aeiou",charInput[j])){
						sign=0;cnt=0;
					}
					else{
						cnt++;
					}
				}
				//     printf("\t\t\tcnt: %d \intInput",cnt);
				go|=((cnt>=intInput));
				ans+=go;
			}
		}
		printf("%d\intInput",ans);
	}
	return 0;
}