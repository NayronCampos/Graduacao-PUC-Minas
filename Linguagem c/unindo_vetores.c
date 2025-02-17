#include<stdio.h>
int main (void){

    int vetor1[5]={4,5,6,7,8}; 
    int vetor2[5]={6,7,8,9,20};
    int vetor3[10];

    for(int i=0;i<10;i++){
        if(i<5){
            vetor3[i]=vetor1[i];
        }
        else{
            vetor3[i]=vetor2[i-5];
        }
    }

    for(int i=0;i<10;i++){
        printf("%d ", vetor3[i]);
    }

    return 0;
}