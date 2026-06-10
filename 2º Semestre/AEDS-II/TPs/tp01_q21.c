#include <stdio.h>
#include <string.h>

// Função recursiva que imprime os caracteres da string em ordem invertida.
void inverterRec(const char *str)
{
    if (*str == '\0')
    {
        return;
    }
    inverterRec(str + 1); // Chama a função
    putchar(*str);
}

int main(void)
{
    char linha[1000];

    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        size_t len = strlen(linha);
        if (len > 0 && linha[len - 1] == '\n')
        {
            linha[len - 1] = '\0';
            len--;
        }
        if (len == 0)
        {
            break;
        }
        inverterRec(linha); // Chama a função recursiva para inverter a string
        putchar('\n');
    }

    return 0;
}
