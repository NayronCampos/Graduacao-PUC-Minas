import java.util.Scanner;

public class tp01_q01 {

    static boolean palindromo(String palavra) {
        int tamanho = palavra.length();
        for (int i = 0, j = tamanho - 1; i < j; i++, j--) {
            if (palavra.charAt(i) != palavra.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    static boolean fim(String palavra) {
        return palavra.equals("FIM");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String palavra = sc.nextLine().trim();

            if (fim(palavra))
                break;
            System.out.println(palindromo(palavra) ? "SIM" : "NAO");
        }

        sc.close();
    }
}
