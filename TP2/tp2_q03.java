import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class tp2_q03 {
    // contador de comparações (para o log)
    static long comparacoes = 0;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // --- PARTE 1: carrega o catálogo de títulos ---
        // leia até a linha “FIM”, descartando o índice e guardando só o título
        java.util.List<String> lista = new java.util.ArrayList<>();
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
            // essa linha é só o número (1, 2, 3, …)
            // vamos descartar e ler a próxima, que é o título
            String titulo = sc.nextLine();
            lista.add(titulo);
        }

        // --- PARTE 2: faz as pesquisas sequenciais ---
        long t0 = System.currentTimeMillis();
        while (true) {
            String valor = sc.nextLine();
            if (valor.equals("FIM")) {
                break;
            }
            boolean achou = pesquisaSequencial(lista, valor);
            if(achou==true){
            System.out.println("SIM");}
            else{
                System.out.println("NAO");}
        }
        long t1 = System.currentTimeMillis();
        sc.close();

        // --- PARTE 3: grava o log ---
        long tempoMs = t1 - t0;
        String MATRICULA = "20210000";                      // ← substitua pela sua
        String nomeArq = MATRICULA + "_sequencial.txt";
        try ( PrintWriter pw = new PrintWriter(new FileWriter(nomeArq)) ) {
            pw.print(MATRICULA);
            pw.print('\t');
            pw.print(tempoMs);
            pw.print('\t');
            pw.print(comparacoes);
        }
    }

    /**
     * Busca sequencial em lista de títulos.
     * Incrementa 'comparacoes' a cada comparação de String.
     */
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
