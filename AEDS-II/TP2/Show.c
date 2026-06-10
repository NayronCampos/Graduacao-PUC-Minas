/*Crie uma classe Show seguindo todas as regras apresentadas no slide unidade00l conceitosBasicos introducaoOO.pdf. Sua classe ter´a os atributos privado show id:
string, type: string, title: string, director: string, cast: string[], country:
string, date added: date, release year: int, rating: string, duration: string, listed in: string[]. Sua classe tamb´em ter´a pelo menos dois construtores, e os m´etodos gets,
sets, clone, imprimir e ler.
O m´etodo imprimir mostra os atributos do registro (ver cada linha da sa´ıda padr˜ao) e o ler lˆe
os atributos de um registro. Aten¸c˜ao para o arquivo de entrada, pois em alguns registros faltam
valores e esse deve ser substitu´ıdo pelo valor NaN. A entrada padr˜ao ´e composta por v´arias linhas
e cada uma cont´em um n´umero inteiro indicando o show id do Show a ser lido.
A ´ultima linha da entrada cont´em a palavra FIM. A sa´ıda padr˜ao tamb´em cont´em v´arias
linhas, uma para cada registro contido em uma linha da entrada padr˜ao, no seguinte formato: [=> id ## type ## title ## director ## [cast] ## country ## date added ##
release year ## rating ## duration ## [listed in].*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


typedef struct {
    char showId[256];
    char type[256];
    char title[256];
    char director[256];
    char cast[10][256];
    int castCount;
    char country[256];
    char dateAdded[256];  
    
    int releaseYear;
    char rating[256];
    char duration[256];
    char listedIn[10][256];
    int listedInCount;
} Show;


void limparString(char *str, const char *chars) {
    char temp[256];
    int j = 0;
    for (int i = 0; str[i] != '\0'; i++) {
        int remover = 0;
        for (int k = 0; chars[k] != '\0'; k++) {
            if (str[i] == chars[k]) {
                remover = 1;
                break;
            }
        }
        if (!remover) {
            temp[j++] = str[i];
        }
    }
    temp[j] = '\0';
    strcpy(str, temp);
}



int splitCSVLine(char *line, char tokens[][256]) {
    int tokenCount = 0, j = 0;
    int inQuotes = 0;
    for (int i = 0; line[i] != '\0' && line[i] != '\n'; i++) {
        char c = line[i];
        if(c == '"') {
            inQuotes = !inQuotes;

        } else if(c == ',' && !inQuotes) {
            tokens[tokenCount][j] = '\0';
            tokenCount++;
            j = 0;
            tokens[tokenCount][0] = '\0';
        } else {
            tokens[tokenCount][j++] = c;
        }
    }
    tokens[tokenCount][j] = '\0';
    return tokenCount + 1;
}


int splitList(char *str, char list[][256]) {
    int count = 0;
    char *token = strtok(str, ",");
    while (token != NULL && count < 10) {

        while(isspace(*token)) token++;
        char *end = token + strlen(token) - 1;
        while(end > token && isspace(*end)) {
            *end = '\0';
            end--;
        }
        strcpy(list[count++], token);
        token = strtok(NULL, ",");
    }
    return count;
}


void parseLine(char *line, Show *s) {
    char tokens[20][256] = {0};
    int n = splitCSVLine(line, tokens);


    strcpy(s->showId, (n > 0 && strlen(tokens[0]) > 0) ? tokens[0] : "NaN");
    strcpy(s->type, (n > 1 && strlen(tokens[1]) > 0) ? tokens[1] : "NaN");

    if(n > 2 && strlen(tokens[2]) > 0) {
        strcpy(s->title, tokens[2]);

        limparString(s->title, "\"");
    } else {
        strcpy(s->title, "NaN");
    }

    strcpy(s->director, (n > 3 && strlen(tokens[3]) > 0) ? tokens[3] : "NaN");


    if(n > 4 && strlen(tokens[4]) > 0) {
        char aux[256];
        strcpy(aux, tokens[4]);
        limparString(aux, "[]\"");
        if(strlen(aux) == 0) {
            strcpy(s->cast[0], "NaN");
            s->castCount = 1;
        } else {
            s->castCount = splitList(aux, s->cast);
        }
    } else {
        strcpy(s->cast[0], "NaN");
        s->castCount = 1;
    }

    strcpy(s->country, (n > 5 && strlen(tokens[5]) > 0) ? tokens[5] : "NaN");


    if(n > 6 && strlen(tokens[6]) > 0) {
        char aux[256];
        strcpy(aux, tokens[6]);
        limparString(aux, "\"");
        strcpy(s->dateAdded, aux);
        if(strlen(s->dateAdded)==0)
            strcpy(s->dateAdded, "NaN");
    } else {
        strcpy(s->dateAdded, "NaN");
    }


    if(n > 7 && strlen(tokens[7]) > 0) {
        s->releaseYear = atoi(tokens[7]);
    } else {
        s->releaseYear = -1;
    }

    strcpy(s->rating, (n > 8 && strlen(tokens[8]) > 0) ? tokens[8] : "NaN");
    strcpy(s->duration, (n > 9 && strlen(tokens[9]) > 0) ? tokens[9] : "NaN");


    if(n > 10 && strlen(tokens[10]) > 0) {
        char aux[256];
        strcpy(aux, tokens[10]);
        limparString(aux, "[]\"");
        if(strlen(aux) == 0) {
            strcpy(s->listedIn[0], "NaN");
            s->listedInCount = 1;
        } else {
            s->listedInCount = splitList(aux, s->listedIn);
        }
    } else {
        strcpy(s->listedIn[0], "NaN");
        s->listedInCount = 1;
    }
}


int lerArquivo(const char *nomeArquivo, Show shows[], int maxShows) {
    FILE *fp = fopen(nomeArquivo, "r");
    if (!fp) return 0;
    char line[1024];


    fgets(line, 1024, fp);
    int count = 0;
    while(fgets(line, 1024, fp) && count < maxShows) {
        if(strlen(line) < 2) continue;
        parseLine(line, &shows[count]);
        count++;
    }
    fclose(fp);
    return count;
}

void imprimirShow(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->showId, s->title, s->type, s->director);
    for (int i = 0; i < s->castCount; i++) {
        printf("%s", s->cast[i]);
        if(i < s->castCount - 1)
            printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->dateAdded, s->releaseYear, s->rating, s->duration);
    for (int i = 0; i < s->listedInCount; i++) {
        printf("%s", s->listedIn[i]);
        if(i < s->listedInCount - 1)
            printf(", ");
    }
    printf("] ##\n");
}

int main() {
    const char *arquivo = "/tmp/disneyplus.csv";
    Show shows[1000];
    int nShows = lerArquivo(arquivo, shows, 1000);

    char id[256];

    while(scanf("%s", id) == 1) {
        if(strcmp(id, "FIM") == 0)
            break;
        int achou = 0;
        for (int i = 0; i < nShows; i++) {
            if(strcmp(shows[i].showId, id) == 0) {
                imprimirShow(&shows[i]);
                achou = 1;
                break;
            }
        }
        if(!achou)
            printf("ID não encontrado: %s\n", id);
    }
    return 0;
}
