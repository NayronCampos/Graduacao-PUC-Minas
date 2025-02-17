#include <stdio.h>
int main (void){

    int y, total, x=1;

    scanf("%d", &y);

    for(int i=1;i<=y;i++){
        x*=i;
        
    }
    printf("%d\n", x);

    x=1;

    while(y>0){
        
        x*=y;
        y--;
    }
    printf("%d\n", x);
    

    return 0;
}