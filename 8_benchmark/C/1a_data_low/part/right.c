#include <stdio.h>

#define N_max 150

int res[N_max][N_max];
char line[N_max];
double WP[N_max], OWP[N_max], OOWP[N_max], RPI[N_max];

int main(){

    int T,T_index;
    int index;
    int n_In;
    double win[N_max],lose[N_max],sum;
    int time;

    scanf("%d",&T);

    for (T_index=1;T_index<=T;T_index++){
        printf("Case #%d: ",T_index);
        printf("\n");        
        scanf("%d\n",&n_In);
        for (i=0;i<n_In;i++){
            scanf("%s",line);
//printf("line is [%s]\n");            
            for (int j=0;j<n_In;j++){
                if (line[j] == '1'){
                    res[i][j] = 1;}
				else if (line[j] == '0'){
                    res[i][j] = -1;}
				else if (line[j] == '.'){
                    res[i][j] = 0;}
				else{
                    printf("error\n"); return 0 ;
                }
            }
        }
/*        for (i=0;i<n_In;i++){
            for (j=0;j<n_In;j++){
                printf("%2d ",res[i][j]);
            }
            printf("\n");
        }                
        printf("\n");
*/        
        for (int i=0;i<n_In;i++){
            win[i] = lose[i] = 0;
            for (j=0;j<n_In;j++){
                if (res[i][j] == 1){
                    win[i]++;}
				else if (res[i][j] == -1){
                    lose[i]++;
                }
//                printf("(%d,%d) , win: %lf, lose: %lf\n",i,j,win,lose);
            }            
            WP[i] = win[i]/(win[i]+lose[i]);            
        }

/*        printf("WP:\n");
        for (i=0;i<n_In;i++){
            printf("%2lf ",WP[i]);
        }                
        printf("\n");
*/

        for (i=0;i<n_In;i++){
            time = 0;
            sum = 0;
            for (j=0;j<n_In;j++){
//printf("win[%d] is %lf, lose[%d] is %lf\n",j,win[j],j,lose[j]);                
                if (res[i][j] == 1 ){
//                    sum+=WP[j];
                    sum+= (win[j]/(win[j]+lose[j]-1) );
                    time++;
//printf("new WP[%d] is %lf\n",j, (win[j]/(win[j]+lose[j]-1) ) );                   
                }
                else if (res[i][j] == -1){
//                    sum+=WP[j];
                    sum+= ((win[j]-1)/(win[j]+lose[j]-1) );
//printf("new WP[%d] is %lf\n",j, ((win[j]-1)/(win[j]+lose[j]-1) ) );                   

                    time++;
                }

            }            
            OWP[i] = sum/time;
        }

/*        printf("OWP:\n");
        for (i=0;i<n_In;i++){
            printf("%2lf ",OWP[i]);
        }                
        printf("\n");
*/


        for (i=0;i<n_In;i++){
            time = 0;
            sum = 0;
            for (j=0;j<n_In;j++){
                if (res[i][j] == 1 || res[i][j] == -1){
                    sum+=OWP[j];
                    time++;}
            }            
            OOWP[i] = sum/time;
        }

        for (i=0;i<n_In;i++){
            RPI[i] = 0.25*WP[i] + 0.5 * OWP[i] + 0.25*OOWP[i];
            printf("%lf\n",RPI[i]);
        }
        
    }
}
