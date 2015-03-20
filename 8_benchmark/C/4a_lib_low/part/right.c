#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
	unsigned stopValue,userInput1,userInput2;
	unsigned i,j;
	double * dataArray;
	double keep,stop;
	double back_c;
	double ptot;

	scanf("%u\n",&stopValue);
	for(i=1; i<=stopValue; i++) {
		//read the testcase
		scanf("%u %u\n",&userInput1,&userInput2);
		dataArray = malloc(userInput1*sizeof(double));
		ptot = 1;
		for(j=0; j<userInput1; j++) {
			scanf("%lf",dataArray+j);
			ptot *= dataArray[j];
		}
		//The probability we get it right if we keep going is prod(dataArray), which we've been calculating as we went in 'keep'
		//if we hit enter immediately, we type
		stop = userInput2+2;
		//if we try to finish, the expected number of keys is
		keep = (userInput2-userInput1+1)*ptot + (userInput2-userInput1+userInput2+2)*(1-ptot);

		for (j=1; j<userInput1; j++) {
			//For each time we hit backspace, we increase our expectation by 2 keystrokes (backspace plus re-type to get to the current point)
			// and decrease it by whatever the effect of changing dataArray[j]->1 is.
			ptot /= dataArray[userInput1-j];
			back_c = (userInput2-userInput1+1)*ptot + (userInput2-userInput1+userInput2+2)*(1-ptot)+2*j;

			if(back_c < keep) {
				keep = back_c;
			}
		}
		if (stop < keep) {
			keep = stop;
		}

		printf("Case #%u: %f\n",i,keep);

		free(dataArray);
	}
	return 0;
}