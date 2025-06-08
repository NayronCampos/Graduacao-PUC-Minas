#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define LIMITE_SHOWS 10000
#define TAM_CAMPO 256
#define MAX_ATORES 50
#define MAX_GENEROS 50

typedef struct Programa {
    char codigo[16];
    char categoria[32];
    char nome[TAM_CAMPO];
    char diretor[TAM_CAMPO];
    char atores[MAX_ATORES][TAM_CAMPO];
    int qtd_atores;
    char pais[TAM_CAMPO];
    char data_add[TAM_CAMPO];
    int ano_lancamento;
    char classificacao[16];
    char duracao[32];
    char generos[MAX_GENEROS][TAM_CAMPO];
    int qtd_generos;
} Programa;

Programa banco[LIMITE_SHOWS];
int total_programas = 0;

typedef struct Celula {
    Programa prog;
    struct Celula *prox;
} Celula;

Celula *inicio = NULL;

void dividir_csv(const char *linha, char campos[][TAM_CAMPO]) {
    int f = 0, i = 0, j = 0;
    bool entre_aspas = false;
    while (linha[i] && f < 11) {
        char c = linha[i++];
        if (c == '"') {
            entre_aspas = !entre_aspas;
        } else if (!entre_aspas && c == ',') {
            campos[f][j] = '\0';
            f++; j = 0;
        } else {
            if (j < TAM_CAMPO-1) campos[f][j++] = c;
        }
    }
    campos[f][j] = '\0';
    for (int k = f+1; k < 11; k++) strcpy(campos[k], "NaN");
}

void limpar(char *s) {
    int n = strlen(s);
    if(n>0 && (s[n-1]=='\n' || s[n-1]=='\r')) s[n-1] = '\0';
}

void analisar_lista(const char *campo, char destino[][TAM_CAMPO], int *qtd) {
    char tmp[TAM_CAMPO];
    strcpy(tmp, campo);
    char *p = tmp;
    if (*p=='[') p++;
    char *fim = strchr(tmp, ']');
    if (fim) *fim = '\0';
    *qtd = 0;
    char *tok = strtok(p, ",");
    while (tok) {
        while(*tok==' ') tok++;
        if (strlen(tok)==0 || strcmp(tok,"NaN")==0) {
            strcpy(destino[*qtd], "NaN");
        } else {
            strcpy(destino[*qtd], tok);
        }
        (*qtd)++;
        tok = strtok(NULL, ",");
    }
    if (*qtd==0) {
        strcpy(destino[0], "NaN");
        *qtd = 1;
    }
    for (int i = 0; i < *qtd-1; i++) {
        for (int j = i+1; j < *qtd; j++) {
            if (strcmp(destino[i], destino[j]) > 0) {
                char buf[TAM_CAMPO];
                strcpy(buf, destino[i]);
                strcpy(destino[i], destino[j]);
                strcpy(destino[j], buf);
            }
        }
    }
}

void carregar_csv(const char *caminho) {
    FILE *f = fopen(caminho, "r");
    if (!f) {
        fprintf(stderr, "Erro ao abrir arquivo '%s'\n", caminho);
        exit(1);
    }
    char linha[1024], campos[11][TAM_CAMPO];
    fgets(linha, sizeof(linha), f);
    while (fgets(linha, sizeof(linha), f)) {
        limpar(linha);
        dividir_csv(linha, campos);
        Programa p;
        strcpy(p.codigo, campos[0]);
        strcpy(p.categoria, strcmp(campos[1], "")?campos[1]:"NaN");
        strcpy(p.nome, strcmp(campos[2], "")?campos[2]:"NaN");
        strcpy(p.diretor, strcmp(campos[3], "")?campos[3]:"NaN");
        analisar_lista(campos[4], p.atores, &p.qtd_atores);
        strcpy(p.pais, strcmp(campos[5], "")?campos[5]:"NaN");
        strcpy(p.data_add, strcmp(campos[6], "")?campos[6]:"March 1, 1900");
        p.ano_lancamento = atoi(campos[7]);
        strcpy(p.classificacao, strcmp(campos[8], "")?campos[8]:"NaN");
        strcpy(p.duracao, strcmp(campos[9], "")?campos[9]:"NaN");
        analisar_lista(campos[10], p.generos, &p.qtd_generos);
        banco[total_programas++] = p;
    }
    fclose(f);
}

Programa* buscar_programa(const char *codigo) {
    for (int i = 0; i < total_programas; i++) {
        if (strcmp(banco[i].codigo, codigo) == 0) {
            return &banco[i];
        }
    }
    return NULL;
}

void inserir_inicio(Programa *p) {
    Celula *nova = malloc(sizeof(Celula));
    nova->prog = *p;
    nova->prox = inicio;
    inicio = nova;
}

void inserir_fim(Programa *p) {
    Celula *nova = malloc(sizeof(Celula));
    nova->prog = *p;
    nova->prox = NULL;
    if (!inicio) inicio = nova;
    else {
        Celula *ptr = inicio;
        while (ptr->prox) ptr = ptr->prox;
        ptr->prox = nova;
    }
}

void inserir_pos(Programa *p, int pos) {
    if (pos == 0) {
        inserir_inicio(p);
        return;
    }
    Celula *ptr = inicio;
    for (int i = 0; ptr && i < pos-1; i++) ptr = ptr->prox;
    if (!ptr) return;
    Celula *nova = malloc(sizeof(Celula));
    nova->prog = *p;
    nova->prox = ptr->prox;
    ptr->prox = nova;
}

Programa remover_inicio() {
    Celula *n = inicio;
    Programa ret = n->prog;
    inicio = inicio->prox;
    free(n);
    return ret;
}

Programa remover_fim() {
    if (!inicio) exit(1);
    if (!inicio->prox) {
        Programa ret = inicio->prog;
        free(inicio);
        inicio = NULL;
        return ret;
    }
    Celula *ptr = inicio;
    while (ptr->prox->prox) ptr = ptr->prox;
    Programa ret = ptr->prox->prog;
    free(ptr->prox);
    ptr->prox = NULL;
    return ret;
}

Programa remover_pos(int pos) {
    if (pos == 0) return remover_inicio();
    Celula *ptr = inicio;
    for (int i = 0; ptr->prox && i < pos-1; i++) ptr = ptr->prox;
    if (!ptr->prox) exit(1);
    Celula *n = ptr->prox;
    Programa ret = n->prog;
    ptr->prox = n->prox;
    free(n);
    return ret;
}

void mostrar_lista() {
    for (Celula *ptr = inicio; ptr; ptr = ptr->prox) {
        Programa *p = &ptr->prog;
        printf("=> %s ## %s ## %s ## %s ## [", p->codigo, p->nome, p->categoria, p->diretor);
        for (int i = 0; i < p->qtd_atores; i++) {
            printf("%s%s", p->atores[i], i < p->qtd_atores-1 ? ", " : "");
        }
        printf("] ## %s ## %s ## %d ## %s ## %s ## [", p->pais, p->data_add,
               p->ano_lancamento, p->classificacao, p->duracao);
        for (int i = 0; i < p->qtd_generos; i++) {
            printf("%s%s", p->generos[i], i < p->qtd_generos-1 ? ", " : "");
        }
        printf("] ##\n");
    }
}

int main() {
    carregar_csv("/tmp/disneyplus.csv");
    char entrada[64];
    while (fgets(entrada, sizeof(entrada), stdin)) {
        limpar(entrada);
        if (strcmp(entrada, "FIM") == 0) break;
        Programa *p = buscar_programa(entrada);
        if (p) inserir_fim(p);
    }
    int n;
    scanf("%d\n", &n);
    for (int i = 0; i < n; i++) {
        char cmd[4];
        fgets(entrada, sizeof(entrada), stdin);
        limpar(entrada);
        char *tk = strtok(entrada, " ");
        strcpy(cmd, tk);
        if (strcmp(cmd, "II") == 0) {
            tk = strtok(NULL, " ");
            Programa *p = buscar_programa(tk);
            if (p) inserir_inicio(p);
        } else if (strcmp(cmd, "IF") == 0) {
            tk = strtok(NULL, " ");
            Programa *p = buscar_programa(tk);
            if (p) inserir_fim(p);
        } else if (strcmp(cmd, "I*") == 0) {
            int pos = atoi(strtok(NULL, " "));
            tk = strtok(NULL, " ");
            Programa *p = buscar_programa(tk);
            if (p) inserir_pos(p, pos);
        } else if (strcmp(cmd, "RI") == 0) {
            Programa r = remover_inicio();
            printf("(R) %s\n", r.nome);
        } else if (strcmp(cmd, "RF") == 0) {
            Programa r = remover_fim();
            printf("(R) %s\n", r.nome);
        } else if (strcmp(cmd, "R*") == 0) {
            int pos = atoi(strtok(NULL, " "));
            Programa r = remover_pos(pos);
            printf("(R) %s\n", r.nome);
        }
    }
    mostrar_lista();
    return 0;
}
