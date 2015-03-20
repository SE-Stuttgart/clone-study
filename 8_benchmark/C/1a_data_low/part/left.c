#include <stdio.h>
#include <stdlib.h>

char array2Dim[102][102];
double wp[100], owp[100], o2wp[100], rpi[100];
int cwin[100], ctotal[100];

int main()
{
	int t, tind;
	int n;
	int i, j, k;
	//int cwin, ctotal;
	scanf("%d", &t);
	for(tind = 1; tind <= t; tind++) {
		scanf("%d", &n);
		for(i = 0; i < n; i++) {
			scanf("%s", array2Dim[i]);
		}
		for(i = 0; i < n; i++) {
			cwin[i] = ctotal[i] = 0;
			for(j = 0; j < n; j++) {
				if(array2Dim[i][j] == '1') {
					cwin[i]++, ctotal[i]++;
				}
				else if(array2Dim[i][j] == '0') {
					ctotal[i]++;
				}
			}
			wp[i] = (double)cwin[i]/ctotal[i];
		}

		for(i = 0; i < n; i++) {
			owp[i] = 0.0; 
			for(j = 0; j < n; j++) {
				if(array2Dim[i][j] == '1' || array2Dim[i][j] == '0') {
					int temp = ((array2Dim[i][j] == '0') ? 1:0) ;
					owp[i] += (double )(cwin[j] - temp) / (ctotal[j] - 1);
				}
			}
			owp[i] = (double)owp[i] / ctotal[i];
		}
		for(i = 0; i < n; i++) {
			o2wp[i] = 0.0; 
			for(j = 0; j < n; j++) {
				if(array2Dim[i][j] == '1' || array2Dim[i][j] == '0') {
					o2wp[i] += owp[j];
				}
			}
			o2wp[i] = (double)o2wp[i] / ctotal[i];
		}
		for(i = 0; i < n; i++) {
			rpi[i] = 0.25 * wp[i] + 0.5 *owp[i] + 0.25 * o2wp[i];
		}
		printf("Case #%d is:\n",tind);
		for(i = 0; i < n; i++) {
			printf("%.7f\n", rpi[i]);
		}
	}
}
