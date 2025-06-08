#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define LIMITE_REGISTROS 10000
#define TAMANHO_PILHA 1000
#define TAMANHO_LINHA 4096

typedef struct {
    char id[32];
    char tipo[64];
    char titulo[256];
    char diretor[256];
    char elenco[512];
    char pais[128];
    char dataInclusao[64];
    int anoLancamento;
    char classificacao[32];
    char duracao[64];
    char categorias[512];
} Midia;

// Banco de dados
static Midia banco[LIMITE_REGISTROS];
static int totalRegistros = 0;

// Pilha
static Midia pilha[TAMANHO_PILHA];
static int topoPilha = 0;

// Parseador de CSV
void lerCSV(const char* linha, Midia* m) {
    char campos[11][512];
    int col = 0, idx = 0;
    bool aspas = false;

    for (int i = 0; linha[i] != '\0' && col < 11; i++) {
        char c = linha[i];
        if (c == '"') {
            aspas = !aspas;
        } else if (c == ',' && !aspas) {
            campos[col][idx] = '\0';
            col++; idx = 0;
        } else if (c != '\r' && c != '\n') {
            if (idx < 511) campos[col][idx++] = c;
        }
    }
    campos[col][idx] = '\0';

    strncpy(m->id,            campos[0][0] ? campos[0] : "NaN", sizeof(m->id));
    strncpy(m->tipo,          campos[1][0] ? campos[1] : "NaN", sizeof(m->tipo));
    strncpy(m->titulo,        campos[2][0] ? campos[2] : "NaN", sizeof(m->titulo));
    strncpy(m->diretor,       campos[3][0] ? campos[3] : "NaN", sizeof(m->diretor));
    strncpy(m->elenco,        campos[4][0] ? campos[4] : "NaN", sizeof(m->elenco));
    strncpy(m->pais,          campos[5][0] ? campos[5] : "NaN", sizeof(m->pais));
    strncpy(m->dataInclusao,  campos[6][0] ? campos[6] : "March 1, 1900", sizeof(m->dataInclusao));
    int ano = atoi(campos[7]);
    m->anoLancamento = (ano >= 1900 && ano <= 2030) ? ano : 0;
    strncpy(m->classificacao, campos[8][0] ? campos[8] : "NaN", sizeof(m->classificacao));
    strncpy(m->duracao,       campos[9][0] ? campos[9] : "NaN", sizeof(m->duracao));
    strncpy(m->categorias,    campos[10][0]? campos[10] : "NaN", sizeof(m->categorias));
}

// Carregamento da base de dados
void carregarBanco(const char* caminho) {
    FILE* arquivo = fopen(caminho, "r");
    if (!arquivo) return;

    char linha[TAMANHO_LINHA];
    fgets(linha, TAMANHO_LINHA, arquivo); // Ignora cabeçalho

    while (fgets(linha, TAMANHO_LINHA, arquivo) && totalRegistros < LIMITE_REGISTROS) {
        if (linha[0] == '\n') continue;
        lerCSV(linha, &banco[totalRegistros++]);
    }

    fclose(arquivo);
}

// Procura midia na base
Midia* buscarMidia(const char* id) {
    for (int i = 0; i < totalRegistros; i++) {
        if (strcmp(banco[i].id, id) == 0) {
            return &banco[i];
        }
    }
    return NULL;
}

// Impressão da mídia
void exibirMidia(const Midia* m) {
    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           m->id, m->titulo, m->tipo, m->diretor,
           m->elenco, m->pais, m->dataInclusao,
           m->anoLancamento, m->classificacao, m->duracao,
           m->categorias);
}

int main() {
    carregarBanco("/tmp/disneyplus.csv");

    // Parte 1: leitura de IDs até "FIM"
    char codigo[32];
    while (scanf("%s", codigo) == 1 && strcmp(codigo, "FIM") != 0) {
        Midia* m = buscarMidia(codigo);
        if (m && topoPilha < TAMANHO_PILHA) {
            pilha[topoPilha++] = *m;
        }
    }

    // Parte 2: comandos
    int totalComandos;
    scanf("%d", &totalComandos);
    for (int i = 0; i < totalComandos; i++) {
        char operacao;
        scanf(" %c", &operacao);
        if (operacao == 'I') {
            scanf("%s", codigo);
            Midia* m = buscarMidia(codigo);
            if (m && topoPilha < TAMANHO_PILHA) {
                pilha[topoPilha++] = *m;
            }
        } else if (operacao == 'R') {
            if (topoPilha > 0) {
                Midia removida = pilha[--topoPilha];
                printf("(R) %s\n", removida.titulo);
            }
        }
    }

    // Impressão da pilha do topo ao fundo
    for (int i = topoPilha - 1; i >= 0; i--) {
        printf("[%d] ", i);
        exibirMidia(&pilha[i]);
    }

    return 0;
}
