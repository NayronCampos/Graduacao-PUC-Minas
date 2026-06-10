import java.io.*;
import java.util.*;

public class tp02_q11 {

    private static int comparacoes = 0;
    private static int movimentacoes = 0;

    public static void main(String[] args) throws Exception {
        List<Show> todos = lerCsv("/tmp/disneyplus.csv");

        Scanner sc = new Scanner(System.in, "UTF-8");
        List<String> ids = new ArrayList<>();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break;
            ids.add(linha);
        }
        sc.close();

        Show[] arr = new Show[ids.size()];
        int n = 0;
        for (String id : ids) {
            for (Show s : todos) {
                if (s.showId.equals(id)) {
                    arr[n++] = s.clone();
                    break;
                }
            }
        }

        long t0 = System.nanoTime();

        for (int i = 1; i < n; i++) {
            Show chave = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparacoes++;
                if (arr[j].title.compareTo(chave.title) > 0) {
                    arr[j + 1] = arr[j];
                    movimentacoes++;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = chave;
            movimentacoes++;
        }

        if (n > 0) {
            int minAno = arr[0].releaseYear, maxAno = arr[0].releaseYear;
            for (int i = 1; i < n; i++) {
                if (arr[i].releaseYear < minAno) minAno = arr[i].releaseYear;
                if (arr[i].releaseYear > maxAno) maxAno = arr[i].releaseYear;
            }
            int faixa = maxAno - minAno + 1;
            int[] cont = new int[faixa];
            for (int i = 0; i < n; i++) {
                cont[arr[i].releaseYear - minAno]++;
            }
            for (int i = 1; i < faixa; i++) {
                cont[i] += cont[i - 1];
            }
            Show[] saida = new Show[n];
            for (int i = n - 1; i >= 0; i--) {
                int idx = arr[i].releaseYear - minAno;
                int pos = --cont[idx];
                saida[pos] = arr[i];
                movimentacoes++;
            }
            for (int i = 0; i < n; i++) {
                arr[i] = saida[i];
                movimentacoes++;
            }
        }

        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;

        // imprime 
        for (int i = 0; i < n; i++) {
            arr[i].imprimir();
        }

        try (PrintWriter log = new PrintWriter(new FileWriter("874422 countingsort.txt"))) {
            log.printf("874422\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
        }
    }

    private static List<Show> lerCsv(String caminho) throws Exception {
        List<Show> lista = new ArrayList<>();
        Scanner sc = new Scanner(new File(caminho), "UTF-8");
        if (sc.hasNextLine()) sc.nextLine(); // pula cabeçalho
        while (sc.hasNextLine()) {
            lista.add(criarShow(sc.nextLine()));
        }
        sc.close();
        return lista;
    }

    // converte CSV em Show
    private static Show criarShow(String linha) {
        List<String> partes = new ArrayList<>();
        boolean aspas = false;
        StringBuilder sb = new StringBuilder();
        for (char c : linha.toCharArray()) {
            if (c == '"') aspas = !aspas;
            else if (c == ',' && !aspas) {
                partes.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        partes.add(sb.toString().trim());

        String id     = pegaOuNaN(partes, 0);
        String tipo   = pegaOuNaN(partes, 1);
        String titulo = pegaOuNaN(partes, 2);
        String dire   = pegaOuNaN(partes, 3);
        String[] elenco = ordenarVetor(pegaOuNaN(partes, 4));
        String pais   = pegaOuNaN(partes, 5);
        String data   = (partes.size() > 6 && !partes.get(6).isEmpty())
                            ? partes.get(6) : "March 1, 1900";
        int ano;
        try { ano = Integer.parseInt(pegaOuNaN(partes, 7)); }
        catch (NumberFormatException e) { ano = 0; }
        String nota   = pegaOuNaN(partes, 8);
        String dur    = pegaOuNaN(partes, 9);
        String[] cat  = ordenarVetor(pegaOuNaN(partes, 10));

        return new Show(id, tipo, titulo, dire, elenco, pais, data, ano, nota, dur, cat);
    }

    private static String pegaOuNaN(List<String> v, int i) {
        return (i >= v.size() || v.get(i).isEmpty()) ? "NaN" : v.get(i);
    }

    private static String[] ordenarVetor(String campo) {
        if ("NaN".equals(campo)) return new String[0];
        String[] a = campo.split(",\\s*");
        Arrays.sort(a);
        return a;
    }

    static class Show implements Cloneable {
        String showId, type, title, director;
        String[] cast, listedIn;
        String country, dateAdded, rating, duration;
        int releaseYear;

        Show(String id, String tp, String ti, String di,
             String[] ca, String pa, String da,
             int ry, String ra, String du, String[] li) {
            showId = id;
            type = tp;
            title = ti;
            director = di;
            cast = ca;
            country = pa;
            dateAdded = da;
            releaseYear = ry;
            rating = ra;
            duration = du;
            listedIn = li;
        }

        void imprimir() {
            System.out.printf(
                "=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##%n",
                showId, title, type, director,
                Arrays.toString(cast),
                country, dateAdded,
                releaseYear,
                rating, duration,
                Arrays.toString(listedIn)
            );
        }

        @Override
        protected Show clone() {
            return new Show(
                showId, type, title, director,
                Arrays.copyOf(cast, cast.length),
                country, dateAdded,
                releaseYear, rating, duration,
                Arrays.copyOf(listedIn, listedIn.length)
            );
        }
    }
}
