#include <stdio.h>
#include <stdlib.h>

int main()
{
    freopen("A-large.in", "r", stdin);
    //freopen("A-large.out", "w", stdout);
    int sizeInput, casesInput;
    int i, j, k, t;
    scanf("%d", &casesInput);

    for(k = 1; k <= casesInput; k++)
    {
        scanf("%d", &sizeInput);
        getchar();
        char board[sizeInput][sizeInput];
        char c;

        for(i = 0; i < sizeInput; i++)
        {
            for(j = 0; j < sizeInput; j++)
            {
                board[i][j] = getchar();
            }
            getchar();
        }
        double RPI[sizeInput], WP[sizeInput], OWP[sizeInput], OOWP[sizeInput];
        //printf("\nWP\n");

        for(i = 0; i < sizeInput; i++)
        {
            double sum = 0;
            int n = 0;

            for(j = 0; j < sizeInput; j++)
            {
                switch(board[i][j])
                {
                case '.':
                    break;

                case '1':
                    n++;
                    sum++;
                    break;

                case '0':
                    n++;
                    break;

                default:
                    break;
                }
            }
            WP[i] = sum / n;
            //printf("%f ", WP[i]);
        }
        //printf("\nOWP\n");
        for(i = 0; i < sizeInput; i++)
        {
            double sum = 0, sumWP = 0;
            int n = 0, nWP = 0;
            for(t = 0; t < sizeInput; t++)
            {
                if(board[i][t] == '.' || t == i)
                    continue;
                for(j = 0; j < sizeInput; j++)
                {
                    if(j == i)
                        continue;

                    switch(board[t][j])
                    {
                    case '.':
                        break;

                    case '1':
                        n++;
                        sum++;
                        break;

                    case '0':
                        n++;
                        break;

                    default:
                        break;
                    }
                }
                sumWP += sum / n;
                nWP++;
                sum = 0;
                n = 0;
            }
            OWP[i] = sumWP / nWP;
            //printf("%f ", OWP[i]);
        }
        //printf("\nOOWP\n");
        for(i = 0; i < sizeInput; i++)
        {
            double sum = 0;
            int n = 0;

            for(j = 0; j < sizeInput; j++)
            {
                if(board[i][j] == '1' || board[i][j] == '0')
                {
                    n++;
                    sum += OWP[j];
                }
            }
            OOWP[i] = sum / n;
            //printf("%f ", OOWP[i]);
        }
        //printf("\n");
        printf("Case #%d:\n", k);

        for(i = 0; i < sizeInput; i++)
        {
            RPI[i] = 0.25 * WP[i] + 0.50 * OWP[i] + 0.25 * OOWP[i];
            printf("%.12f\n", RPI[i]);
        }
    }
    return 0;
}
