#include <stdio.h>

int fatorial(int x){
    if(x==1 || x==0){
        return 1;
    }
    return x * fatorial(x-1);
    
}   

int main (void){

    int n;
    printf("Digite o valor:\n");
    scanf("%d", &n);
    printf("%d", fatorial(n));

    return 0;
}