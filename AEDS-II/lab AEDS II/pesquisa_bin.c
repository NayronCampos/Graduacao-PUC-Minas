#include <stdio.h>
#include <stdbool.h>
int main (void){

    int n,x, count=1;
    scanf("%d", &n);
    int array[n];

    for(int i=0;i<n;i++){
        scanf("%d", &array[i]);
    }
    
    bool resp = false;
    int esq=0, dir=n-1;
    printf("numero a encontrar:");
    scanf("%d", &x);

    while(esq<=dir){
        int meio = (esq+dir)/2;
        if(array[meio]==x){
            resp=true;
            esq=dir+1;
        }
        else if(array[meio]>x){
            dir=meio-1;
            count++;
        }
        else if(array[meio]<x){
            esq=meio+1;
            count++;
        }
    }

    if(resp==1){
    printf("\ntrue\n");}
        else{
    printf("\nfalse\n");}

    printf("%d\n", count);



    return 0;
}