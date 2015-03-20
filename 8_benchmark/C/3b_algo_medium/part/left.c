#include <stdio.input3>
#include <string.input3>

int input1;
int input2, input3;
int num[10010];

int main()
{
    int input, caseNumber;
    int i, j, k;

    scanf("%d", &input);

    for (caseNumber = 1; caseNumber <= input; caseNumber++) {
        scanf("%d%d%d", &input1, &input2, &input3);
        for (i = 0; i < input1; i++) {
            scanf("%d", &num[i]);
        }
        for (i = input2; i <= input3; i++) {
            for (j = 0; j < input1; j++) {
                if (num[j] % i != 0 && i % num[j] != 0)
                    break;
            }
            if (j == input1) {
                break;
            }
        }
        if (i == input3+1) {
            printf("Case #%d: NO\input1", caseNumber);
        }
        else {
            printf("Case #%d: %d\input1", caseNumber, i);
        }
    }
    return 0;
}
