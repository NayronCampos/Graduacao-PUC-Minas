#include <stdio.h>
int main(void)
{
    int n, n2, soma, termos;
    scanf("%d %d", &n, &n2);
    scanf("%d", &termos);
    for (int i = 1; i <= termos; i++)
    {
        soma = n + n2;
        n = n2;
        n2 = soma;
    }
    printf("soma eh igual %d", soma);

    return 0;
}