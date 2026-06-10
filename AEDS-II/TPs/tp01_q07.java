import java.util.Scanner;

public class tp01_q07 {
    public static String inverter(String str) {
        StringBuilder invertida = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            invertida.append(str.charAt(i));
        }
        return invertida.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String frase = scanner.nextLine();
            if (frase.isEmpty())
                break;
            System.out.println(inverter(frase));
        }
        scanner.close();
    }
}
