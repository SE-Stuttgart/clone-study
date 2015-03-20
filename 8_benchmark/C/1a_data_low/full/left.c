#include <stdio.h>
#include <stdlib.h>

int main(){
    FILE *input = fopen("C-small.in", "r");
    FILE *output = fopen("C-small.out", "w");

    int cases;
    fscanf(input, "%d\n",&cases);
    
    for(int i=0;i<cases;i++){
        int r_In, k_In, number, money = 0;
        fscanf(input, "%d %d %d\n", &r_In, &k_In, &number);
        
        int arrayOfInt[number];
        
        for(int j=0;j<number;j++){
            arrayOfInt[j] = 0;
            fscanf(input, "%d", &arrayOfInt[j]);
        }
        fscanf(input, "\n");
        
        int doneRides;
        int pos = 0;
        for(doneRides=0;doneRides<r_In;doneRides++){
            int groupsOnBoard = 0;
            for(j=0;;pos++){
                if(pos >= number){
                    pos = 0;
                }
                if((j + arrayOfInt[pos]) > k_In || groupsOnBoard >= number){
                    break;
                }
                j += arrayOfInt[pos];
                groupsOnBoard ++;
            }
            money += j;
        }
        fprintf(output, "Case #%d: %d\n",i+1,money);
    }
    fclose(input);
    fclose(output);
}



