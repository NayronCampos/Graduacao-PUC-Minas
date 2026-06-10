import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class tp2_q03 {

    static long comparacoes = 0;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        java.util.List<String> lista = new java.util.ArrayList<>();
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
          
            
            String titulo = sc.nextLine();
            lista.add(titulo);
        }


        long t0 = System.currentTimeMillis();
        while (true) {
            String query = sc.nextLine();
            if (query.equals("FIM")) {
                break;
            }
            boolean achou = pesquisaSequencial(lista, query);
            System.out.println(achou ? "SIM" : "NAO");
        }
        long t1 = System.currentTimeMillis();
        sc.close();


        long tempoMs = t1 - t0;
        String MATRICULA = "874422";                      
        String nomeArq = MATRICULA + "_sequencial.txt";
        try ( PrintWriter pw = new PrintWriter(new FileWriter(nomeArq)) ) {
            pw.print(MATRICULA);
            pw.print('\t');
            pw.print(tempoMs);
            pw.print('\t');
            pw.print(comparacoes);
        }
    }

  
    public static boolean pesquisaSequencial(java.util.List<String> lista, String x) {
        for (String s : lista) {
            comparacoes++;
            if (s.equals(x)) {
                return true;
            }
        }
        return false;
    }
}
