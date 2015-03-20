#include<stdio.h>
int main()
{
    int values[31];
    int cases,n,s,p,ans,i,j=1;
    scanf("%d",&cases);

    while(cases--)
    {
        printf("Case #%d: ",j++);
        ans=0;
        scanf("%d%d%d",&n,&s,&p);

        for(i=0;i<n;i++)
        {
            scanf("%d",&values[i]);

            if(values[i]==0 && p==0)
            {
                ans++;
                continue;
            }
            else
				if (values[i] == 0) {
					continue;
				}

            int mo=values[i]%3;
            int sc=values[i]/3;

            if(sc>=p)
                ans++;
            else{
				if (mo == 1 && sc + 1 >= p) {
					ans++;
				}
				if (mo == 2 && sc + 1 >= p) {
					ans++;
				}
                else
					if (s > 0 && mo == 2 && sc + 2 >= p) {
						ans++, s--;
					}
				if (s > 0 && mo == 0 && sc + 1 >= p) {
					ans++, s--;
				}
            }
        }
        printf("%d\n",ans);

    }
}
