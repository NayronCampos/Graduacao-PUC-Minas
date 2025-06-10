#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>

#define MAX_LINE 1000
#define TAM_FILA 5

typedef struct Show {
    char id[20];
    char title[200];
    char type[50];
    char director[100];
    char cast[300];
    char country[100];
    char date_added[50];
    int release_year;
    char rating[10];
    char duration[20];
    char listed_in[200];
} Show;

typedef struct {
    Show array[TAM_FILA];
    int inicio;
    int fim;
    int tamanho;
} FilaCircular;

void trim(char* str) {
    int len = strlen(str);
    while (len > 0 && (str[len - 1] == '\n' || str[len - 1] == '\r'))
        str[--len] = '\0';
}

char* lerCampo(char* linha, char* campo) {
    int i = 0, j = 0;
    bool aspas = false;
    while (linha[i] != '\0') {
        if (linha[i] == '"') {
            aspas = !aspas;
        } else if (linha[i] == ',' && !aspas) {
            break;
        } else {
            campo[j++] = linha[i];
        }
        i++;
    }
    campo[j] = '\0';
    if (linha[i] == ',') i++;
    return linha + i;
}

Show lerShowPorID(char* idBuscado, FILE* csv) {
    Show s;
    rewind(csv);
    char linha[MAX_LINE], campo[300];

    while (fgets(linha, MAX_LINE, csv)) {
        if (strstr(linha, idBuscado) == linha) {
            char* p = linha;
            p = lerCampo(p, campo);
            strcpy(s.id, campo);
            p = lerCampo(p, s.type);
            p = lerCampo(p, s.title);
            p = lerCampo(p, s.director);
            p = lerCampo(p, s.cast);
            p = lerCampo(p, s.country);
            p = lerCampo(p, s.date_added);
            if (strlen(s.date_added) == 0) strcpy(s.date_added, "March 1, 1900");
            p = lerCampo(p, campo);
            s.release_year = strlen(campo) > 0 ? atoi(campo) : 0;
            p = lerCampo(p, s.rating);
            p = lerCampo(p, s.duration);
            p = lerCampo(p, s.listed_in);
            return s;
        }
    }

    strcpy(s.title, "NaN");
    return s;
}

void inicializarFila(FilaCircular* f) {
    f->inicio = f->fim = f->tamanho = 0;
}

bool filaCheia(FilaCircular* f) {
    return f->tamanho == TAM_FILA;
}

bool filaVazia(FilaCircular* f) {
    return f->tamanho == 0;
}

void remover(FilaCircular* f) {
    Show removido = f->array[f->inicio];
    printf("(R) %s\n", removido.title);
    f->inicio = (f->inicio + 1) % TAM_FILA;
    f->tamanho--;
}

void inserir(FilaCircular* f, Show s) {
    if (filaCheia(f)) {
        remover(f);
    }
    f->array[f->fim] = s;
    f->fim = (f->fim + 1) % TAM_FILA;
    f->tamanho++;

    int soma = 0;
    for (int i = 0, idx = f->inicio; i < f->tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        soma += f->array[idx].release_year;
    }
    int media = (int)round((double)soma / f->tamanho);
    printf("[Media] %d\n", media);
}

void imprimirFila(FilaCircular* fila){
    for (int i = 0, idx = fila->inicio; i < fila->tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        Show s = fila->array[idx];
        printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
               s.id, s.title, s.type, s.director, s.cast, s.country,
               s.date_added, s.release_year, s.rating, s.duration, s.listed_in);
    }
}


int main() {
    FILE* csv = fopen("/tmp/disneyplus.csv", "r");
    if (!csv) {
        printf("Erro ao abrir /tmp/disneyplus.csv\n");
        return 1;
    }

    FilaCircular fila;
    inicializarFila(&fila);

    char linha[MAX_LINE];
    while (fgets(linha, MAX_LINE, stdin)) {
        trim(linha);
        if (strcmp(linha, "FIM") == 0) break;
        Show s = lerShowPorID(linha, csv);
        inserir(&fila, s);
    }

    imprimirFila(&fila);
    fclose(csv);
    return 0;
}
