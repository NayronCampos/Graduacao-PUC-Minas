#include <stdio.h>

void imprime(char vetor[], int count){

    for(int i=0;i<count;i++){
        if((vetor[i]>='A' && vetor[i]<='Z') || (vetor[i]>='a' && vetor[i]<='z') 
        || vetor[i]>='0' && vetor[i]<='9'){
            printf("%c",vetor[i]);
        }
        else{
            int tmp = vetor[i];
            vetor[i] = vetor[i+1];
            vetor[i+1] = tmp;
            printf("%c",vetor[i]);
        }
    }
}

int main (void){
    int j=0;
    scanf("%d", &j);
    char ch;
    scanf("%c", &ch);

    for(int i=0;i<j;i++){

        int count=0;
        char vetor[30];

        for(int i=0; vetor[i]!='\0'; i++){

        scanf("%c", &vetor[i]);
            if(vetor[i]=='\n'){
                break;
            }
        count++;
        }
        imprime(vetor, count);
        printf("\n");
    }
}