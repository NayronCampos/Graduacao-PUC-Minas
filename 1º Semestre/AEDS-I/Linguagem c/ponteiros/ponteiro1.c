#include <stdio.h>
int main (void){
int *x;
int y[10];
x=y;
for(int i=0;i<10;i++){
scanf("%d", &x[i]);}

for(int i=0;i<10;i++){
printf("%d ", x[i]);}

}