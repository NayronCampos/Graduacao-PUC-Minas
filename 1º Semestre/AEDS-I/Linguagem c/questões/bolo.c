#include <stdio.h>

int main (void){


    int kg, p,total=0;
    scanf("%d %d", &kg, &p);

    while( kg%p!=0 ){
        kg++;
        total++;
       
    }

    printf("\n%d", total);
    return 0;
}