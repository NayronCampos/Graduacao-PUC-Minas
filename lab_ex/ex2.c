#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_AVIOES 10000
#define TAM_ID        10

int main() {

    char oeste[MAX_AVIOES][TAM_ID];
    char norte[MAX_AVIOES][TAM_ID];
    char sul[MAX_AVIOES][TAM_ID];
    char leste[MAX_AVIOES][TAM_ID];
    int co = 0, cn = 0, cs = 0, cl = 0;

    char token[TAM_ID];
    int direcaoAtual = 0;

    while (scanf("%s", token) == 1) {

        if (strcmp(token, "0") == 0) {
            break;
        }
        // Se começa com ‘-’, é um ponto cardeal
        if (token[0] == '-') {
            direcaoAtual = atoi(token);
            continue;
        }


        if (direcaoAtual == -1 && co < MAX_AVIOES) {
            strcpy(oeste[co++], token);
        }
        else if (direcaoAtual == -3 && cn < MAX_AVIOES) {
            strcpy(norte[cn++], token);
        }
        else if (direcaoAtual == -2 && cs < MAX_AVIOES) {
            strcpy(sul[cs++], token);
        }
        else if (direcaoAtual == -4 && cl < MAX_AVIOES) {
            strcpy(leste[cl++], token);
        }

    }



    int maxLinhas = co;
    if (cn > maxLinhas) maxLinhas = cn;
    if (cs > maxLinhas) maxLinhas = cs;
    if (cl > maxLinhas) maxLinhas = cl;

    int primeiro = 1;
    for (int i = 0; i < maxLinhas; i++) {
        if (i < co) {
            if (!primeiro) printf(" ");
            printf("%s", oeste[i]);
            primeiro = 0;
        }
        if (i < cn) {
            if (!primeiro) printf(" ");
            printf("%s", norte[i]);
            primeiro = 0;
        }
        if (i < cs) {
            if (!primeiro) printf(" ");
            printf("%s", sul[i]);
            primeiro = 0;
        }
        if (i < cl) {
            if (!primeiro) printf(" ");
            printf("%s", leste[i]);
            primeiro = 0;
        }
    }
    printf("\n");
    return 0;
}
