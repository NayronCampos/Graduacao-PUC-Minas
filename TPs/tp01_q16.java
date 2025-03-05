import java.util.Scanner;

public class tp01_q16 {

    static boolean PalidromeRecursivo(String frase, int index, int reverseindex) {

        if (index >= reverseindex) {
            MyIO.println("SIM");
            return true;
        } else {
            if (frase.charAt(index) == frase.charAt(reverseindex)) {// recursivo
                PalidromeRecursivo(frase, index + 1, reverseindex - 1);
                return true;
            } else {
                MyIO.println("NAO");
                return false;
            }

        }
    }

    static boolean Palindrome(String str) {// metodo que chama o recursivo e printa

        if (PalidromeRecursivo(str, 0, str.length() - 1)) {
            return true;
        } else {
            return false;
        }

    }

    static boolean igual(String frase) {

        if (frase.length() == 3 && frase.charAt(0) == 'F' && frase.charAt(1) == 'I' && frase.charAt(2) == 'M') {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = MyIO.readLine();
            if (igual(input)) {
                break;
            }

            Palindrome(input);
        }

        scanner.close();
    }
}