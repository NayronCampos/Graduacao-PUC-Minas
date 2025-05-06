#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <stdbool.h>

#define MAX_LINE 10000
#define DEFAULT_STR "NaN"
#define DEFAULT_DATE_STR "March 1, 1900"

// --- Estrutura Show ---
typedef struct {
    char    *showId,
            *type,
            *title,
            *director;
    char   **cast;    size_t castSize;
    char    *country;
    struct tm dateAdded;
    int      releaseYear;
    char    *rating,
            *duration;
    char   **listedIn; size_t listedSize;
} Show;

// --- Helpers ---
// Checa malloc/strdup
static void *must_alloc(void *p) {
    if (!p) { perror("malloc/strdup"); exit(EXIT_FAILURE); }
    return p;
}
// Trim genérico
static char *trim(char *s) {
    char *end;
    while (isspace((unsigned char)*s)) s++;
    if (!*s) return s;
    end = s + strlen(s) - 1;
    while (end > s && isspace((unsigned char)*end)) end--;
    *(end+1) = '\0';
    return s;
}
// split CSV, respeita aspas, faz trim e retorna vetor
static char **split_csv_line(const char *line, size_t *outCount) {
    size_t cap = 8, cnt = 0;
    char **arr = must_alloc(malloc(cap * sizeof(char*)));
    char buf[MAX_LINE];
    bool inQ = false;
    size_t bi = 0;
    for (const char *p = line; *p && *p!='\n'; ++p) {
        if (*p == '"') {
            inQ = !inQ;
        } else if (*p == ',' && !inQ) {
            buf[bi] = '\0';
            char *tok = must_alloc(strdup(buf));
            arr[cnt++] = trim(tok);
            bi = 0;
            if (cnt >= cap) arr = must_alloc(realloc(arr, (cap*=2)*sizeof(char*)));
        } else {
            buf[bi++] = *p;
        }
    }
    buf[bi] = '\0';
    char *tok = must_alloc(strdup(buf));
    arr[cnt++] = trim(tok);
    *outCount = cnt;
    return arr;
}

// date parsing simplificado (versão do exemplo)
static char *my_strptime(const char *s, const char *fmt, struct tm *tm) {
    // implementa apenas "%B %d, %Y" e DEFAULT_DATE_STR
    if (strcmp(fmt, "%B %d, %Y")==0) {
        const char *meses[] = {"January","February","March","April","May","June",
                               "July","August","September","October","November","December"};
        char m[20]; int d,y;
        if (sscanf(s, "%19s %d, %d", m,&d,&y)!=3) return NULL;
        int im;
        for (im=0; im<12; im++) if (!strcmp(m,meses[im])) break;
        if (im==12) return NULL;
        tm->tm_mon  = im;
        tm->tm_mday = d;
        tm->tm_year = y - 1900;
        return (char*)s + strlen(s);
    }
    return NULL;
}
static void set_default_date(struct tm *tm) {
    if (!my_strptime(DEFAULT_DATE_STR, "%B %d, %Y", tm)) {
        time_t now = time(NULL);
        *tm = *localtime(&now);
    }
}

// free de Show completo
static void free_show(Show *s) {
    if (!s) return;
    free(s->showId); free(s->type); free(s->title); free(s->director);
    for (size_t i=0;i<s->castSize;i++) free(s->cast[i]);
    free(s->cast);
    free(s->country);
    free(s->rating); free(s->duration);
    for (size_t i=0;i<s->listedSize;i++) free(s->listedIn[i]);
    free(s->listedIn);
    free(s);
}

// parse de linha CSV em Show
static void parse_show(Show *s, const char *line) {
    size_t nFields;
    char **f = split_csv_line(line, &nFields);
    // garante ao menos 11 campos
    if (nFields < 11) {
        f = must_alloc(realloc(f, 11*sizeof(char*)));
        for (size_t i=nFields; i<11; i++)
            f[i] = must_alloc(strdup(DEFAULT_STR));
        nFields = 11;
    }
    // duplica campos principais
    s->showId      = must_alloc(strdup(f[0]));
    s->type        = must_alloc(strdup(f[1]));
    s->title       = must_alloc(strdup(f[2]));
    s->director    = must_alloc(strdup(f[3]));

    // cast
    if (!strcmp(f[4], "NaN")) {
        s->castSize = 1;
        s->cast = must_alloc(malloc(sizeof(char*)));
        s->cast[0] = must_alloc(strdup(DEFAULT_STR));
    } else {
        s->cast = split_csv_line(f[4], &s->castSize);
        qsort(s->cast, s->castSize, sizeof(char*),
              (int(*)(const void*,const void*))strcmp);
    }
    // country
    s->country = must_alloc(strdup(f[5]));

    // dateAdded
    if (!my_strptime(f[6], "%B %d, %Y", &s->dateAdded))
        set_default_date(&s->dateAdded);

    // outros simples
    s->releaseYear = atoi(f[7]);
    s->rating      = must_alloc(strdup(f[8]));
    s->duration    = must_alloc(strdup(f[9]));

    // listedIn
    if (!strcmp(f[10], "NaN")) {
        s->listedSize = 1;
        s->listedIn = must_alloc(malloc(sizeof(char*)));
        s->listedIn[0] = must_alloc(strdup(DEFAULT_STR));
    } else {
        s->listedIn = split_csv_line(f[10], &s->listedSize);
        qsort(s->listedIn, s->listedSize, sizeof(char*),
              (int(*)(const void*,const void*))strcmp);
    }

    // libera campos temporários
    for (size_t i=0;i<nFields;i++) free(f[i]);
    free(f);
}

// impressão no formato exigido
static void print_show(const Show *s) {
    char date_buf[50];
    strftime(date_buf, sizeof(date_buf), "%B %d, %Y", &s->dateAdded);
    printf("=> %s ## %s ## %s ## %s ## [",
           s->showId, s->title, s->type, s->director);
    for (size_t i=0;i<s->castSize;i++)
        printf("%s%s", s->cast[i], i+1<s->castSize?", ":"");
    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
           s->country, date_buf, s->releaseYear, s->rating, s->duration);
    for (size_t i=0;i<s->listedSize;i++)
        printf("%s%s", s->listedIn[i], i+1<s->listedSize?", ":"");
    printf("] ##\n");
}

int main(void) {
    // lê IDs
    char line[MAX_LINE];
    char *ids[10000];
    size_t nIds = 0;
    while (fgets(line,sizeof(line),stdin) && strcmp(trim(line),"FIM"))
        ids[nIds++] = must_alloc(strdup(trim(line)));

    // carrega CSV
    Show **shows = must_alloc(malloc(nIds * sizeof(Show*)));
    size_t nShows=0;
    FILE *f = fopen("disneyplus.csv","r");
    if (!f) { perror("fopen"); return EXIT_FAILURE; }
    fgets(line,sizeof(line),f); // header

    while (fgets(line,sizeof(line),f)) {
        char tmp[MAX_LINE];
        // extrai showId (até vírgula, respeitando aspas)
        bool inQ=false; size_t ti=0;
        for (char *p=line; *p && *p!='\n'; ++p) {
            if (*p=='"') inQ=!inQ;
            else if (*p==',' && !inQ) break;
            else tmp[ti++]=*p;
        }
        tmp[ti]='\0';
        trim(tmp);
        // compara e parse
        for (size_t i=0;i<nIds;i++) {
            if (!strcmp(tmp, ids[i])) {
                Show *s = must_alloc(malloc(sizeof(Show)));
                parse_show(s, line);
                shows[nShows++] = s;
                break;
            }
        }
    }
    fclose(f);

    // ordena por duração (int) e title
    qsort(shows, nShows, sizeof(Show*),
          (int(*)(const void*,const void*)) (void*) (               \
            (int (*)(const Show**,const Show**))                     \
            [](const Show **a, const Show **b){                     \
                int da=atoi((*a)->duration), db=atoi((*b)->duration);\
                return da!=db? da-db : strcmp((*a)->title,(*b)->title);\
            }));

    // imprime até 10
    size_t m = nShows<10? nShows:10;
    for (size_t i=0;i<m;i++) {
        print_show(shows[i]);
        free_show(shows[i]);
    }
    // cleanup IDs e array
    for (size_t i=0;i<nIds;i++) free(ids[i]);
    free(shows);

    return EXIT_SUCCESS;
}
