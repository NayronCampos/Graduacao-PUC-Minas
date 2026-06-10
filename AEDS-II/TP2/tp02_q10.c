#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_REGISTROS 10000
#define MAX_LINHA     10000

typedef struct {
    char *showId;
    char *type;
    char *title;
    char *director;
    char **cast;
    int castSize;
    char *country;
    char *dateAdded;
    int day, month, year;
    int releaseYear;
    char *rating;
    char *duration;
    char **listedIn;
    int listedSize;
} Show;

int comparacoes = 0, movimentacoes = 0;

char *aparar(char *s) {
    while (isspace((unsigned char)*s)) s++;
    if (*s == '\0') return s;
    char *fim = s + strlen(s) - 1;
    while (fim > s && isspace((unsigned char)*fim)) fim--;
    *(fim+1) = '\0';
    return s;
}

char *duplicar(const char *s) {
    char *d = malloc(strlen(s) + 1);
    return d ? strcpy(d, s) : NULL;
}

int numMes(const char *m) {
    if (!strcmp(m,"January")) return 1;
    if (!strcmp(m,"February")) return 2;
    if (!strcmp(m,"March")) return 3;
    if (!strcmp(m,"April")) return 4;
    if (!strcmp(m,"May")) return 5;
    if (!strcmp(m,"June")) return 6;
    if (!strcmp(m,"July")) return 7;
    if (!strcmp(m,"August")) return 8;
    if (!strcmp(m,"September")) return 9;
    if (!strcmp(m,"October")) return 10;
    if (!strcmp(m,"November")) return 11;
    if (!strcmp(m,"December")) return 12;
    return 0;
}

void dividirLista(char *campo, char ***arr, int *tam) {
    *tam = 0;
    if (!campo || !strcmp(campo,"NaN")) { *arr = NULL; return; }
    char *copia = duplicar(campo), *tok;
    int cap = 4;
    *arr = malloc(cap * sizeof(char*));
    tok = strtok(copia, ",");
    while (tok) {
        if (*tam >= cap) {
            cap *= 2;
            *arr = realloc(*arr, cap * sizeof(char*));
        }
        (*arr)[(*tam)++] = duplicar(aparar(tok));
        tok = strtok(NULL, ",");
    }
    free(copia);
}

// lendo CSV
void lerRegistro(Show *s, const char *linha) {
    char buf[MAX_LINHA], *fields[11];
    int inQ=0, bi=0, f=0;
    for (int i=0; linha[i] && linha[i]!='\n'; i++) {
        char c = linha[i];
        if (c=='"') inQ = !inQ;
        else if (c==',' && !inQ) {
            buf[bi]='\0';
            fields[f++] = duplicar(aparar(buf));
            bi=0;
        } else buf[bi++]=c;
    }
    buf[bi]='\0'; fields[f++]=duplicar(aparar(buf));
    s->showId     = duplicar(fields[0]);
    s->type       = duplicar(fields[1]);
    s->title      = duplicar(fields[2]);
    s->director   = duplicar(fields[3]);
    dividirLista(fields[4], &s->cast, &s->castSize);
    s->country    = duplicar(fields[5]);
    s->dateAdded  = !strcmp(fields[6],"") ? duplicar("March 1, 1900") : duplicar(fields[6]);
    {
        char m[20]; int d,y;
        if (sscanf(s->dateAdded,"%19s %d, %d",m,&d,&y)==3) {
            s->month = numMes(m);
            s->day   = d;
            s->year  = y;
        } else {
            s->month=3; s->day=1; s->year=1900;
        }
    }
    s->releaseYear = atoi(fields[7]);
    s->rating      = duplicar(fields[8]);
    s->duration    = duplicar(fields[9]);
    dividirLista(fields[10], &s->listedIn, &s->listedSize);
    for (int i=0; i<f; i++) free(fields[i]);
}

// imprime 
void mostrarRegistro(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->showId, s->title, s->type, s->director);
    for (int i=0; i<s->castSize; i++) {
        printf("%s", s->cast[i]);
        if (i<s->castSize-1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
        s->country, s->dateAdded, s->releaseYear, s->rating, s->duration);
    for (int i=0; i<s->listedSize; i++) {
        printf("%s", s->listedIn[i]);
        if (i<s->listedSize-1) printf(", ");
    }
    printf("]\n");
}

// compara 
int compararData(const Show *a, const Show *b) {
    if (a->year!=b->year)      return a->year  - b->year;
    if (a->month!=b->month)    return a->month - b->month;
    if (a->day!=b->day)        return a->day   - b->day;
    return strcmp(a->title,b->title);
}

// quicksort principal
void ordenarRapido(Show *v, int l, int r) {
    int i=l, j=r;
    Show piv = v[(l+r)/2];
    while (i<=j) {
        while (++comparacoes && compararData(&v[i],&piv)<0) i++;
        while (++comparacoes && compararData(&v[j],&piv)>0) j--;
        if (i<=j) {
            Show tmp=v[i]; v[i]=v[j]; v[j]=tmp;
            movimentacoes++;
            i++; j--;
        }
    }
    if (l<j) ordenarRapido(v,l,j);
    if (i<r) ordenarRapido(v,i,r);
}

int main() {
    char buf[MAX_LINHA], idbuf[MAX_LINHA];
    char *ids[MAX_REGISTROS]; int idc=0;
    while (fgets(buf,MAX_LINHA,stdin)) {
        buf[strcspn(buf,"\n")]=0;
        if (!strcmp(buf,"FIM")) break;
        ids[idc++]=duplicar(buf);
    }
    Show arr[MAX_REGISTROS]; int n=0;
    FILE *fp = fopen("/tmp/disneyplus.csv","r");
    fgets(buf,MAX_LINHA,fp);
    while (fgets(buf,MAX_LINHA,fp)) {
        buf[strcspn(buf,"\n")]=0;
        int inQ=0, bi=0;
        for (int k=0; buf[k] && buf[k]!='\n'; k++) {
            char c=buf[k];
            if (c=='"') inQ=!inQ;
            else if (c==',' && !inQ) break;
            else idbuf[bi++]=c;
        }
        idbuf[bi]=0; aparar(idbuf);
        for (int k=0; k<idc; k++) {
            if (!strcmp(idbuf,ids[k])) {
                lerRegistro(&arr[n++], buf);
                break;
            }
        }
    }
    fclose(fp);
    clock_t t0 = clock();
    if (n>0) ordenarRapido(arr,0,n-1);
    double tempo = (double)(clock()-t0)/CLOCKS_PER_SEC;
    for (int i=0; i<n; i++) mostrarRegistro(&arr[i]);
    FILE *log = fopen("874422_quicksort.txt","w");
    fprintf(log,"874422\t%d\t%d\t%.6f\n",comparacoes,movimentacoes,tempo);
    fclose(log);
    for (int i=0;i<idc;i++) free(ids[i]);
    return 0;
}
