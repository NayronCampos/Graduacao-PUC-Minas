#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define TAM_CAMPO 500
#define MAX_ENTRADAS 1000

typedef struct {
    char id[20];
    char tipo[TAM_CAMPO];
    char nome[TAM_CAMPO];
    char diretor[TAM_CAMPO];
    char atores[TAM_CAMPO];
    char pais[TAM_CAMPO];
    char data[TAM_CAMPO];
    int ano;
    char classificacao[TAM_CAMPO];
    char duracao[TAM_CAMPO];
    char categorias[TAM_CAMPO];
} Programa;

typedef struct Bloco {
    Programa conteudo;
    struct Bloco* ant;
    struct Bloco* prox;
} Bloco;

int mesParaNumero(const char *mes) {
    if (strcmp(mes, "January") == 0) return 1;
    if (strcmp(mes, "February") == 0) return 2;
    if (strcmp(mes, "March") == 0) return 3;
    if (strcmp(mes, "April") == 0) return 4;
    if (strcmp(mes, "May") == 0) return 5;
    if (strcmp(mes, "June") == 0) return 6;
    if (strcmp(mes, "July") == 0) return 7;
    if (strcmp(mes, "August") == 0) return 8;
    if (strcmp(mes, "September") == 0) return 9;
    if (strcmp(mes, "October") == 0) return 10;
    if (strcmp(mes, "November") == 0) return 11;
    if (strcmp(mes, "December") == 0) return 12;
    return 0;
}

int converterData(const char *data) {
    if (strlen(data) < 3) return 19000301;
    char copia[TAM_CAMPO];
    strcpy(copia, data);
    char *mes = strtok(copia, " ");
    char *diaStr = strtok(NULL, " ");
    char *anoStr = strtok(NULL, " ");
    if (!mes || !diaStr || !anoStr) return 19000301;

    int m = mesParaNumero(mes);
    char diaBuf[10];
    int len = strlen(diaStr);
    if (diaStr[len - 1] == ',') {
        strncpy(diaBuf, diaStr, len - 1);
        diaBuf[len - 1] = '\0';
    } else {
        strcpy(diaBuf, diaStr);
    }

    int d = atoi(diaBuf);
    int a = atoi(anoStr);
    return a * 10000 + m * 100 + d;
}

Bloco* novoBloco(Programa prog) {
    Bloco* novo = (Bloco*) malloc(sizeof(Bloco));
    novo->conteudo = prog;
    novo->ant = novo->prox = NULL;
    return novo;
}

void inserirFinal(Bloco** inicio, Programa prog) {
    Bloco* novo = novoBloco(prog);
    if (*inicio == NULL) {
        *inicio = novo;
        return;
    }
    Bloco* atual = *inicio;
    while (atual->prox) atual = atual->prox;
    atual->prox = novo;
    novo->ant = atual;
}

void trocar(Programa* a, Programa* b) {
    Programa tmp = *a;
    *a = *b;
    *b = tmp;
}

Bloco* dividir(Bloco* ini, Bloco* fim, int* comparacoes) {
    int base = converterData(fim->conteudo.data);
    Bloco* i = ini->ant;

    for (Bloco* j = ini; j != fim; j = j->prox) {
        (*comparacoes)++;
        int jData = converterData(j->conteudo.data);
        if (jData < base || (jData == base && strcmp(j->conteudo.nome, fim->conteudo.nome) < 0)) {
            i = (i == NULL) ? ini : i->prox;
            trocar(&(i->conteudo), &(j->conteudo));
        }
    }

    i = (i == NULL) ? ini : i->prox;
    trocar(&(i->conteudo), &(fim->conteudo));
    return i;
}

void ordenarRapido(Bloco* ini, Bloco* fim, int* comparacoes) {
    if (fim && ini && ini != fim && ini != fim->prox) {
        Bloco* p = dividir(ini, fim, comparacoes);
        ordenarRapido(ini, p->ant, comparacoes);
        ordenarRapido(p->prox, fim, comparacoes);
    }
}

void iniciarOrdenacao(Bloco* ini, int* comparacoes) {
    Bloco* fim = ini;
    while (fim && fim->prox) fim = fim->prox;
    ordenarRapido(ini, fim, comparacoes);
}

void tirarAspas(char* str) {
    int len = strlen(str);
    if (len >= 2 && str[0] == '\"' && str[len - 1] == '\"') {
        memmove(str, str + 1, len - 2);
        str[len - 2] = '\0';
    }
}

void extrairCampos(char* linha, Programa* p) {
    char* campos[11];
    int idx = 0, entreAspas = 0, bufPos = 0;
    char buf[TAM_CAMPO];

    for (int i = 0; linha[i] != '\0' && idx < 11; i++) {
        char c = linha[i];
        if (c == '\"') {
            entreAspas = !entreAspas;
            buf[bufPos++] = c;
        } else if (c == ',' && !entreAspas) {
            buf[bufPos] = '\0';
            campos[idx++] = strdup(buf);
            bufPos = 0;
        } else {
            buf[bufPos++] = c;
        }
    }
    buf[bufPos] = '\0';
    campos[idx++] = strdup(buf);

    while (idx < 11) campos[idx++] = strdup("NaN");

    strcpy(p->id, campos[0]); tirarAspas(p->id);
    strcpy(p->tipo, campos[1]); tirarAspas(p->tipo);
    strcpy(p->nome, campos[2]); tirarAspas(p->nome);
    strcpy(p->diretor, campos[3]); tirarAspas(p->diretor);
    strcpy(p->atores, campos[4]); tirarAspas(p->atores);
    strcpy(p->pais, campos[5]); tirarAspas(p->pais);
    strcpy(p->data, campos[6]); tirarAspas(p->data);
    if (strlen(p->data) < 3) strcpy(p->data, "March 1, 1900");
    p->ano = atoi(campos[7]);
    strcpy(p->classificacao, campos[8]); tirarAspas(p->classificacao);
    strcpy(p->duracao, campos[9]); tirarAspas(p->duracao);
    strcpy(p->categorias, campos[10]); tirarAspas(p->categorias);

    for (int i = 0; i < 11; i++) free(campos[i]);
}

void mostrarPrograma(Programa p) {
    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           p.id, p.nome, p.tipo, p.diretor, p.atores,
           p.pais, p.data, p.ano, p.classificacao, p.duracao, p.categorias);
}


void exibirLista(Bloco* ini) {
    for (Bloco* atual = ini; atual != NULL; atual = atual->prox) {
        mostrarPrograma(atual->conteudo);
    }
}

void gerarLog(double tempo, int comparacoes) {
    FILE* log = fopen("867656_quicksort2.txt", "w");
    if (log) {
        fprintf(log, "867656\t%.6lf\t%d\n", tempo, comparacoes);
        fclose(log);
    }
}

int main() {
    char *entradas[MAX_ENTRADAS];
    int qtdIds = 0;
    char buf[100];

    while (fgets(buf, sizeof(buf), stdin)) {
        buf[strcspn(buf, "\r\n")] = '\0';
        if (strcmp(buf, "FIM") == 0) break;
        entradas[qtdIds++] = strdup(buf);
    }

    FILE* arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (!arquivo) {
        printf("Erro ao abrir /tmp/disneyplus.csv\n");
        return 1;
    }

    char linha[5000];
    Bloco* lista = NULL;
    Programa prog;
    clock_t ini = clock();

    for (int i = 0; i < qtdIds; i++) {
        rewind(arquivo);
        fgets(linha, sizeof(linha), arquivo); // cabeçalho
        while (fgets(linha, sizeof(linha), arquivo)) {
            Programa temp;
            extrairCampos(linha, &temp);
            if (strcmp(temp.id, entradas[i]) == 0) {
                prog = temp;
                inserirFinal(&lista, prog);
                break;
            }
        }
    }
    fclose(arquivo);

    int comparacoes = 0;
    iniciarOrdenacao(lista, &comparacoes);
    clock_t fim = clock();

    double tempo = (double)(fim - ini) / CLOCKS_PER_SEC;
    exibirLista(lista);
    gerarLog(tempo, comparacoes);

    for (int i = 0; i < qtdIds; i++) free(entradas[i]);

    return 0;
}
