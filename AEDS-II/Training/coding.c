/*Objetivos:
- ler três valores literais separadamente (um caractere em cada) do teclado e 
se esses todos forem diferentes entre si.
dizer se o primeiro valor lido está fora do intervalo definido pelos outros dois,
OBS.: Notar que não haverá garantias de ser o segundo menor que o terceiro.


Exemplos: { ('a','e','c'), ('e','a','c'), ('a','c','e'), ('e','c','a'), ('a','e','a') }
*/


#include<stdio.h>
int main(void){
char um, dois, tres; 
printf("digite tres letras\n");
scanf("%c %c %c", &um, &dois, &tres);
char maior, menor;
    if (dois > tres){
        maior = dois;
        menor = tres;
    }else{
        maior = tres;
        menor = dois;
    }
if(um != menor && um != maior && maior != menor){
    if(um > maior || um < menor){ 
        printf("\nsim, \"%c\" esta fora do intervalo de \"%c\" ate \"%c\"\n", um, menor, maior);
    }
}else{
    printf("\nnao\n");}

    
    
    
return 0;
}
/*Plano:
se Todos forem diferentes entre si: 
Verificar se um é maior que maior ou menor que menor 





*/