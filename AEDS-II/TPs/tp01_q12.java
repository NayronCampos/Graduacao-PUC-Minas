import java.util.Scanner;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class tp01_q12 {

    public static boolean senhaValida(String senha) {
        if (senha.length() < 8) {
            return false;
        }

        boolean Maiuscula = false, Minuscula = false, Numero = false, Especial = false;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            if (c >= 'A' && c <= 'Z') {
                Maiuscula = true;
            } else if (c >= 'a' && c <= 'z') {
                Minuscula = true;
            } else if (c >= '0' && c <= '9') {
                Numero = true;
            } else {
                Especial = true;
            }
        }

        return Maiuscula && Minuscula && Numero && Especial;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        PrintStream saida = new PrintStream(System.out, true, "UTF-8"); // Garante saída UTF-8

        while (scanner.hasNextLine()) {
            String senha = scanner.nextLine();
            if (senhaValida(senha)) {
                saida.println("SIM");
            } else {
                saida.println("NÃO");
            }
        }

        scanner.close();
    }
}
