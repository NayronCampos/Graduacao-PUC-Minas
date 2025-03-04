import java.util.Scanner;

public class tp01_q10 {

    // Método que conta o número de palavras na string sem usar métodos de String
    public static int contarPalavras(String texto) {
        int contador = 0;
        boolean emPalavra = false;

        // Itera por cada caractere da string
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i); // Obtém o caractere na posição i

            // Se o caractere for um espaço
            if (c == ' ') {
                emPalavra = false; // Terminou a palavra
            } else {
                // Se não é um espaço e não está em uma palavra, então é uma nova palavra
                if (!emPalavra) {
                    contador++; // Conta uma nova palavra
                    emPalavra = true; // Marca que estamos dentro de uma palavra
                }
            }
        }

        return contador;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Lê as linhas até o final
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();

            // Se a linha estiver vazia, pula
            if (linha.isEmpty()) {
                continue;
            }

            // Chama o método para contar as palavras na linha
            System.out.println(contarPalavras(linha));
        }

        scanner.close();
    }
}
