#include <stdio.h>

void soma(int x, int resultado){
if(x==0){
    printf("A soma eh: %d", resultado);
    return;
}

 soma(x-1, resultado + x);
}

int main (void){
    int n;
    printf("Digite seu valor: \n");
    scanf("%d", &n);
    soma(n, 0);

    return 0;
}