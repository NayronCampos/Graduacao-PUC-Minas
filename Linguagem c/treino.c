#include <stdio.h>
int soma(int x, int *p){
    if(x==0){
        return 0;
    }
    return p[x-1] + soma(x-1, p);
   
}

int main (void){
int n;
do{
scanf("%d", &n);
int valores[n];


for(int i=0;i<n;i++){
   scanf("%d", &valores[i]);
}
printf("\n%d", soma(n,valores));
}while(n!=0);
}