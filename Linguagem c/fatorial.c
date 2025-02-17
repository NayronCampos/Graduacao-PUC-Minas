#include <stdio.h>
int main (void){

    int y, total, x=1;

    scanf("%d", &y);

    while(y>0){
        
        x*=y;
        y--;
    }
    printf("%d", x);
    return 0;
}