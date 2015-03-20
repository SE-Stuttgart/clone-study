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

int ccw(point p1, point p2, point p3)

// Slightly deficient function to determine if the two lines p1, p2 and
// p2, p3 turn in counter clockwise direction}
{
	double dx1, dx2, dy1, dy2;
	dx1 = p2.x - p1.x; dy1 = p2.y - p1.y;
	dx2 = p3.x - p2.x; dy2 = p3.y - p2.y;
	if (dy1*dx2 < dy2*dx1)
	{
		return 1;
	}
	else return 0;
}

int intersect(line l1, line l2)
{
	return
		((ccw(l1.p1, l1.p2, l2.p1) != ccw(l1.p1, l1.p2, l2.p2))
		&& (ccw(l2.p1, l2.p2, l1.p1) != ccw(l2.p1, l2.p2, l1.p2)));
}

int main(void) {
	int t, upperBound;
	int N;
	int array1[MAX_N];
	int array2[MAX_N];
	int i, j, count;

	scanf("%d\n", &upperBound);
	for (t = 0; t < upperBound; t++) {
		scanf("%d\n", &N);
		for (i = 0; i < N; i++) {
			scanf("%d %d\n", &(array1[i]), &(array2[i]));
		}

		count = 0;
		for (i = 0; i < N; i++) {
			for (j = i + 1; j < N; j++) {
				line L1, L2;

				L1.p1.x = 1; L1.p1.y = array1[i];
				L1.p2.x = 9; L1.p2.y = array2[i];

				L2.p1.x = 1; L2.p1.y = array1[j];
				L2.p2.x = 9; L2.p2.y = array2[j];

				if (intersect(L1, L2)) {
					count++;
				}
			}
		}

		printf("Case #%d: %d\n", t + 1, count);
	}


	return 0;
}
