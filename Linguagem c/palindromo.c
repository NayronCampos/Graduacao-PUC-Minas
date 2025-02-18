#include<stdio.h>
int main (void){

    char tam[20];
    int i=0;
    scanf("%s", tam);
    
    while(tam[i]!='\0'){
        i++;
    }
    int resp=1;
    printf("%d\n", i);
    int fim= i-1;
    for(int j=0;j<i;j++){
        if(tam[j]==tam[fim]){
            fim--;
        }
        else{
            resp=0;
        }
    }
    if(resp==1){
        printf("Eh palindromo");
    }
    else {
        printf("Nao eh palindromo");
    }

    
    return 0;
}