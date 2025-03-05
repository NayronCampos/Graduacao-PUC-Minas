import java.util.Scanner;

public class tp01_q09 {
    public static boolean saoAnagramas(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        
        int[] contador = new int[256]; // Considerando a tabela ASCII
        
        for (int i = 0; i < str1.length(); i++) {
            contador[Character.toLowerCase(str1.charAt(i))]++;
            contador[Character.toLowerCase(str2.charAt(i))]--;
        }
        
        for (int i = 0; i < 256; i++) {
            if (contador[i] != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String linha = scanner.nextLine();
            
            if (linha.equals("FIM")) {
                break;
            }
            
            String[] partes = linha.split(" - ");
            if (partes.length != 2) {
                continue; // Ignora entradas inválidas
            }
            
            String str1 = partes[0].replaceAll("[^a-zA-ZÀ-ÿ]", "");
            String str2 = partes[1].replaceAll("[^a-zA-ZÀ-ÿ]", "");
            
            if (saoAnagramas(str1, str2)) {
                System.out.println("SIM");
            } else {
                System.out.println("NÃO");
            }
        }
        
        scanner.close();
    }
}