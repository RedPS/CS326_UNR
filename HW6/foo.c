#include <stdio.h>
#include <stdlib.h>

void foo()
{
	int i;
	printf("%d ",i++);
}

void main()
{
	int j;

	int * a; 	
	for (j = 1; j <=10; j++)
	{
		a = malloc (sizeof(int));	

		foo();	

		free(a);

	}

}










