
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int t_In, n_In;
char vocalArray[110];

int isLegal(int st, int ed)
{
    
    int i, maxLen = 0;
    for (i=st; i<=ed; i++) {
        if (vocalArray[i] == 'a' || vocalArray[i] == 'e' || vocalArray[i] == 'i' || vocalArray[i] == 'o' || vocalArray[i] == 'u') {
            maxLen = 0;
        } else {
            maxLen++;
        }
        if (maxLen >= n_In) {
            /* printf("\n"); */
            /* printf("%d %d\n", st, ed); */
            return 1;
        }
    }
    /* printf("\n"); */
    return 0;
}
    

int cal()
{
    int i, j, count = 0;
    for (i=0; i<=strlen(vocalArray)-n_In; i++) {
        for (j=n_In; i+j<=strlen(vocalArray); j++) {
            if (isLegal(i, i+j-1) == 1) {
                count++;
            }
        }
    }
    return count;
}

int main()
{
    freopen("A-small-attempt0.in", "r", stdin);
    int t, ret;
    scanf("%d", &t_In);
    for (t=0; t<t_In; t++) {
        scanf("%s %d", vocalArray, &n_In);
        ret = cal();
        printf("Case #%d: %d\n", t+1, ret);
    }
    return 0;
}

