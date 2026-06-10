import java.util.Scanner;

public class tp01_q08 {

    public static int somar(String numero) {
        int i = 0;
        int soma = 0;

        while (i < numero.length()) {
            soma += numero.charAt(i) - '0'; // Converte o caractere para número e soma
            i++;
        }

        return soma;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String valor = scanner.nextLine();
            if (valor.isEmpty())
                break;
            System.out.println(somar(valor));
        }
    }
}
