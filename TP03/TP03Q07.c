#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX_QUEUE 5
#define MAX_LINE 1024

typedef struct {
    char show_id[32];
    char type[64];
    char title[128];
    char director[128];
    char cast[256];
    char country[64];
    char date_added[64];
    int release_year;
    char rating[32];
    char duration[32];
    char listed_in[128];
} Show;

typedef struct {
    Show queue[MAX_QUEUE];
    int front;
    int rear;
    int count;
} FilaCircular;

void inicializarFila(FilaCircular *fila) {
    fila->front = 0;
    fila->rear = 0;
    fila->count = 0;
}

int estaCheia(FilaCircular *fila) {
    return fila->count == MAX_QUEUE;
}

int estaVazia(FilaCircular *fila) {
    return fila->count == 0;
}

void removerFila(FilaCircular *fila) {
    printf("(R) %s\n", fila->queue[fila->front].title);
    fila->front = (fila->front + 1) % MAX_QUEUE;
    fila->count--;
}

void inserirFila(FilaCircular *fila, Show s) {
    if (estaCheia(fila)) {
        removerFila(fila);
    }
    fila->queue[fila->rear] = s;
    fila->rear = (fila->rear + 1) % MAX_QUEUE;
    fila->count++;

    // Calcular média
    int soma = 0;
    for (int i = 0, idx = fila->front; i < fila->count; i++, idx = (idx + 1) % MAX_QUEUE) {
        soma += fila->queue[idx].release_year;
    }
    int media = (int)round((double)soma / fila->count);
    printf("[Media] %d\n", media);
}

void imprimirFila(FilaCircular *fila) {
    for (int i = 0, idx = fila->front; i < fila->count; i++, idx = (idx + 1) % MAX_QUEUE) {
        Show *s = &fila->queue[idx];
        printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
            s->show_id, s->title, s->type, s->director, s->cast,
            s->country, s->date_added, s->release_year,
            s->rating, s->duration, s->listed_in);
    }
}

void limparCampo(char *str) {
    if (str[0] == '"') {
        size_t len = strlen(str);
        if (str[len - 1] == '"') {
            memmove(str, str + 1, len - 2);
            str[len - 2] = '\0';
        }
    }
}

void extrairLista(char *dest, const char *src) {
    char copia[256];
    strncpy(copia, src, sizeof(copia));
    copia[sizeof(copia) - 1] = '\0';

    limparCampo(copia);
    for (int i = 0; copia[i]; i++) {
        if (copia[i] == ',') {
            if (i > 0 && copia[i - 1] != ' ')
                dest[strlen(dest)] = ',';
            dest[strlen(dest)] = ' ';
        } else {
            dest[strlen(dest)] = copia[i];
        }
    }
    dest[strlen(dest)] = '\0';
}

Show buscarShowPorId(const char *id) {
    FILE *arq = fopen("disneyplus.csv", "r");
    Show s;
    strcpy(s.show_id, id);
    strcpy(s.type, "NaN");
    strcpy(s.title, "NaN");
    strcpy(s.director, "NaN");
    strcpy(s.cast, "NaN");
    strcpy(s.country, "NaN");
    strcpy(s.date_added, "NaN");
    s.release_year = 0;
    strcpy(s.rating, "NaN");
    strcpy(s.duration, "NaN");
    strcpy(s.listed_in, "NaN");

    if (!arq) return s;

    char linha[MAX_LINE];
    while (fgets(linha, sizeof(linha), arq)) {
        char *token = strtok(linha, ",");
        if (token && strcmp(token, id) == 0) {
            strcpy(s.show_id, token);
            token = strtok(NULL, ","); if (token) strcpy(s.type, token);
            token = strtok(NULL, ","); if (token) strcpy(s.title, token);
            token = strtok(NULL, ","); if (token) strcpy(s.director, token);
            token = strtok(NULL, ","); if (token) strcpy(s.cast, token);
            token = strtok(NULL, ","); if (token) strcpy(s.country, token);
            token = strtok(NULL, ","); if (token) strcpy(s.date_added, token);
            token = strtok(NULL, ","); if (token) s.release_year = atoi(token);
            token = strtok(NULL, ","); if (token) strcpy(s.rating, token);
            token = strtok(NULL, ","); if (token) strcpy(s.duration, token);
            token = strtok(NULL, "\n"); if (token) strcpy(s.listed_in, token);

            limparCampo(s.title);
            limparCampo(s.director);
            limparCampo(s.country);
            limparCampo(s.date_added);
            limparCampo(s.rating);
            limparCampo(s.duration);

            char temp_cast[256] = "";
            char temp_listed[128] = "";

            extrairLista(temp_cast, s.cast);
            extrairLista(temp_listed, s.listed_in);

            snprintf(s.cast, sizeof(s.cast), "%s", temp_cast);
            snprintf(s.listed_in, sizeof(s.listed_in), "%s", temp_listed);

            break;
        }
    }
    fclose(arq);
    return s;
}

int main() {
    FilaCircular fila;
    inicializarFila(&fila);

    char entrada[32];
    while (scanf("%s", entrada) && strcmp(entrada, "FIM") != 0) {
        Show s = buscarShowPorId(entrada);
        inserirFila(&fila, s);
    }

    imprimirFila(&fila);
    return 0;
}
