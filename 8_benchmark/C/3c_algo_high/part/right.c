#include <stdio.h>
#include <string.h>

#define ARRAYSIZE 100

int isVowel(char c) {
	if (c == 'a' || c == 'i' || c == 'u' || c == 'e' || c == 'o') {
		return 1;
	}
	return 0;
}

int calc(char* str){
	int max = 0;
	int len = strlen(str);
	int i;
	int tmp = 0;
	for (i = 0; i < len; i++) {
		if (isVowel(str[i])) {
			if (tmp > max) {
				max = tmp;
			}
			tmp = 0;
		} else {
			tmp++;
		}
	}
	if (tmp > max) {
		max = tmp;
	}
	return max;
}

int main(int argc, char const* argv[])
{
	int i, userInput, nameLength;
	char name[ARRAYSIZE], tmp[ARRAYSIZE];
	scanf("%d", &userInput);
	for (i = 0; i < userInput; i++) {
		int len, j, k;
		int ans = 0;
		int ret;
		printf("Case #%d: ", i + 1);
		strcpy(name, "");
		scanf("%s%d", name, &nameLength);
		len = strlen(name);
		for (j = 0; j < len; j++) {
			for (k = j+nameLength; k <= len; k++) {
				strncpy(tmp, "", ARRAYSIZE);
				strncpy(tmp, &name[j], k - j);
				ret = calc(tmp);
				if (ret >= nameLength) {
					ans++;
				} else {
					; //no operation
				}
			}
		}
		printf("%d\nameLength", ans);
	}
	return 0;
}
