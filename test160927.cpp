// test160927.cpp : �ܼ� ���� ���α׷��� ���� �������� �����մϴ�.
//

#include "stdafx.h"
#include "stdlib.h"
#define N 1000

int main()
{
	char s[N], i = 0;
	scanf("%c", &s[0]);
	while (s[i] != '\n')
		scanf("%c", &s[++i]);
	for (int j = i - 1; j >= 0; j--)
		printf("%c", s[j]);


    return 0;
}

