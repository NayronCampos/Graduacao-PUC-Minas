import java.util.Random;
import java.util.Scanner;

public class tp01_q04 {
    static Random gerador = new Random();

    static {
        gerador.setSeed(4);
    }

    static String alterarString(String s) {
        int tamanho = s.length();

        // Sorteia a primeira letra aleatória (entre 'a' e 'z')
        char letra1 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);

        // Sorteia a segunda letra, garantindo que seja diferente da primeira
        char letra2;
        do {
            letra2 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
        } while (letra2 == letra1);

        // Constrói a nova string, substituindo letra1 por letra2
        String resultado = "";
        for (int i = 0; i < tamanho; i++) {
            char c = s.charAt(i);
            if (c == letra1) {
                resultado += letra2;
            } else {
                resultado += c;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leitura das linhas até a palavra "FIM"
        while (true) {
            String linha = scanner.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
            // Processa a linha e exibe o resultado
            System.out.println(alterarString(linha));
        }
        scanner.close();
    }
}
