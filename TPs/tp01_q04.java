import java.util.Random;
import java.util.Scanner;

public class tp01_q04 {

    public static String alterarString(String input) {
        Random gerador = new Random();
        gerador.setSeed(4); // Definir a semente para garantir reprodutibilidade

        // Sorteando duas letras minúsculas aleatórias e garantindo que sejam diferentes
        char letra1, letra2;
        letra1 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
        do {
            letra2 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
        } while (letra1 == letra2); // Garante que as letras não sejam iguais

        // Substituindo todas as ocorrências da letra1 por letra2
        return input.replace(letra1, letra2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("FIM")) {
                break; // Encerra o loop ao encontrar "FIM"
            }

            // Processa a string e exibe o resultado
            System.out.println(alterarString(input));
        }

        scanner.close();
    }
}
