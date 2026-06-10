#include <stdio.h>
#include <stdlib.h>

int main() {
    FILE *arquivo;
    int n;
    
    scanf("%d", &n);

    arquivo = fopen("Entrada.txt", "wb");
    if (arquivo == NULL) {
        perror("Erro ao abrir o arquivo para escrita");
        return 1;
    }

    for (int i = 0; i < n; i++) {
        double numero;
        scanf("%lf", &numero);
        if (fwrite(&numero, sizeof(double), 1, arquivo) != 1) {
            perror("Erro ao escrever no arquivo");
            fclose(arquivo);
            return 1;
        }
    }

    fclose(arquivo);

    arquivo = fopen("Entrada.txt", "rb");
    if (arquivo == NULL) {
        perror("Erro ao abrir o arquivo para leitura");
        return 1;
    }

    fseek(arquivo, 0, SEEK_END);
    long posicaoAtual = ftell(arquivo);

    for (int i = n - 1; i >= 0; i--) {
        posicaoAtual -= sizeof(double);
        if (posicaoAtual < 0) {
            perror("Erro: posição negativa no arquivo");
            fclose(arquivo);
            return 1;
        }

        if (fseek(arquivo, posicaoAtual, SEEK_SET) != 0) {
            perror("Erro ao posicionar o ponteiro do arquivo");
            fclose(arquivo);
            return 1;
        }

        double numero;
        if (fread(&numero, sizeof(double), 1, arquivo) != 1) {
            perror("Erro ao ler do arquivo");
            fclose(arquivo);
            return 1;
        }

        // Exibir sem zeros desnecessários
        if (numero == (long)numero)
            printf("%ld\n", (long)numero);
        else
            printf("%.10g\n", numero);
    }

    fclose(arquivo);
    return 0;
}
