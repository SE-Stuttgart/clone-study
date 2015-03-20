

#include <iostream>
#include <cstdio>

using namespace std;

double e[100003],f[100003],g[100003];

int main(){
        int n,j;
        scanf("%d",&n);

        for(j=1;j<=n;j++){
                int a,b,i;
                double c,d,erg1,erg2,ans=99999999,erg3;
                scanf("\n%d %d",&a,&b);
                f[0]=1;
                e[0]=0;

                for(i=a;i>=1;i--){
                        scanf("%lf",&e[i]);
                        f[0]*=e[i];
                }

                g[0]=f[0];

                for(i=1;i<=a;i++){
                        f[i]=((f[i-1]/(1-e[i-1]))*(1-e[i]))/e[i];
                        g[i]=g[i-1]+f[i];
                }
                
                erg1=f[0]*(b-a+1)+(1-f[0])*(2*b-a+2);
                if(erg1<ans){ans=erg1;}
                erg2=b+2;
                if(erg2<ans){
					ans=erg2;
				}

                for(i=1;i<=a;i++){
                        erg3=(b-a+2*i+1)*(f[0]+g[i]-g[0])+(b-a+2*i+1+b+1)*(1-f[0]-g[i]+g[0]);

                        if(erg3<ans){
							ans=erg3;
						}
                }
                printf("Case #%d: %.6lf\n",j,ans);
        }
        return 0;
}
