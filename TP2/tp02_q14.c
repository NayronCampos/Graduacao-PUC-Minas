#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

typedef struct {
    char *id, *tipo, *titulo, *diretor;
    char **elenco;     int qtElenco;
    char *pais;
    struct tm dataAdd;
    int anoLan;
    char *classif, *duracao;
    char **cats;       int qtCats;
} Show;

long long movimentos = 0;

static char *trim(char *s) {
    char *end;
    while (isspace((unsigned char)*s)) s++;
    end = s + strlen(s) - 1;
    while (end > s && isspace((unsigned char)*end)) *end-- = 0;
    return s;
}

static char *dupe(const char *s) {
    if (!s || !*s) s = "NaN";
    char *r = malloc(strlen(s)+1);
    return r? strcpy(r,s): NULL;
}

static void leData(const char *txt, struct tm *t) {
    static char *mnames[] = {
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    };
    char m[20]; int d,y;
    if (sscanf(txt,"%19s %d, %d", m,&d,&y)==3) {
        t->tm_mday=d; t->tm_year=y-1900;
        for(int i=0;i<12;i++) if(!strcmp(m,mnames[i])){t->tm_mon=i;break;}
    } else leData("March 1, 1900", t);
}

static int cmpstr(const void *a,const void *b){ return strcmp(*(char*const*)a,*(char*const*)b); }
static void splitSort(const char *f, char ***out,int *n){
    *n=0; if(!f||!*f||!strcmp(f,"NaN")){*out=NULL;return;}
    char *c=strdup(f), *tok=strtok(c,",");
    int cap=4; *out=malloc(cap*sizeof(char*));
    while(tok){
        if(*n>=cap)*out=realloc(*out,sizeof(char*)*(cap*=2));
        (*out)[(*n)++]=strdup(trim(tok));
        tok=strtok(NULL,",");
    }
    free(c);
    if(*n>1) qsort(*out,*n,sizeof(char*),cmpstr);
}

static Show *parseCad(const char *l){
    char buf[10000], *f[11]; int inQ=0, bi=0, fi=0;
    for(int i=0;l[i]&&l[i]!='\n';i++){
        char c=l[i];
        if(c=='"') inQ=!inQ;
        else if(c==','&&!inQ){ buf[bi]=0; f[fi++]=strdup(trim(buf)); bi=0; }
        else buf[bi++]=c;
    }
    buf[bi]=0; f[fi++]=strdup(trim(buf));
    Show *s=calloc(1,sizeof(*s));
    s->id    =dupe(f[0]);
    s->tipo  =dupe(f[1]);
    s->titulo=dupe(f[2]);
    s->diretor=dupe(f[3]);
    splitSort(f[4],&s->elenco,&s->qtElenco);
    s->pais =dupe(f[5]);
    leData(f[6][0]?f[6]:"", &s->dataAdd);
    s->anoLan=atoi(f[7]);
    s->classif=dupe(f[8]);
    s->duracao=dupe(f[9]);
    splitSort(f[10],&s->cats,&s->qtCats);
    for(int i=0;i<fi;i++) free(f[i]);
    return s;
}

static void imprime(Show *s){
    char dt[30], mo[10];
    strftime(mo,10,"%B",&s->dataAdd);
    sprintf(dt,"%s %d, %d", mo, s->dataAdd.tm_mday, s->dataAdd.tm_year+1900);
    printf("=> %s ## %s ## %s ## %s ## [", s->id,s->titulo,s->tipo,s->diretor);
    for(int i=0;i<s->qtElenco;i++) printf("%s%s", s->elenco[i], i+1<s->qtElenco?", ":"");
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->pais,dt,s->anoLan,s->classif,s->duracao);
    for(int i=0;i<s->qtCats;i++) printf("%s%s", s->cats[i], i+1<s->qtCats?", ":"");
    printf("] ##\n");
}

static void libera(Show *s){
    free(s->id); free(s->tipo); free(s->titulo); free(s->diretor);
    for(int i=0;i<s->qtElenco;i++) free(s->elenco[i]); free(s->elenco);
    free(s->pais); free(s->classif); free(s->duracao);
    for(int i=0;i<s->qtCats;i++) free(s->cats[i]); free(s->cats);
    free(s);
}

static int dig(int x,int e){return(x/e)%10;}
static void cntDig(Show **v,int N,int e){
    Show **o=malloc(N*sizeof*o);
    int c[10]={0};
    for(int i=0;i<N;i++) c[dig(v[i]->anoLan,e)]++;
    for(int i=1;i<10;i++) c[i]+=c[i-1];
    for(int i=N-1;i>=0;i--){
        int d=dig(v[i]->anoLan,e);
        o[--c[d]] = v[i];
        movimentos++;
    }
    for(int i=0;i<N;i++){ v[i]=o[i]; movimentos++; }
    free(o);
}
static void radix(Show **v,int N){

    for(int i=1;i<N;i++){
        Show *k=v[i]; int j=i-1;
        while(j>=0 && strcmp(v[j]->titulo,k->titulo)>0){v[j+1]=v[j];j--;}
        v[j+1]=k;
    }
    int mx=0; for(int i=0;i<N;i++) if(v[i]->anoLan>mx)mx=v[i]->anoLan;
    for(int e=1;mx/e>0;e*=10) cntDig(v,N,e);
}

int main(){
    FILE *f=fopen("/tmp/disneyplus.csv","r");
    char l[10000];
    fgets(l,sizeof l,f); // cabeçalho
    Show **all=NULL; int tot=0,cap=8;
    all=malloc(cap*sizeof*all);
    while(fgets(l,sizeof l,f)){
        if(tot>=cap) all=realloc(all, (cap*=2)*sizeof*all);
        all[tot++]=parseCad(l);
    }
    fclose(f);
    //lendo os ids 
    Show **sub=NULL; int subc=0; cap=8;
    sub=malloc(cap*sizeof*sub);
    char idb[256];
    while(fgets(idb,sizeof idb,stdin)){
        idb[strcspn(idb,"\r\n")]=0;
        if(!strcmp(idb,"FIM"))break;
        for(int i=0;i<tot;i++){
            if(!strcmp(all[i]->id,idb)){
                if(subc>=cap) sub=realloc(sub,(cap*=2)*sizeof*sub);
                sub[subc++]=all[i];
                break;
            }
        }
    }

    clock_t t0=clock();
    radix(sub,subc);
    double ms=(double)(clock()-t0)*1000/CLOCKS_PER_SEC;
    for(int i=0;i<subc;i++) imprime(sub[i]);

    FILE *lg=fopen("874422_radixsort.txt","w");
    if(lg) { fprintf(lg,"874422\t%.3f\t%lld\n",ms,movimentos); fclose(lg); }

    free(sub);
    for(int i=0;i<tot;i++) libera(all[i]);
    free(all);
    return 0;
}
