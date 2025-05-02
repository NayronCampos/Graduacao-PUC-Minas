import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;             

public class tp02_q13 {

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


        Show[] vet = new Show[ids.size()];
        int n = 0;
        for (String id : ids) {
            for (Show s : todos) {
                if (s.showId.equals(id)) {
                    vet[n++] = s.clone();
                    break;
                }
            }
        }


        long t0 = System.nanoTime();
        Show[] aux = new Show[n];
        mergeSort(vet, aux, 0, n - 1);
        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;


        for (int i = 0; i < n; i++) {
            vet[i].imprimir();
        }


        try (PrintWriter log = new PrintWriter(new FileWriter("874422_mergesort.txt"))) {
            log.printf("874422\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
        }
    }

    private static List<Show> lerCsv(String caminho) throws Exception {
        List<Show> lista = new ArrayList<>();
        List<String> linhas = Files.readAllLines(Paths.get(caminho));

        for (int i = 1; i < linhas.size(); i++) {
            lista.add(parseShow(linhas.get(i)));
        }
        return lista;
    }

    private static Show parseShow(String line) {
        List<String> campos = new ArrayList<>();
        boolean aspas = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                aspas = !aspas;
            } else if (c == ',' && !aspas) {
                campos.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        campos.add(sb.toString().trim());

        String id     = getOrNaN(campos, 0);
        String tipo   = getOrNaN(campos, 1);
        String tit    = getOrNaN(campos, 2);
        String dir    = getOrNaN(campos, 3);
        String[] el   = sortArray(getOrNaN(campos, 4));
        String pais   = getOrNaN(campos, 5);
        String data   = (campos.size() > 6 && !campos.get(6).isEmpty()) ? campos.get(6) : "March 1, 1900";
        int ano;
        try { ano = Integer.parseInt(getOrNaN(campos, 7)); }
        catch (Exception e) { ano = 0; }
        String nota   = getOrNaN(campos, 8);
        String dur    = getOrNaN(campos, 9);
        String[] cat  = sortArray(getOrNaN(campos, 10));

        return new Show(id, tipo, tit, dir, el, pais, data, ano, nota, dur, cat);
    }

    private static String getOrNaN(List<String> v, int i) {
        return (i >= v.size() || v.get(i).isEmpty()) ? "NaN" : v.get(i);
    }

    private static String[] sortArray(String f) {
        if ("NaN".equals(f)) return new String[0];
        String[] a = f.split(",\\s*");
        Arrays.sort(a);
        return a;
    }

    private static void mergeSort(Show[] arr, Show[] aux, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, aux, left, mid);
        mergeSort(arr, aux, mid + 1, right);

        //ordenandoo
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            comparacoes++;
            if (compareDuration(arr[i], arr[j]) <= 0) {
                aux[k++] = arr[i++];
            } else {
                aux[k++] = arr[j++];
            }
            movimentacoes++;
        }
        while (i <= mid) {
            aux[k++] = arr[i++];
            movimentacoes++;
        }
        while (j <= right) {
            aux[k++] = arr[j++];
            movimentacoes++;
        }
        for (k = left; k <= right; k++) {
            arr[k] = aux[k];
            movimentacoes++;
        }
    }

    private static int compareDuration(Show a, Show b) {
        int da = parseMinutes(a.duration);
        int db = parseMinutes(b.duration);
        if (da != db) return da - db;
        return a.title.compareTo(b.title);
    }

    private static int parseMinutes(String s) {
        try {
            return Integer.parseInt(s.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    static class Show implements Cloneable {
        String showId, type, title, director;
        String[] cast, listedIn;
        String country, dateAdded, rating, duration;
        int releaseYear;

        Show(String id, String t, String ti, String d,
             String[] c, String p, String da, int ry,
             String r, String du, String[] li) {
            showId      = id;
            type        = t;
            title       = ti;
            director    = d;
            cast        = c;
            country     = p;
            dateAdded   = da;
            releaseYear = ry;
            rating      = r;
            duration    = du;
            listedIn    = li;
        }

        void imprimir() {
            String castStr = (cast.length == 0) ? "[NaN]" : Arrays.toString(cast);
            String listStr = (listedIn.length == 0) ? "[NaN]" : Arrays.toString(listedIn);
            System.out.printf(
                "=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##%n",
                showId, title, type, director,
                castStr, country, dateAdded,
                releaseYear, rating, duration,
                listStr
            );
        }

        @Override
        protected Show clone() {
            return new Show(
                showId, type, title, director,
                Arrays.copyOf(cast, cast.length),
                country, dateAdded, releaseYear,
                rating, duration,
                Arrays.copyOf(listedIn, listedIn.length)
            );
        }
    }
}
