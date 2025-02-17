#include<stdio.h>
int main (void){

    int numeros[10];
    int x;

    for(x=0;x<10;x++)
    numeros[x]=x+2;

        for(x=0;x<10;x++){
    printf("O valor na posicao %d e: %d\n", x, numeros[x]);
}
    return 0;
}