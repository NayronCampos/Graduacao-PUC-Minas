
import java.util.Scanner;

public class tp01_q03 {
    static String cifrar(String mensagem) {
        String resultado = "";
        int tamanho = mensagem.length();

        for (int i = 0; i < tamanho; i++) {
            char caractere = mensagem.charAt(i);
            char cifrado = (char) (caractere + 3); // Desloca o caractere 3 posições para frente
            resultado += cifrado; // Adiciona o caractere cifrado na nova string
        }

        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String mensagem = sc.nextLine().trim();

            if (mensagem.equals("FIM"))
                break; // Para o programa se a entrada for "FIM"

            System.out.println(cifrar(mensagem));
        }

        sc.close();
    }
}
