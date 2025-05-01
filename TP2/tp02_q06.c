#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_SHOWS 10000
#define MAX_TOKENS 50
#define MAX_LINE 10000

typedef struct {
    char *id, *tipo, *titulo, *diretor;
    char *elenco[MAX_TOKENS];   int qtElenco;
    char *pais, *dataAdicao;
    int   ano;
    char *classificacao, *duracao;
    char *categorias[MAX_TOKENS]; int qtCategorias;
} Show;

static Show showsTudo[MAX_SHOWS];
static int totalShows = 0;
static int contComparacoes = 0;
static int contMovimentacoes = 0;

char *dupOuNaN(const char *s) {
    return (s && *s) ? strdup(s) : strdup("NaN");
}

char *trim(char *s) {
    char *f = s + strlen(s) - 1;
    while (*s && isspace((unsigned char)*s)) s++;
    while (f > s && isspace((unsigned char)*f)) *f-- = '\0';
    return s;
}

char **lerLinhaCSV(const char *l, int *qtd) {
    char buf[MAX_LINE], **f = malloc(16*sizeof(char*));
    int inQ=0, bi=0, cap=16, cnt=0;
    for (int i=0; l[i]; i++) {
        char c=l[i];
        if (c=='"') inQ=!inQ;
        else if (c==',' && !inQ) {
            buf[bi]='\0';
            if (cnt>=cap) f=realloc(f,(cap*=2)*sizeof(char*));
            f[cnt++]=strdup(trim(buf));
            bi=0;
        } else buf[bi++]=c;
    }
    buf[bi]='\0';
    if (cnt>=cap) f=realloc(f,(cap*=2)*sizeof(char*));
    f[cnt++]=strdup(trim(buf));
    *qtd=cnt;
    return f;
}

int dividirTokens(const char *c, char *t[]) {
    if (!c||!*c||strcmp(c,"NaN")==0) return 0;
    char *cpy=strdup(c),*tok=strtok(cpy,","),cnt=0;
    while (tok && cnt<MAX_TOKENS) {
        t[cnt++]=strdup(trim(tok));
        tok=strtok(NULL,",");
    }
    free(cpy);
    return cnt;
}

void carregarCSV(const char *path) {
    FILE *f=fopen(path,"r");
    if (!f) exit(1);
    char linha[MAX_LINE];
    fgets(linha,sizeof(linha),f);
    while (fgets(linha,sizeof(linha),f)) {
        int n; char **fld=lerLinhaCSV(linha,&n);
        Show *s=&showsTudo[totalShows++];
        s->id            =dupOuNaN(fld[0]);
        s->tipo          =dupOuNaN(fld[1]);
        s->titulo        =dupOuNaN(fld[2]);
        s->diretor       =dupOuNaN(fld[3]);
        s->qtElenco      =dividirTokens(fld[4],s->elenco);
        s->pais          =dupOuNaN(fld[5]);
        s->dataAdicao    =(n>6&&*fld[6])?strdup(fld[6]):strdup("March 1, 1900");
        s->ano           =atoi(fld[7]);
        s->classificacao =dupOuNaN(fld[8]);
        s->duracao       =dupOuNaN(fld[9]);
        s->qtCategorias  =dividirTokens(fld[10],s->categorias);
        for(int i=0;i<n;i++) free(fld[i]);
        free(fld);
    }
    fclose(f);
}

int cmpStr(const void *a,const void *b){
    return strcmp(*(char**)a,*(char**)b);
}

void imprimir(const Show *s){
    if (s->qtElenco==0)
        printf("=> %s ## %s ## %s ## %s ## [NaN] ## ",
            s->id,s->titulo,s->tipo,s->diretor);
    else{
        qsort((void*)s->elenco,s->qtElenco,sizeof(char*),cmpStr);
        printf("=> %s ## %s ## %s ## %s ## [",
            s->id,s->titulo,s->tipo,s->diretor);
        for(int i=0;i<s->qtElenco;i++){
            printf("%s",s->elenco[i]);
            if(i<s->qtElenco-1) printf(", ");
        }
        printf("] ## ");
    }
    printf("%s ## %s ## %d ## %s ## %s ## [",
        s->pais,s->dataAdicao,s->ano,s->classificacao,s->duracao);
    if(s->qtCategorias>1)
        qsort((void*)s->categorias,s->qtCategorias,sizeof(char*),cmpStr);
    for(int i=0;i<s->qtCategorias;i++){
        printf("%s",s->categorias[i]);
        if(i<s->qtCategorias-1) printf(", ");
    }
    printf("] ##\n");
}

void troca(Show *v[],int a,int b){
    Show *t=v[a]; v[a]=v[b]; v[b]=t;
}

void ordenaRec(Show *v[],int i,int n){
    if(i>=n-1) return;
    int m=i;
    for(int j=i+1;j<n;j++){
        contComparacoes++;
        if(strcmp(v[j]->titulo,v[m]->titulo)<0) m=j;
    }
    if(m!=i){
        troca(v,i,m);
        contMovimentacoes++;
    }
    ordenaRec(v,i+1,n);
}

int main(){
    char *ids[MAX_SHOWS],buf[MAX_LINE];
    int qtIds=0;
    while(fgets(buf,sizeof(buf),stdin)){
        trim(buf);
        if(!strcmp(buf,"FIM"))break;
        ids[qtIds++]=strdup(buf);
    }
    carregarCSV("/tmp/disneyplus.csv");
    Show *filtrados[MAX_SHOWS]; int qtF=0;
    for(int i=0;i<totalShows;i++)
        for(int j=0;j<qtIds;j++)
            if(!strcmp(showsTudo[i].id,ids[j]))
                filtrados[qtF++]=&showsTudo[i];
    clock_t t0=clock();
    ordenaRec(filtrados,0,qtF);
    double t=(double)(clock()-t0)/CLOCKS_PER_SEC;
    for(int i=0;i<qtF;i++) imprimir(filtrados[i]);
    FILE *log=fopen("874422_selecao.txt","w");
    fprintf(log,"874422\t%d\t%d\t%.6f\n",
        contComparacoes,contMovimentacoes,t);
    fclose(log);
    return 0;
}
