#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX_FIELD 256
#define TAM_FILA 5

typedef struct {
    char id[32];
    char title[MAX_FIELD];
    int releaseYear;
} Show;

Show fila[TAM_FILA];
int frente = 0, tras = 0, tamanho = 0;

// Função para ler os dados do show a partir do CSV
Show lerShow(const char *arquivoCSV, const char *idBuscado) {
    FILE *fp = fopen(arquivoCSV, "r");
    Show s;
    strcpy(s.id, idBuscado);
    s.releaseYear = 0;
    strcpy(s.title, "NaN");

    if (!fp) return s;

    char linha[1024];
    while (fgets(linha, sizeof(linha), fp)) {
        if (strstr(linha, idBuscado) == linha) {
            char *fields[3];
            int i = 0;
            char *token = strtok(linha, ",");
            while (token && i < 3) {
                fields[i++] = token;
                token = strtok(NULL, ",");
            }
            if (i >= 2) strncpy(s.title, fields[1], MAX_FIELD - 1);
            if (i >= 3) s.releaseYear = atoi(fields[2]);
            break;
        }
    }

    fclose(fp);
    return s;
}

void enfileirar(Show s) {
    if (tamanho == TAM_FILA) {
        Show removido = fila[frente];
        printf("(R) %s\n", removido.title);
        frente = (frente + 1) % TAM_FILA;
        tamanho--;
    }

    fila[tras] = s;
    tras = (tras + 1) % TAM_FILA;
    tamanho++;

    int soma = 0;
    for (int i = 0, idx = frente; i < tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        soma += fila[idx].releaseYear;
    }
    printf("%d\n", (int)round((double)soma / tamanho));
}

int main() {
    char csv[] = "/tmp/disneyplus.csv";  // Caminho para o arquivo CSV
    char id[32];
    while (scanf("%s", id) == 1 && strcmp(id, "FIM") != 0) {
        Show s = lerShow(csv, id);
        enfileirar(s);
    }

    int n;
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        char comando[8], idComando[32];
        scanf("%s %s", comando, idComando);
        if (strcmp(comando, "I") == 0) {
            Show s = lerShow(csv, idComando);
            enfileirar(s);
        }
    }

    for (int i = 0, idx = frente; i < tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        Show s = fila[idx];
        printf("=> %s ## %s ## %d ##\n", s.id, s.title, s.releaseYear);
    }

    return 0;
}
