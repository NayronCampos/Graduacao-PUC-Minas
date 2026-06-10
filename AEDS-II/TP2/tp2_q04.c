#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>

#define MAX       100000
#define LINHA_LEN 1000

long long comparacoes = 0;

//Pegando os valores
int parseId(const char *s) {
    int x = 0;
    for (int i = 0; s[i]; i++) {
        if (isdigit((unsigned char)s[i])) {
            x = x * 10 + (s[i] - '0');
        }
    }
    return x;
}

//ordenando por ser pesq binária
void selectionSort(int *a, int n) {
    for (int i = 0; i < n - 1; i++) {
        int min = i;
        for (int j = i + 1; j < n; j++) {
            if (a[j] < a[min]) {
                min = j;
            }
        }
        int tmp = a[i];
        a[i] = a[min];
        a[min] = tmp;
    }
}


int pesqBin(int *vet, int n, int x) {
    int esq = 0, dir = n - 1, meio;
    while (esq <= dir) {
        meio = (esq + dir) / 2;
        comparacoes++;
        if (vet[meio] == x) {
            return 1;
        } else if (x > vet[meio]) {
            esq = meio + 1;
        } else {
            dir = meio - 1;
        }
    }
    return 0;
}

int main() {
    char linha[LINHA_LEN];
    int vet[MAX];
    int n = 0;


    while (fgets(linha, sizeof(linha), stdin)) {
        linha[strcspn(linha, "\r\n")] = '\0';
        if (strcmp(linha, "FIM") == 0) break;


        vet[n++] = parseId(linha);


        if (!fgets(linha, sizeof(linha), stdin)) break;
    }


    selectionSort(vet, n);


    clock_t t0 = clock();
    while (fgets(linha, sizeof(linha), stdin)) {
        linha[strcspn(linha, "\r\n")] = '\0';
        if (strcmp(linha, "FIM") == 0) break;

        int query = parseId(linha);
        int achou = pesqBin(vet, n, query);
        printf("%s\n", achou ? "SIM" : "NAO");
    }
    clock_t t1 = clock();


    double tempoMs = (double)(t1 - t0) * 1000.0 / CLOCKS_PER_SEC;
    const char *MATRICULA = "874422"; 
    char nomeArq[64];
    snprintf(nomeArq, sizeof(nomeArq), "%s_sequencial.txt", MATRICULA);

    FILE *fp = fopen(nomeArq, "w");
    if (fp) {
        fprintf(fp, "%s\t%.0f\t%lld",
                MATRICULA, tempoMs, comparacoes);
        fclose(fp);
    } else {
        perror("Erro ao criar arquivo de log");
    }

    return 0;
}
