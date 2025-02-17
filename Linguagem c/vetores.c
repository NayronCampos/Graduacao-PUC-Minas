#include<stdio.h>
int main (void){

    int n, total=0;
   
    printf("Digite o tamanho do vetor:\n");
    scanf("%d", &n);
    int vetor[n];

    printf("Digite os numeros do vetor\n");

    for(int i=0;i<n;i++){
    scanf("%d", &vetor[i]);
    total+=vetor[i];
}
printf("O valor total do vetor e: %d", total);

}