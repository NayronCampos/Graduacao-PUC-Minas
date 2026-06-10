
import java.util.Scanner;

public class tp01_q03 {
    static String cifrar(String mensagem) {
        String resultado = "";
        int tamanho = mensagem.length();

        for (int i = 0; i < tamanho; i++) {
            char caractere = mensagem.charAt(i);
            char cifrado = (char) (caractere + 3);
            resultado += cifrado;
        }

        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String mensagem = sc.nextLine().trim();

            if (mensagem.equals("FIM"))
                break;

            System.out.println(cifrar(mensagem));
        }

        sc.close();
    }
}
