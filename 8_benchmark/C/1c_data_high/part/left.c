#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

#define MAX_N 1024

typedef struct _point {
	double x;
	double y;
} point;

typedef struct _line {
	point p1;
	point p2;
} line;

int counterClockWise(point p1, point p2, point p3)

	// Slightly deficient function to determine if the two lines p1, p2 and
	// p2, p3 turn in counter clockwise direction}
{
	double dx1, dx2, dy1, dy2;
	dx1 = p2.x - p1.x; dy1 = p2.y - p1.y;
	dx2 = p3.x - p2.x; dy2 = p3.y - p2.y;
	if(dy1*dx2 < dy2*dx1) {
		return 1;
	}else{
		return 0;
	}
}

int intersect(line l1, line l2)
{
	return
		((counterClockWise(l1.p1, l1.p2, l2.p1) != counterClockWise(l1.p1, l1.p2, l2.p2))
		&& (counterClockWise(l2.p1, l2.p2, l1.p1) != counterClockWise(l2.p1, l2.p2, l1.p2)));
}

int main(void) {
	int t_Out, t_In;
	int n_In;
	int y1[MAX_N];
	int y2[MAX_N];
	int i, j, count;

	scanf("%d\n", &t_In);
	for (t_Out = 0; t_Out < t_In; t_Out++) {
		scanf("%d\n", &n_In);
		for (i = 0; i < n_In; i++) {
			scanf("%d %d\n", &(y1[i]), &(y2[i]));
		}

		count = 0;
		for (i = 0; i < n_In; i++) {
			for (j = i+1; j < n_In; j++) {
				line L1, L2;

				L1.p1.x = 1; L1.p1.y = y1[i];
				L1.p2.x = 9; L1.p2.y = y2[i];

				L2.p1.x = 1; L2.p1.y = y1[j];
				L2.p2.x = 9; L2.p2.y = y2[j];

				if (intersect(L1, L2)) {
					count++;
				}
			}
		}

		printf("Case #%d: %d\n", t_Out+1, count);
	}

	return 0;
}