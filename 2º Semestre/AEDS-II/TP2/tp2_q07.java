import java.io.*;
import java.util.*;

public class tp2_q07 {

    private static int comparacoes = 0;
    private static int movimentacoes = 0;

    public static void main(String[] args) throws Exception {
        List<Show> lista = lerCsv("/tmp/disneyplus.csv");
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
            for (Show s : lista) {
                if (s.showId.equals(id)) {
                    vet[n++] = s.clone();
                    break;
                }
            }
        }

        long t0 = System.nanoTime();
        ordenarInsercao(vet, n);
        long t1 = System.nanoTime();
        double tempo = (t1 - t0) / 1e9;

        for (int i = 0; i < n; i++) {
            vet[i].imprimir();
        }

        try (PrintWriter log = new PrintWriter(new FileWriter("874422 insercao.txt"))) {
            log.printf("874422\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
        }
    }

    private static List<Show> lerCsv(String caminho) throws IOException {
        List<Show> res = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        br.readLine(); // pula cabeçalho
        String linha;
        while ((linha = br.readLine()) != null) {
            res.add(criarShow(linha));
        }
        br.close();
        return res;
    }

    private static Show criarShow(String linha) {
        List<String> campos = new ArrayList<>();
        boolean aspas = false;
        StringBuilder sb = new StringBuilder();
        for (char c : linha.toCharArray()) {
            if (c == '"') aspas = !aspas;
            else if (c == ',' && !aspas) {
                campos.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        campos.add(sb.toString().trim());

        String id     = pegaOuNaN(campos, 0);
        String tipo   = pegaOuNaN(campos, 1);
        String tit    = pegaOuNaN(campos, 2);
        String dir    = pegaOuNaN(campos, 3);
        String[] el   = ordenaArray(pegaOuNaN(campos, 4));
        String pais   = pegaOuNaN(campos, 5);
        String data   = (campos.size()>6 && !campos.get(6).isEmpty())
                          ? campos.get(6) : "March 1, 1900";
        int ano;
        try { ano = Integer.parseInt(pegaOuNaN(campos, 7)); }
        catch (Exception e) { ano = 0; }
        String nota   = pegaOuNaN(campos, 8);
        String dur    = pegaOuNaN(campos, 9);
        String[] cat  = ordenaArray(pegaOuNaN(campos, 10));

        return new Show(id, tipo, tit, dir, el, pais, data, ano, nota, dur, cat);
    }

    private static String pegaOuNaN(List<String> v, int i) {
        return (i >= v.size() || v.get(i).isEmpty()) ? "NaN" : v.get(i);
    }

    private static String[] ordenaArray(String f) {
        if ("NaN".equals(f)) return new String[0];
        String[] a = f.split(",\\s*");
        Arrays.sort(a);
        return a;
    }

    private static void ordenarInsercao(Show[] a, int n) {
        for (int i = 1; i < n; i++) {
            Show tmp = a[i];
            int j = i - 1;
            while (j >= 0) {
                comparacoes++;
                int cmp1 = a[j].type.compareTo(tmp.type);
                int cmp  = (cmp1 != 0) ? cmp1 : a[j].title.compareTo(tmp.title);
                if (cmp > 0) {
                    a[j + 1] = a[j];
                    movimentacoes++;
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = tmp;
            movimentacoes++;
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
            showId = id; type = t; title = ti; director = d;
            cast = c; country = p; dateAdded = da;
            releaseYear = ry; rating = r; duration = du;
            listedIn = li;
        }

        void imprimir() {
            String castStr = (cast.length == 0)
                ? "[NaN]"
                : Arrays.toString(cast);
            String listStr = (listedIn.length == 0)
                ? "[NaN]"
                : Arrays.toString(listedIn);
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
