#include <stdio.h>
void imprimir(char *p, int x){
    for(int i=x-1; i>=0;i--){
        printf("%c", p[i]);
    }

}

int main (void){
    char palavra[10];
    int i=0;
    
    while(i<10){
        scanf("%c", &palavra[i]);
        if(palavra[i]=='\n'){
            break;
        }
        i++;
        
    }
    imprimir(palavra, i);
}