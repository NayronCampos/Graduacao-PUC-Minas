

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_RECORDS       10000
#define MAX_LINE          10000
#define MAX_INPUT_LINES   20000

typedef struct {
    char **tokens;
    int size;
} StringArray;

typedef struct {
    char *showId;
    char *type;
    char *title;
    char *director;
    StringArray cast;
    char *country;
    char *dateAdded;
    int releaseYear;
    char *rating;
    char *duration;
    StringArray listedIn;
} Show;

char *trim(char *s) {
    char *end;
    while (isspace((unsigned char)*s)) s++;
    if (*s == '\0') return s;
    end = s + strlen(s) - 1;
    while (end > s && isspace((unsigned char)*end)) end--;
    *(end + 1) = '\0';
    return s;
}

char *dupString(const char *s) {
    char *d = malloc(strlen(s) + 1);
    return d ? strcpy(d, s) : NULL;
}

int cmpStr(const void *a, const void *b) {
    return strcmp(*(char**)a, *(char**)b);
}

StringArray split_and_sort(const char *campo) {
    StringArray res = {NULL, 0};
    if (!campo || strcmp(campo, "NaN") == 0) return res;
    char *cpy = dupString(campo);
    int cap = 8;
    res.tokens = malloc(cap * sizeof(char*));
    char *tok = strtok(cpy, ",");
    while (tok) {
        char *t = dupString(trim(tok));
        if (res.size >= cap) {
            cap *= 2;
            res.tokens = realloc(res.tokens, cap * sizeof(char*));
        }
        res.tokens[res.size++] = t;
        tok = strtok(NULL, ",");
    }
    if (res.size > 1) qsort(res.tokens, res.size, sizeof(char*), cmpStr);
    free(cpy);
    return res;
}

char **CSVLine(const char *line, int *outCount) {
    char buf[MAX_LINE];
    int inQ = 0, bi = 0, cap = 8, cnt = 0;
    char **fields = malloc(cap * sizeof(char*));
    for (int i = 0; line[i] && line[i] != '\n'; i++) {
        char c = line[i];
        if (c == '"') inQ = !inQ;
        else if (c == ',' && !inQ) {
            buf[bi] = '\0';
            if (cnt >= cap) {
                cap *= 2;
                fields = realloc(fields, cap * sizeof(char*));
            }
            fields[cnt++] = dupString(trim(buf));
            bi = 0;
        } else {
            buf[bi++] = c;
        }
    }
    buf[bi] = '\0';
    if (cnt >= cap) {
        cap *= 2;
        fields = realloc(fields, cap * sizeof(char*));
    }
    fields[cnt++] = dupString(trim(buf));
    *outCount = cnt;
    return fields;
}

char *getOrNaN(char **fld, int count, int idx) {
    if (idx >= count || strlen(fld[idx]) == 0) return dupString("NaN");
    return dupString(fld[idx]);
}

void lerShow(Show *s, const char *linha) {
    int fcnt;
    char **fld = CSVLine(linha, &fcnt);
    s->showId      = getOrNaN(fld, fcnt, 0);
    s->type        = getOrNaN(fld, fcnt, 1);
    s->title       = getOrNaN(fld, fcnt, 2);
    s->director    = getOrNaN(fld, fcnt, 3);
    s->cast        = split_and_sort(getOrNaN(fld, fcnt, 4));
    s->country     = getOrNaN(fld, fcnt, 5);
    s->dateAdded   = (fcnt > 6 && strlen(fld[6])>0) ? dupString(fld[6]) : dupString("March 1, 1900");
    s->releaseYear = atoi(getOrNaN(fld, fcnt, 7));
    s->rating      = getOrNaN(fld, fcnt, 8);
    s->duration    = getOrNaN(fld, fcnt, 9);
    s->listedIn    = split_and_sort(getOrNaN(fld, fcnt, 10));
    for (int i = 0; i < fcnt; i++) free(fld[i]);
    free(fld);
}

void limpa(Show *s) {
    free(s->showId); free(s->type); free(s->title);
    free(s->director); free(s->country); free(s->dateAdded);
    free(s->rating); free(s->duration);
    for (int i = 0; i < s->cast.size; i++) free(s->cast.tokens[i]);
    free(s->cast.tokens);
    for (int i = 0; i < s->listedIn.size; i++) free(s->listedIn.tokens[i]);
    free(s->listedIn.tokens);
}

void imprimirShow(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->showId, s->title, s->type, s->director);
    for (int i = 0; i < s->cast.size; i++) {
        printf("%s", s->cast.tokens[i]);
        if (i < s->cast.size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
        s->country, s->dateAdded, s->releaseYear, s->rating, s->duration);
    for (int i = 0; i < s->listedIn.size; i++) {
        printf("%s", s->listedIn.tokens[i]);
        if (i < s->listedIn.size - 1) printf(", ");
    }
    printf("]\n");
}

int comparacoes = 0;
int movimentacoes = 0;

int comparacao(const Show *a, const Show *b) {
    int cmp = strcmp(a->type, b->type);
    if (cmp != 0) return cmp;
    return strcmp(a->title, b->title);
}

void shellSort(Show arr[], int n) {
    for (int gap = n/2; gap > 0; gap /= 2) {
        for (int i = gap; i < n; i++) {
            Show temp = arr[i];
            int j = i;
            while (j >= gap && (++comparacoes, comparacao(&arr[j-gap], &temp) > 0)) {
                arr[j] = arr[j-gap];
                movimentacoes++;
                j -= gap;
            }
            arr[j] = temp;
            movimentacoes++;
        }
    }
}

int main() {
    char *input[MAX_INPUT_LINES];
    int L = 0;
    char buf[MAX_LINE];
    while (fgets(buf, MAX_LINE, stdin)) {
        buf[strcspn(buf, "\n")] = '\0';
        input[L++] = dupString(buf);
    }
    int p1 = 0;
    while (p1 < L && strcmp(input[p1], "FIM") != 0) p1++;
    int idCount = p1;
    char **ids = malloc(idCount * sizeof(char*));
    for (int i = 0; i < idCount; i++) ids[i] = dupString(input[i]);

    Show shows[MAX_RECORDS];
    int showCount = 0;
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    fgets(buf, MAX_LINE, fp);
    while (fgets(buf, MAX_LINE, fp)) {
        buf[strcspn(buf, "\n")] = '\0';
        char idBuf[MAX_LINE];
        int inQ = 0, bi = 0;
        for (int i = 0; buf[i] && buf[i] != '\n'; i++) {
            char c = buf[i];
            if (c == '"') inQ = !inQ;
            else if (c == ',' && !inQ) break;
            else idBuf[bi++] = c;
        }
        idBuf[bi] = '\0';
        trim(idBuf);
        for (int i = 0; i < idCount; i++) {
            if (strcmp(idBuf, ids[i]) == 0) {
                lerShow(&shows[showCount++], buf);
                break;
            }
        }
    }
    fclose(fp);

    int n = showCount;
    clock_t t0 = clock();
    shellSort(shows, n);
    double tempo = (double)(clock() - t0) / CLOCKS_PER_SEC;
    for (int i = 0; i < n; i++) imprimirShow(&shows[i]);
    FILE *log = fopen("874422 shellsort.txt", "w");
    fprintf(log, "874422\t%d\t%d\t%.6f\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    for (int i = 0; i < showCount; i++) limpa(&shows[i]);
    for (int i = 0; i < idCount; i++) free(ids[i]);
    for (int i = 0; i < L; i++) free(input[i]);
    free(ids);
    return 0;
}
