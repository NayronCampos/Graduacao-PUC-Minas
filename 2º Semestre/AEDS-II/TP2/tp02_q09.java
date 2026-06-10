import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class tp02_q09 {
    private static int contComparacoes, contMovimentacoes;

    public static void main(String[] args) throws Exception {
        List<Show> listaShows = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        leitor.readLine(); // pula cabeçalho
        String linha;
        while ((linha = leitor.readLine()) != null) {
            listaShows.add(construirShow(linha));
        }
        leitor.close();

        Scanner scanner = new Scanner(System.in, "UTF-8");
        List<String> ids = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String id = scanner.nextLine();
            if (id.equals("FIM")) break;
            ids.add(id);
        }
        scanner.close();

        Show[] arr = new Show[ids.size()];
        int n = 0;
        for (String id : ids) {
            for (Show s : listaShows) {
                if (s.showId.equals(id)) {
                    arr[n++] = s.clone();
                    break;
                }
            }
        }

        long inicio = System.nanoTime();
        ordenarHeap(arr, n);
        long duracao = System.nanoTime() - inicio;
        double segundos = duracao / 1e9;

        for (int i = 0; i < n; i++) {
            arr[i].mostrar();
        }

        PrintWriter log = new PrintWriter(new FileWriter("874422_heapsort.txt"));
        log.printf("874422\t%d\t%d\t%.6f\n", contComparacoes, contMovimentacoes, segundos);
        log.close();
    }

    private static Show construirShow(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean entreAspas = false;
        for (char c : linha.toCharArray()) {
            if (c == '"') entreAspas = !entreAspas;
            else if (c == ',' && !entreAspas) {
                campos.add(sb.toString().trim()); sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        campos.add(sb.toString().trim());

        String id       = pegaOuNaN(campos, 0, "NaN");
        String tipo     = pegaOuNaN(campos, 1, "NaN");
        String titulo   = pegaOuNaN(campos, 2, "NaN");
        String diretor  = pegaOuNaN(campos, 3, "NaN");
        String[] elenco = dividirEOrdenar(pegaOuNaN(campos, 4, "NaN"));
        String pais     = pegaOuNaN(campos, 5, "NaN");
        String data     = campos.size()>6 && !campos.get(6).isEmpty() ? campos.get(6) : "March 1, 1900";
        int ano         = parseInt(pegaOuNaN(campos, 7, "0"));
        String nota     = pegaOuNaN(campos, 8, "NaN");
        String duracao  = pegaOuNaN(campos, 9, "NaN");
        String[] lista  = dividirEOrdenar(pegaOuNaN(campos, 10, "NaN"));

        return new Show(id, tipo, titulo, diretor, elenco, pais, data, ano, nota, duracao, lista);
    }

    private static String pegaOuNaN(List<String> v, int i, String padrao) {
        return (i < v.size() && !v.get(i).isEmpty()) ? v.get(i) : padrao;
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s); } catch(Exception e) { return 0; }
    }

    private static String[] dividirEOrdenar(String campo) {
        if ("NaN".equals(campo)) return new String[0];
        String[] arr = campo.split(",\\s*");
        heapSortStrings(arr);
        return arr;
    }

    private static void ordenarHeap(Show[] a, int n) {
        for (int i = n/2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int tam = n-1; tam > 0; tam--) {
            trocar(a, 0, tam); contMovimentacoes++;
            heapify(a, tam, 0);
        }
    }

    private static void heapify(Show[] a, int n, int i) {
        int maior = i, esq = 2*i+1, dir = 2*i+2;
        if (esq < n && (++contComparacoes)>0 && comparar(a[esq], a[maior]) > 0) maior = esq;
        if (dir < n && (++contComparacoes)>0 && comparar(a[dir], a[maior]) > 0) maior = dir;
        if (maior != i) {
            trocar(a, i, maior); contMovimentacoes++;
            heapify(a, n, maior);
        }
    }

    private static void heapSortStrings(String[] a) {
        int n = a.length;
        for (int i = n/2 -1; i >=0; i--) heapifyStr(a, n, i);
        for (int tam = n-1; tam > 0; tam--) {
            String t = a[0]; a[0] = a[tam]; a[tam] = t;
            heapifyStr(a, tam, 0);
        }
    }
    private static void heapifyStr(String[] a, int n, int i) {
        int maior = i, esq = 2*i+1, dir = 2*i+2;
        if (esq < n && a[esq].compareTo(a[maior])>0) maior = esq;
        if (dir < n && a[dir].compareTo(a[maior])>0) maior = dir;
        if (maior != i) {
            String t = a[i]; a[i] = a[maior]; a[maior] = t;
            heapifyStr(a, n, maior);
        }
    }

    private static int comparar(Show x, Show y) {
        int c = x.director.compareTo(y.director);
        return c != 0 ? c : x.title.compareTo(y.title);
    }

    private static void trocar(Show[] a, int i, int j) {
        Show tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static class Show implements Cloneable {
        String showId, type, title, director;
        String[] cast, listedIn;
        String country, dateAdded, rating, duration;
        int releaseYear;

        Show(String id, String t, String ti, String d,
             String[] c, String p, String da, int ry,
             String r, String du, String[] li) {
            showId = id; type = t; title = ti; director = d;
            cast = c; country = p; dateAdded = da;
            releaseYear = ry; rating = r; duration = du;
            listedIn = li;
        }

        void mostrar() {
            String cs = cast.length==0 ? "[NaN]" : Arrays.toString(cast);
            String ls = listedIn.length==0 ? "[NaN]" : Arrays.toString(listedIn);
            System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##%n",
                showId, title, type, director,
                cs, country, dateAdded,
                releaseYear, rating, duration, ls);
        }

        @Override protected Show clone() {
            return new Show(showId, type, title, director,
                Arrays.copyOf(cast, cast.length), country,
                dateAdded, releaseYear, rating, duration,
                Arrays.copyOf(listedIn, listedIn.length));
        }
    }
}
