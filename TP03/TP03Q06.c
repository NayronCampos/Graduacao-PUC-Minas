#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define TAM_LINHA 1000

typedef struct {
    char id[20];
    char nome[200];
    char tipo[50];
    char diretor[100];
    char elenco[300];
    char pais[100];
    char data[50];
    int ano;
    char classificacao[10];
    char duracao[20];
    char categoria[200];
} Programa;

typedef struct No {
    Programa info;
    struct No* prox;
} No;

No* pilha = NULL;
int qtd = 0;

void limparLinha(char* str) {
    int tam = strlen(str);
    while (tam > 0 && (str[tam - 1] == '\n' || str[tam - 1] == '\r')) {
        str[--tam] = '\0';
    }
}

char* extrairCampo(char* linha, char* campo) {
    int i = 0, j = 0;
    bool entreAspas = false;

    while (linha[i] != '\0') {
        if (linha[i] == '"') {
            entreAspas = !entreAspas;
        } else if (linha[i] == ',' && !entreAspas) {
            break;
        } else {
            campo[j++] = linha[i];
        }
        i++;
    }
    campo[j] = '\0';
    if (linha[i] == ',') i++;
    return linha + i;
}

Programa buscarPrograma(char* chave, FILE* arquivo) {
    Programa p;
    rewind(arquivo);
    char linha[TAM_LINHA];

    while (fgets(linha, TAM_LINHA, arquivo)) {
        char* ptr = linha;
        char campo[300];

        // Comparar o primeiro campo (show_id)
        ptr = extrairCampo(ptr, campo);
        if (strcmp(campo, chave) != 0) continue;

        // Preencher os dados
        strcpy(p.id, campo);
        ptr = extrairCampo(ptr, p.tipo);
        ptr = extrairCampo(ptr, p.nome);
        ptr = extrairCampo(ptr, p.diretor);
        ptr = extrairCampo(ptr, p.elenco);
        ptr = extrairCampo(ptr, p.pais);
        ptr = extrairCampo(ptr, p.data);
        if (strlen(p.data) == 0) strcpy(p.data, "March 1, 1900");
        ptr = extrairCampo(ptr, campo);
        p.ano = (strlen(campo) > 0) ? atoi(campo) : 0;
        ptr = extrairCampo(ptr, p.classificacao);
        ptr = extrairCampo(ptr, p.duracao);
        ptr = extrairCampo(ptr, p.categoria);

        return p;
    }

    strcpy(p.nome, "NaN");
    return p;
}

void adicionarNaPilha(Programa p) {
    No* novo = (No*)malloc(sizeof(No));
    novo->info = p;
    novo->prox = pilha;
    pilha = novo;
    qtd++;
}

Programa removerDaPilha() {
    Programa pRemovido;
    if (pilha == NULL) {
        strcpy(pRemovido.nome, "NaN");
        return pRemovido;
    }
    No* tmp = pilha;
    pRemovido = tmp->info;
    pilha = pilha->prox;
    free(tmp);
    qtd--;
    return pRemovido;
}

void exibirPrograma(Programa p, int pos) {
    printf("[%d] => %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           pos, p.id, p.nome, p.tipo, p.diretor, p.elenco, p.pais,
           p.data, p.ano, p.classificacao, p.duracao, p.categoria);
}

void mostrarTudo() {
    No* atual = pilha;
    int pos = qtd - 1;
    while (atual != NULL) {
        exibirPrograma(atual->info, pos);
        atual = atual->prox;
        pos--;
    }
}

int main() {
    FILE* arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (arquivo == NULL) {
        printf("Arquivo disneyplus.csv não encontrado em /tmp/\n");
        return 1;
    }

    char entrada[TAM_LINHA];
    while (fgets(entrada, TAM_LINHA, stdin)) {
        limparLinha(entrada);
        if (strcmp(entrada, "FIM") == 0) break;
        Programa p = buscarPrograma(entrada, arquivo);
        adicionarNaPilha(p);
    }

    int operacoes;
    scanf("%d\n", &operacoes);
    for (int i = 0; i < operacoes; i++) {
        fgets(entrada, TAM_LINHA, stdin);
        limparLinha(entrada);
        if (entrada[0] == 'I') {
            char chave[20];
            sscanf(entrada + 2, "%s", chave);
            Programa p = buscarPrograma(chave, arquivo);
            adicionarNaPilha(p);
        } else if (entrada[0] == 'R') {
            Programa removido = removerDaPilha();
            printf("(R) %s\n", removido.nome);
        }
    }

    mostrarTudo();
    fclose(arquivo);
    return 0;
}
