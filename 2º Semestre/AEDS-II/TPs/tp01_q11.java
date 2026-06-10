import java.util.Scanner;

public class tp01_q11 {

    public static int comprimentoSubstring(String texto) {
        int maxComprimento = 0;
        int inicio = 0;
        int[] posicao = new int[256];

        for (int i = 0; i < 256; i++) {
            posicao[i] = -1;
        }

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (posicao[c] >= inicio) {
                inicio = posicao[c] + 1;
            }

            posicao[c] = i;
            int comprimentoAtual = i - inicio + 1;

            if (comprimentoAtual > maxComprimento) {
                maxComprimento = comprimentoAtual;
            }
        }

        return maxComprimento;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            System.out.println(comprimentoSubstring(linha));
        }

        scanner.close();
    }
}
