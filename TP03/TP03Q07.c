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
    char cast[512];
    char country[64];
    char date_added[64];
    int release_year;
    char rating[32];
    char duration[32];
    char listed_in[256];
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

// Função robusta de parsing de linha CSV com suporte a campos entre aspas
void parseCSVLine(char* line, Show* s) {
    char* token;
    int campo = 0, i = 0;
    int len = strlen(line);
    char buffer[MAX_LINE];
    int b = 0;
    int entreAspas = 0;

    memset(buffer, 0, sizeof(buffer));
    for (i = 0; i <= len; i++) {
        if (line[i] == '"') entreAspas = !entreAspas;
        else if ((line[i] == ',' && !entreAspas) || line[i] == '\0' || line[i] == '\n') {
            buffer[b] = '\0';
            switch (campo) {
                case 0: strcpy(s->show_id, buffer); break;
                case 1: strcpy(s->type, buffer); break;
                case 2: strcpy(s->title, buffer); break;
                case 3: strcpy(s->director, buffer); break;
                case 4: strcpy(s->cast, buffer); break;
                case 5: strcpy(s->country, buffer); break;
                case 6: strcpy(s->date_added, buffer); break;
                case 7: s->release_year = atoi(buffer); break;
                case 8: strcpy(s->rating, buffer); break;
                case 9: strcpy(s->duration, buffer); break;
                case 10: strcpy(s->listed_in, buffer); break;
            }
            campo++;
            b = 0;
            memset(buffer, 0, sizeof(buffer));
        } else {
            buffer[b++] = line[i];
        }
    }
}

Show buscarShowPorId(const char* id) {
    FILE* file = fopen("disneyplus.csv", "r");
    Show s;
    memset(&s, 0, sizeof(Show));

    if (!file) return s;

    char linha[MAX_LINE];
    while (fgets(linha, sizeof(linha), file)) {
        if (strstr(linha, id) == linha) {
            parseCSVLine(linha, &s);
            break;
        }
    }
    fclose(file);
    return s;
}

int main() {
    FilaCircular fila;
    inicializarFila(&fila);

    char entrada[64];
    while (scanf("%s", entrada) && strcmp(entrada, "FIM") != 0) {
        Show s = buscarShowPorId(entrada);
        if (strlen(s.title) > 0)
            inserirFila(&fila, s);
    }

    imprimirFila(&fila);
    return 0;
}
