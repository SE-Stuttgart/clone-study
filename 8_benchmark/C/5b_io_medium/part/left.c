#include <stdio.h>
#include <stdlib.h>

#define LIMIT 10

int main() {
    int score[LIMIT][LIMIT];
    int wons[LIMIT];
    int played[LIMIT];

    float results[LIMIT][3];
    int teams;

    int teamCounter = 0;

    float temp;
    int opponent;
    int k;

    int cases;

    char c;

    scanf("%d\n", &cases);

    for(int l=0; l < cases; l++) {
        scanf("%d\n", &teams);

        for(int i=0; i < teams; i++) {
            wons[i] = 0;
            played[i] = 0;
        }

        for(int line = 0; line < teams; line++) {
            for(int game = 0; game <= teams; game++) {
                scanf("%1c", &c);
                if (c=='\n') continue;

                score[line][game] = (c=='.') ? -1 : atoi(&c);
                wons[line] += (c=='1') ? 1 : 0;
                played[line] += (c!='.') ? 1 : 0;
               
            }
        }

        // printf("\n");

        // WP
        for(teamCounter = 0; teamCounter < teams; teamCounter++) {
            results[teamCounter][0] = (float) wons[teamCounter]/played[teamCounter]; // WP
            // printf("WP: %.15f\n", results[teamCounter][0]);
        }

        // OWP
        for(teamCounter = 0; teamCounter < teams; teamCounter++) {
            temp = 0;
            opponent = 0;
            k = 0;

            for(; opponent < teams; opponent++) {
                if (opponent == teamCounter)
					continue; 
                if (score[teamCounter][opponent] == -1)
					continue; 

                if (score[opponent][teamCounter] == 1) {
                    temp += (float) (wons[opponent]-1)/(played[opponent]-1);
                    
                } else {
                    temp += (float) (wons[opponent])/(played[opponent]-1);
                  
                }

                k++;

            }

            results[teamCounter][1] = (float) temp/k;
            // printf("OWP: %f\n", results[teamCounter][1]);

        }

        // OOWP
        for(teamCounter = 0; teamCounter < teams; teamCounter++) {
            temp = 0;
            opponent = 0;
            k = 0;

            for(; opponent < teams; opponent++) {
                if (score[opponent][teamCounter] != -1) {
                    temp += results[opponent][1];
                    k++;
                }
            }

            results[teamCounter][2] = (float) temp/k;
            // printf("OOWP: %f\n", results[teamCounter][2]);
        }


        printf("Case #%d:\n", l+1);
        for(teamCounter = 0; teamCounter < teams; teamCounter++) {
            temp = (0.25 * results[teamCounter][0]) + (0.5 * results[teamCounter][1]) + (0.25 * results[teamCounter][2]);
            printf("%.10f\n", temp);
        }
    }

    return 0;
}
