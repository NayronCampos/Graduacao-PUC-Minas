import java.util.Scanner;

class Tabela {
    private int linhas;
    private int colunas;
    private int[][] valores;

    public Tabela(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.valores = new int[linhas][colunas];
    }

    public void atribuir(int i, int j, int valor) {
        valores[i][j] = valor;
    }

    public int obter(int i, int j) {
        return valores[i][j];
    }

    public Tabela somar(Tabela outra) {
        Tabela resultado = new Tabela(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado.valores[i][j] = this.valores[i][j] + outra.valores[i][j];
            }
        }
        return resultado;
    }

    public Tabela multiplicar(Tabela outra) {
        Tabela produto = new Tabela(this.linhas, outra.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                int soma = 0;
                for (int k = 0; k < this.colunas; k++) {
                    soma += this.valores[i][k] * outra.valores[k][j];
                }
                produto.valores[i][j] = soma;
            }
        }
        return produto;
    }

    public void exibirDiagonalPrincipal() {
        int limite = Math.min(linhas, colunas);
        for (int i = 0; i < limite; i++) {
            System.out.print(valores[i][i]);
            if (i < limite - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public void exibirDiagonalSecundaria() {
        int n = linhas;
        for (int i = 0; i < n; i++) {
            int j = colunas - 1 - i;
            if (j >= 0 && j < colunas) {
                System.out.print(valores[i][j]);
                if (i < n - 1) System.out.print(" ");
            }
        }
        System.out.println();
    }

    public void mostrar() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(valores[i][j]);
                if (j < colunas - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }
}

public class TP3Q11 {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int testes = leitor.nextInt();
        for (int caso = 0; caso < testes; caso++) {
            int l1 = leitor.nextInt();
            int c1 = leitor.nextInt();
            Tabela matriz1 = new Tabela(l1, c1);
            for (int i = 0; i < l1; i++) {
                for (int j = 0; j < c1; j++) {
                    matriz1.atribuir(i, j, leitor.nextInt());
                }
            }

            int l2 = leitor.nextInt();
            int c2 = leitor.nextInt();
            Tabela matriz2 = new Tabela(l2, c2);
            for (int i = 0; i < l2; i++) {
                for (int j = 0; j < c2; j++) {
                    matriz2.atribuir(i, j, leitor.nextInt());
                }
            }

            matriz1.exibirDiagonalPrincipal();
            matriz1.exibirDiagonalSecundaria();
            Tabela soma = matriz1.somar(matriz2);
            soma.mostrar();
            Tabela produto = matriz1.multiplicar(matriz2);
            produto.mostrar();
        }
        leitor.close();
    }
}
