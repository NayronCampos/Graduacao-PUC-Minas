#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_REG 10000
#define MAX_LIN 10000

typedef struct {
    char *showId, *type, *title, *director;
    char **cast;    int castSize;
    char *country, *dateAdded;
    int day, month, year;
    int releaseYear;
    char *rating, *duration;
    char **listedIn; int listedSize;
} Show;

int comparacoes = 0, movimentacoes = 0;

char *trim(char *s) {
    char *end;
    while (isspace((unsigned char)*s)) s++;
    if (*s=='\0') return s;
    end = s + strlen(s) - 1;
    while (end > s && isspace((unsigned char)*end)) end--;
    *(end+1) = '\0';
    return s;
}

char *dupstr(const char *s) {
    char *d = malloc(strlen(s)+1);
    return d? strcpy(d,s) : NULL;
}

int monthNum(const char *m) {
    static const char *meses[] = {
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    };
    for (int i=0; i<12; i++)
        if (strcmp(m, meses[i])==0) return i+1;
    return 0;
}

void splitList(char *f, char ***out, int *sz) {
    *sz = 0;
    if (!f || !*f || strcmp(f,"NaN")==0) { *out = NULL; return; }
    char *cpy = dupstr(f), *tok = strtok(cpy, ",");
    int cap = 4;
    *out = malloc(cap * sizeof(char*));
    while (tok) {
        if (*sz >= cap) {
            cap *= 2;
            *out = realloc(*out, cap * sizeof(char*));
        }
        (*out)[(*sz)++] = dupstr(trim(tok));
        tok = strtok(NULL, ",");
    }
    free(cpy);
}

void readShow(Show *s, const char *line) {
    char buf[MAX_LIN], *fld[11];
    int inQ=0, bi=0, fc=0;
    for (int i=0; line[i] && line[i]!='\n'; i++) {
        char c = line[i];
        if (c=='"') inQ = !inQ;
        else if (c==',' && !inQ) {
            buf[bi]='\0';
            fld[fc++] = dupstr(trim(buf));
            bi = 0;
        } else buf[bi++] = c;
    }
    buf[bi]='\0';
    fld[fc++] = dupstr(trim(buf));

    s->showId = dupstr(fld[0]);
    s->type   = dupstr(fld[1]);
    s->title  = dupstr(fld[2]);
    s->director = dupstr(fld[3]);

    splitList(fld[4], &s->cast, &s->castSize);
    if (s->castSize>1)
        qsort(s->cast, s->castSize, sizeof(char*),
              (int(*)(const void*,const void*))strcmp);

    s->country   = dupstr(fld[5]);
    s->dateAdded = fld[6][0]? dupstr(fld[6]) : dupstr("March 1, 1900");

    { int d,y; char m[20];
      if (sscanf(s->dateAdded, "%19s %d, %d", m, &d, &y)==3) {
          s->month = monthNum(m);
          s->day   = d;
          s->year  = y;
      } else {
          s->month=3; s->day=1; s->year=1900;
      }
    }

    s->releaseYear = atoi(fld[7]);
    s->rating      = dupstr(fld[8]);
    s->duration    = dupstr(fld[9]);

    splitList(fld[10], &s->listedIn, &s->listedSize);
    if (s->listedSize>1)
        qsort(s->listedIn, s->listedSize, sizeof(char*),
              (int(*)(const void*,const void*))strcmp);

    for (int i=0; i<fc; i++) free(fld[i]);
}

void printShow(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [",
           s->showId, s->title, s->type, s->director);
           if (s->castSize > 1) {
                    qsort(
                        s->cast, s->castSize, sizeof(char*),
                        (int (*)(const void*, const void*)) strcmp
                    );
                }

    for (int i=0; i<s->castSize; i++) {
        printf("%s", s->cast[i]);
        if (i<s->castSize-1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
           s->country, s->dateAdded, s->releaseYear,
           s->rating, s->duration);

           if (s->listedSize > 1) {
            qsort(
                s->listedIn, s->listedSize, sizeof(char*),
                (int (*)(const void*, const void*)) strcmp
            );
        }
    for (int i=0; i<s->listedSize; i++) {
        printf("%s", s->listedIn[i]);
        if (i<s->listedSize-1) printf(", ");
    }
    printf("] ##\n");
}

int cmpDate(const Show *a, const Show *b) {
    if (a->year != b->year)   return a->year  - b->year;
    if (a->month!= b->month)  return a->month - b->month;
    if (a->day  != b->day)    return a->day   - b->day;
    return strcmp(a->title, b->title);
}

void bubbleSort(Show v[], int n) {
    for (int i=0; i<n-1; i++) {
        for (int j=0; j<n-1-i; j++) {
            comparacoes++;
            if (cmpDate(&v[j], &v[j+1]) > 0) {
                Show tmp = v[j];
                v[j]   = v[j+1];
                v[j+1] = tmp;
                movimentacoes++;
            }
        }
    }
}

int main() {
    char buf[MAX_LIN], idb[MAX_LIN];
    char *ids[MAX_REG]; int idc=0;
    while (fgets(buf,MAX_LIN,stdin)) {
        buf[strcspn(buf,"\n")]=0;
        if (!strcmp(buf,"FIM")) break;
        ids[idc++] = dupstr(buf);
    }

    Show arr[MAX_REG]; int n=0;
    FILE *f = fopen("/tmp/disneyplus.csv","r");
    fgets(buf,MAX_LIN,f);
    while (fgets(buf,MAX_LIN,f)) {
        buf[strcspn(buf,"\n")]=0;
        int inQ=0, bi=0;
        for (int k=0; buf[k] && buf[k]!='\n'; k++) {
            char c=buf[k];
            if (c=='"') inQ=!inQ;
            else if (c==',' && !inQ) break;
            else idb[bi++]=c;
        }
        idb[bi]=0; trim(idb);
        for (int k=0; k<idc; k++){
            if (!strcmp(idb, ids[k])) {
                readShow(&arr[n++], buf);
                break;
            }
        }
    }
    fclose(f);

    clock_t t0 = clock();
    if (n>0) bubbleSort(arr, n);
    double t = (double)(clock() - t0)/CLOCKS_PER_SEC;

    for (int i=0; i<n; i++) printShow(&arr[i]);

    FILE *log = fopen("874422_bolha.txt","w");
    fprintf(log, "874422\t%d\t%d\t%.6f\n",
            comparacoes, movimentacoes, t);
    fclose(log);

    for (int i=0; i<idc; i++) free(ids[i]);
    return 0;
}
