#include <stdio.h>
int main(void)
{
    int n;
    scanf("%d", &n);
    int vetor[n];
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &vetor[i]);
    }
    int menor = vetor[0];
    int maior = vetor[0];

    for (int i = 1; i < n; i++)
    {
        if (menor >= vetor[i])
        {
            menor = vetor[i];
        }
        else if (maior <= vetor[i])
        {
            maior = vetor[i];
        }
    }
    printf("o maior eh %d o menor eh %d", maior, menor);
    return 0;
}