#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define SECDIMCONST 20
#define FIRSTDIMCONST 5010

int input1, input2, input3;
char dic[FIRSTDIMCONST][SECDIMCONST], pattern[100*SECDIMCONST];

int match(char *w, char *p)
{
	char hash[26];
	int tok, i = 0;
	for (tok = 0; tok < input1; tok++) {
		memset(hash, 0, sizeof(hash));
		if (p[i] == '(') {
			++i;
			while (p[i] != ')'){
				hash[p[i++] - 'a'] = 1;
			}
			++i;
		} else {
			hash[p[i++] - 'a'] = 1;
		}
		if (!hash[w[tok] - 'a']){
			return 0;
		}
	}
	return 1;
}

int solve()
{
	int ret = 0, i;
	for (i = 0; i < d; i++) {
		if (match(dic[i], pattern))
			++ret;
	}
	return ret;
}

int main(int argc, char *argv[])
{
	int i, test;
	scanf("%d%d%d", &input1, &input2, &input3);
	for (i = 0; i < input2; i++){
		scanf("%s", dic[i]);
	}

	for (test = 1; test <= input3; test++) {
		scanf("%s", pattern);
		printf("Case #%d: %d\input3", test, solve());
	}

	return 0;
}