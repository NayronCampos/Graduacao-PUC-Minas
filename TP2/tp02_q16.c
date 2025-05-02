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


static void split_and_sort(char *field, char ***out, int *outSize) {
    *outSize = 0;
    if (!field || !*field || strcmp(field, "NaN") == 0) {
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
    // assign
    s->showId      = dupstr(fields[0]);
    s->type        = dupstr(fields[1]);
    s->title       = dupstr(fields[2]);
    s->director    = dupstr(fields[3]);
    split_and_sort(fields[4], &s->cast,     &s->castSize);
    s->country     = dupstr(fields[5]);
    s->dateAdded   = fields[6][0] ? dupstr(fields[6])
                                   : dupstr("March 1, 1900");
    s->releaseYear = atoi(fields[7]);
    s->rating      = dupstr(fields[8]);
    s->duration    = dupstr(fields[9]);
    split_and_sort(fields[10], &s->listedIn, &s->listedSize);

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


static int comparacoes = 0, movimentacoes = 0;


static int cmp_show(const Show *a, const Show *b) {
    int c = strcmp(a->type, b->type);
    if (c != 0) return c;
    return strcmp(a->title, b->title);
}

// Algoritmo ordenacao
static void insertion_sort(Show *v, int n) {
    for (int i = 1; i < n; i++) {
        Show tmp = v[i];
        int j = i - 1;
        while (j >= 0) {
            comparacoes++;
            if (cmp_show(&v[j], &tmp) > 0) {
                v[j+1] = v[j];
                movimentacoes++;
                j--;
            } else {
                break;
            }
        }
        v[j+1] = tmp;
        movimentacoes++;
    }
}

int main() {
    char line[MAX_LINE], idbuf[MAX_LINE];
    char *ids[MAX_SHOWS];
    int idCount = 0;

    while (fgets(line, sizeof(line), stdin)) {
        line[strcspn(line, "\n")] = '\0';
        trim(line);
        if (strcmp(line, "FIM") == 0) break;
        ids[idCount++] = dupstr(line);
    }

    Show shows[MAX_SHOWS];
    int showCount = 0;

    // abrir o CSV
    FILE *f = fopen("/tmp/disneyplus.csv", "r");
    if (!f) { perror("fopen"); return 1; }
    fgets(line, sizeof(line), f);  


    while (fgets(line, sizeof(line), f)) {
        line[strcspn(line, "\n")] = '\0';
        int inQ = 0, bi = 0;
        for (int i = 0; line[i] && line[i] != '\n'; i++) {
            char c = line[i];
            if (c == '"') inQ = !inQ;
            else if (c == ',' && !inQ) break;
            else idbuf[bi++] = c;
        }
        idbuf[bi] = '\0';
        trim(idbuf);
        for (int k = 0; k < idCount; k++) {
            if (strcmp(idbuf, ids[k]) == 0) {
                parse_show(&shows[showCount++], line);
                break;
            }
        }
    }
    fclose(f);


    clock_t t0 = clock();
    insertion_sort(shows, showCount);
    double t = (double)(clock() - t0) / CLOCKS_PER_SEC;

    int toPrint = showCount < 10 ? showCount : 10;
    for (int i = 0; i < toPrint; i++) {
        print_show(&shows[i]);
    }

   
    FILE *log = fopen("874422_insercao.txt", "w");
    fprintf(log, "874422\t%d\t%d\t%.6f\n",
            comparacoes, movimentacoes, t);
    fclose(log);

    
    for (int i = 0; i < idCount; i++) free(ids[i]);
    return 0;
}
