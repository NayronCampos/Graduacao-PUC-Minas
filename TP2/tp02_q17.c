#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_SHOWS 10000
#define MAX_LINE 10000

typedef struct {
    char *showId, *type, *title, *director;
    char **cast;    int castSize;
    char *country, *dateAdded;
    int releaseYear;
    char *rating, *duration;
    char **listedIn; int listedSize;
} Show;

//--------------------------------------------------
// Helpers para leitura e parsing
//--------------------------------------------------
static char *trim(char *s) {
    char *end;
    while (isspace((unsigned char)*s)) s++;
    if (*s == '\0') return s;
    end = s + strlen(s) - 1;
    while (end > s && isspace((unsigned char)*end)) end--;
    *(end+1) = '\0';
    return s;
}

static char *dupstr(const char *s) {
    char *d = malloc(strlen(s)+1);
    return d ? strcpy(d, s) : NULL;
}

// Divide uma lista separada por vírgulas e já ordena com qsort
static void split_and_sort(char *field, char ***out, int *outSize) {
    *outSize = 0;
    if (!field || !*field || strcmp(field,"NaN")==0) {
        *out = NULL;
        return;
    }
    char *copy = dupstr(field), *tok = strtok(copy, ",");
    int cap = 4;
    *out = malloc(cap * sizeof(char*));
    while (tok) {
        if (*outSize >= cap) {
            cap *= 2;
            *out = realloc(*out, cap * sizeof(char*));
        }
        (*out)[(*outSize)++] = dupstr(trim(tok));
        tok = strtok(NULL, ",");
    }
    free(copy);
    if (*outSize > 1) {
        qsort(*out, *outSize, sizeof(char*),
              (int(*)(const void*,const void*)) strcmp);
    }
}

static void parse_show(Show *s, const char *line) {
    char buf[MAX_LINE], *fields[11];
    int inQ = 0, bi = 0, fi = 0;
    for (int i = 0; line[i] && line[i] != '\n'; i++) {
        char c = line[i];
        if (c == '"') {
            inQ = !inQ;
        } else if (c == ',' && !inQ) {
            buf[bi] = '\0';
            fields[fi++] = dupstr(trim(buf));
            bi = 0;
        } else {
            buf[bi++] = c;
        }
    }
    buf[bi] = '\0';
    fields[fi++] = dupstr(trim(buf));
    // atribuições
    s->showId      = dupstr(fields[0]);
    s->type        = dupstr(fields[1]);
    s->title       = dupstr(fields[2]);
    s->director    = dupstr(fields[3]);
    split_and_sort(fields[4], &s->cast,    &s->castSize);
    s->country     = dupstr(fields[5]);
    s->dateAdded   = fields[6][0] ? dupstr(fields[6])
                                   : dupstr("March 1, 1900");
    s->releaseYear = atoi(fields[7]);
    s->rating      = dupstr(fields[8]);
    s->duration    = dupstr(fields[9]);
    split_and_sort(fields[10], &s->listedIn, &s->listedSize);
    // libera temporários
    for (int i = 0; i < fi; i++) free(fields[i]);
}

static void print_show(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [",
           s->showId, s->title, s->type, s->director);
    for (int i = 0; i < s->castSize; i++) {
        printf("%s", s->cast[i]);
        if (i < s->castSize - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
           s->country, s->dateAdded, s->releaseYear,
           s->rating, s->duration);
    for (int i = 0; i < s->listedSize; i++) {
        printf("%s", s->listedIn[i]);
        if (i < s->listedSize - 1) printf(", ");
    }
    printf("] ##\n");
}


//--------------------------------------------------
// Comparador: type, tie-breaker title
//--------------------------------------------------
static int cmp_show(const Show *a, const Show *b) {
    int c = strcmp(a->type, b->type);
    if (c != 0) return c;
    return strcmp(a->title, b->title);
}

//--------------------------------------------------
// Contadores para o log
//--------------------------------------------------
static int comparacoes = 0, movimentacoes = 0;

//--------------------------------------------------
// Heap‐sort “ao estilo Java”
//--------------------------------------------------

// troca dois ponteiros no heap
static void troca(Show **heap, int i, int j){
    Show *tmp = heap[i];
    heap[i] = heap[j];
    heap[j] = tmp;
    movimentacoes++;
}

// sobe o nó i até restaurar max‐heap
static void construir(Show **heap, int tamHeap, int i) {
    while (i > 1 && (++comparacoes, cmp_show(heap[i], heap[i/2]) > 0)) {
        troca(heap, i, i/2);
        i /= 2;
    }
}

// desce a raiz até restaurar max‐heap
static void reconstruir(Show **heap, int tamHeap) {
    int i = 1;
    while (i <= tamHeap/2) {
        int esq = 2*i, dir = 2*i+1, maior = esq;
        if (dir <= tamHeap && (++comparacoes, cmp_show(heap[dir], heap[esq]) > 0)) {
            maior = dir;
        }
        if (++comparacoes, cmp_show(heap[maior], heap[i]) > 0) {
            troca(heap, i, maior);
            i = maior;
        } else {
            break;
        }
    }
}

// ordena vet[0..n-1] usando heap temporário em 1..n
static void heap_sort(Show **vet, int n) {
    Show **heap = malloc((n+1)*sizeof(Show*));
    // copia para heap[1..n]
    for (int i = 0; i < n; i++) heap[i+1] = vet[i];
    // construção incremental
    for (int tam = 2; tam <= n; tam++) {
        construir(heap, tam, tam);
    }
    // ordenação
    int tam = n;
    while (tam > 1) {
        troca(heap, 1, tam--);
        reconstruir(heap, tam);
    }
    // copia de volta para vet[0..n-1]
    for (int i = 0; i < n; i++) {
        vet[i] = heap[i+1];
    }
    free(heap);
}

//--------------------------------------------------
// main
//--------------------------------------------------
int main() {
    char line[MAX_LINE], idbuf[MAX_LINE];
    char *ids[MAX_SHOWS];
    int idCount = 0;

    // 1) lê IDs até "FIM"
    while (fgets(line, sizeof(line), stdin)) {
        line[strcspn(line,"\n")] = '\0';
        trim(line);
        if (strcmp(line,"FIM")==0) break;
        ids[idCount++] = dupstr(line);
    }

    // 2) carrega CSV filtrando por ID
    Show arr[MAX_SHOWS];
    int showCount = 0;
    FILE *f = fopen("/tmp/disneyplus.csv","r");
    if (!f) { perror("fopen"); return 1; }
    fgets(line, sizeof(line), f); // pula cabeçalho

    while (fgets(line, sizeof(line), f)) {
        line[strcspn(line,"\n")] = '\0';
        // extrai showId (até vírgula, respeitando aspas)
        int inQ=0, bi=0;
        for (int i=0; line[i] && line[i]!='\n'; i++) {
            char c = line[i];
            if (c=='"') inQ = !inQ;
            else if (c==',' && !inQ) break;
            else idbuf[bi++] = c;
        }
        idbuf[bi] = '\0';
        trim(idbuf);
        // se ID estiver na lista, parseia
        for (int k=0;k<idCount;k++) {
            if (strcmp(idbuf, ids[k])==0) {
                parse_show(&arr[showCount++], line);
                break;
            }
        }
    }
    fclose(f);

    // 3) cria vetor de ponteiros e ordena
    Show *vet[MAX_SHOWS];
    for (int i = 0; i < showCount; i++) vet[i] = &arr[i];

    clock_t t0 = clock();
    heap_sort(vet, showCount);
    double tempo = (double)(clock()-t0)/CLOCKS_PER_SEC;

    // 4) imprime só os 10 primeiros
    int toPrint = showCount < 10 ? showCount : 10;
    for (int i = 0; i < toPrint; i++) {
        print_show(vet[i]);
    }

    // 5) grava log
    FILE *log = fopen("874422_heapsort.txt","w");
    fprintf(log, "874422\t%d\t%d\t%.6f\n",
            comparacoes, movimentacoes, tempo);
    fclose(log);

    // libera IDs
    for (int i=0;i<idCount;i++) free(ids[i]);
    return 0;
}
