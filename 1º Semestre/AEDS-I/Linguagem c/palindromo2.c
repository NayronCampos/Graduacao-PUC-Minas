#include <stdio.h>
#include <string.h>
int main (void){
 
    char palavra[800];
    scanf("%s", palavra);
    
    int n=0;
    while(palavra[n]!='\0'){
        n++;
    }

    int resposta=1;
    int direita=n-;
   
    for(int esquerda=0;esquerda<direita;esquerda++){
        if(palavra[esquerda]==palavra[direita]){
                direita--;
        }
        else{
            resposta=2;
        }
    }
    if(resposta==1){
        printf("Eh palindromo");
    }
    else if(resposta==2)  {
        printf("nao eh palindromo");
    }

return 0;
}