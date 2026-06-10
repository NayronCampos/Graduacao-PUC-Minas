#include <stdio.h>
int soma(int x, int *p, int somar){
    if(x==0){
        return somar;
    }
    somar+= p[x-1];
    return soma(x-1,p, somar);
   
}

int main (void){
int n, somar=0;
scanf("%d", &n);
int valores[n];


for(int i=0;i<n;i++){
   scanf("%d", &valores[i]);
}
printf("%d", soma(n,valores, somar));

}