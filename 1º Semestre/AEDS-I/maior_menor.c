#include <stdio.h>
int main (void){

    int x, y;
    scanf("%d %d", &x, &y);

    if(x>y){
        printf("O primeiro e maior");
    }
    else{
        printf("O segundo e maior");
    }
    
    return 0;
}