#include <stdio.h>

int palindromorec(char m[], int e, int d) {
    if (e >= d) { 
        return 1;
    }
    if (m[e] != m[d]) { 
        return 0;
    }
    return palindromorec(m, e + 1, d - 1); 
}

int main(void) {
    char palavra[20];
    int i = 0;
    printf("Digite a palavrinha\n");

    scanf("%s", palavra);

    while (palavra[i] != '\0') {
        i++;
    }

    int esq = 0;
    int dir = i - 1; 

    if (palindromorec(palavra, esq, dir)) {
        printf("Eh um palindromo!\n");
    } else {
        printf("Nao eh um palindromo!\n");
    }

    return 0;
}