#include <stdio.h>

int fibonaci(int a, int b, int valor){
    int temp =0;
    for(int i=0; i<valor; i++){
        temp = a+b;
        printf("valor %d eh %d", i, temp);
        a=b;
        b=temp;
    }
    return ;

}

int main (void){

    int n, x1, x2;
    scanf("%d %d %d", &n, &x1, &x2);
    printf("fibonacci eh: %d", fibonaci(x1,x2,n));
}