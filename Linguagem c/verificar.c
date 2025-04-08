#include <stdio.h>
#include <stdbool.h>

int main (void){
int t;
bool result;
scanf("%d", &t);
char l1,l2,l3;

while(t>0){
scanf("%c %c %c", &l1,&l2,&l3);

if(l1<l2 && l2<l3){
result=true;}
else{
result=false;}
if(result=true){
    printf("Sim\n");
}
else{
    printf("Nao");
}
t--;
};
}