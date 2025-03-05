#include <stdio.h>
#include <string.h>

int somarRec(char num[], int pos)
{
    // Caso base: fim da string (ou nova linha)
    if (num[pos] == '\0' || num[pos] == '\n')
    {
        return 0;
    }
    // Converte o caractere para dígito e soma com o resultado da chamada recursiva
    return (num[pos] - '0') + somarRec(num, pos + 1);
}

int somar(char numero[])
{
    return somarRec(numero, 0);
}

int main(void)
{
    char valor[1000];

    // Leitura de cada linha até encontrar uma linha vazia
    while (fgets(valor, sizeof(valor), stdin) != NULL)
    {
        // Se a linha for apenas uma nova linha, encerra o loop
        if (valor[0] == '\n')
        {
            break;
        }
        printf("%d\n", somar(valor));
    }

    return 0;
}
