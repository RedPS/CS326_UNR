#include <stdio.h>

int i; 
int times;

#define fact(n) for (times = 1, i = 1; i <= n; i++) {times *=i;}

int main()
{
	int a = 5;
	fact(a)
	printf("factorial of %d is %d \n", a, times );
	return 0;
}

