#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_BASE 10000
#define MAX_LIST 1000
#define MAX_LINE 4096

typedef struct {
    char id[32];
    char type[64];
    char title[256];
    char director[256];
    char cast[512];
    char country[128];
    char dateAdded[64];
    int releaseYear;
    char rating[32];
    char duration[64];
    char listedIn[512];
} Show;

static Show base[MAX_BASE];
static int baseCount = 0;

static Show lista[MAX_LIST];
static int listCount = 0;

char* trim(char *str) {
    while (*str == ' ') str++;
    char *end = str + strlen(str) - 1;
    while (end > str && *end == ' ') end--;
    *(end + 1) = '\0';
    return str;
}

void inserirInicio(Show s) {
    for (int i = listCount; i > 0; i--) lista[i] = lista[i - 1];
    lista[0] = s;
    listCount++;
}

void inserirFim(Show s) {
    lista[listCount++] = s;
}

void inserirPos(Show s, int pos) {
    for (int i = listCount; i > pos; i--) lista[i] = lista[i - 1];
    lista[pos] = s;
    listCount++;
}

Show removerInicio() {
    Show tmp = lista[0];
    for (int i = 0; i < listCount - 1; i++) lista[i] = lista[i + 1];
    listCount--;
    return tmp;
}

Show removerFim() {
    return lista[--listCount];
}

Show removerPos(int pos) {
    Show tmp = lista[pos];
    for (int i = pos; i < listCount - 1; i++) lista[i] = lista[i + 1];
    listCount--;
    return tmp;
}

void imprimirListaFormatada(const char *str) {
    char buffer[512];
    strncpy(buffer, str, sizeof(buffer));
    char *token = strtok(buffer, ",");
    bool primeiro = true;
    while (token != NULL) {
        if (!primeiro) printf(", ");
        printf("%s", trim(token));
        primeiro = false;
        token = strtok(NULL, ",");
    }
}

void imprimirShow(const Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->id, s->title, s->type, s->director);
    imprimirListaFormatada(s->cast);
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", 
           s->country, s->dateAdded, s->releaseYear, s->rating, s->duration);
    imprimirListaFormatada(s->listedIn);
    printf("] ##\n");
}

Show* buscarBase(const char *id) {
    for (int i = 0; i < baseCount; i++) {
        if (strcmp(base[i].id, id) == 0) return &base[i];
    }
    return NULL;
}

void parseCSV(const char *linha, Show *s) {
    char field[11][512] = {0};
    int f = 0, p = 0;
    bool inQuotes = false;

    for (int i = 0; linha[i] != '\0' && f < 11; i++) {
        char c = linha[i];
        if (c == '"') {
            inQuotes = !inQuotes;
        } else if (c == ',' && !inQuotes) {
            field[f][p] = '\0';
            f++; p = 0;
        } else if (c != '\r' && c != '\n') {
            if (p < 511) field[f][p++] = c;
        }
    }
    field[f][p] = '\0';

    #define SET_OR_DEFAULT(dest, src, fallback) strncpy(dest, (src[0] ? src : fallback), sizeof(dest))

    SET_OR_DEFAULT(s->id,       field[0], "NaN");
    SET_OR_DEFAULT(s->type,     field[1], "NaN");
    SET_OR_DEFAULT(s->title,    field[2], "NaN");
    SET_OR_DEFAULT(s->director, field[3], "NaN");
    SET_OR_DEFAULT(s->cast,     field[4], "NaN");
    SET_OR_DEFAULT(s->country,  field[5], "NaN");
    SET_OR_DEFAULT(s->dateAdded,field[6], "March 1, 1900");
    s->releaseYear = (atoi(field[7]) >= 1900 && atoi(field[7]) <= 2030) ? atoi(field[7]) : 0;
    SET_OR_DEFAULT(s->rating,   field[8], "NaN");
    SET_OR_DEFAULT(s->duration, field[9], "NaN");
    SET_OR_DEFAULT(s->listedIn, field[10], "NaN");

    #undef SET_OR_DEFAULT
}

int carregarBase(const char *path) {
    FILE *fp = fopen(path, "r");
    if (!fp) return 0;

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, fp); // Cabeçalho

    while (fgets(linha, MAX_LINE, fp)) {
        if (linha[0] == '\n') continue;
        parseCSV(linha, &base[baseCount++]);
    }

    fclose(fp);
    return baseCount;
}

int main() {
    if (!carregarBase("/tmp/disneyplus.csv")) return 1;

    char id[32];
    while (scanf("%s", id) == 1 && strcmp(id, "FIM") != 0) {
        Show *sp = buscarBase(id);
        if (sp) inserirFim(*sp);
    }

    int m; scanf("%d", &m);
    for (int i = 0; i < m; i++) {
        char cmd[4];
        scanf("%s", cmd);

        if (strcmp(cmd, "II") == 0) {
            scanf("%s", id);
            Show *sp = buscarBase(id);
            if (sp) inserirInicio(*sp);

        } else if (strcmp(cmd, "IF") == 0) {
            scanf("%s", id);
            Show *sp = buscarBase(id);
            if (sp) inserirFim(*sp);

        } else if (strcmp(cmd, "I*") == 0) {
            int pos; scanf("%d %s", &pos, id);
            Show *sp = buscarBase(id);
            if (sp && pos <= listCount) inserirPos(*sp, pos);

        } else if (strcmp(cmd, "RI") == 0) {
            Show r = removerInicio();
            printf("(R) %s\n", r.title);

        } else if (strcmp(cmd, "RF") == 0) {
            Show r = removerFim();
            printf("(R) %s\n", r.title);

        } else if (strcmp(cmd, "R*") == 0) {
            int pos; scanf("%d", &pos);
            Show r = removerPos(pos);
            printf("(R) %s\n", r.title);
        }
    }

    for (int i = 0; i < listCount; i++) {
        imprimirShow(&lista[i]);
    }

    return 0;
}
