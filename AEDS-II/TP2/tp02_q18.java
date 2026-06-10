import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class tp02_q18 {

    public static void main(String[] args) throws Exception {
        // 1) Carrega todo o catálogo
        List<Show> catalog = lerCsv("/tmp/disneyplus.csv");

        // 2) Ordena globalmente por title, ignorando prefixos não-alfabéticos
        Comparator<Show> cmpTitle = Comparator.comparing(
            s -> normalize(s.title),
            String::compareToIgnoreCase
        );
        catalog.sort(cmpTitle);

        // 3) Lê IDs até "FIM"
        Scanner sc = new Scanner(System.in, "UTF-8");
        Set<String> wanted = new HashSet<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("FIM")) break;
            wanted.add(line);
        }
        sc.close();

        // 4) Varre o catálogo ordenado e pega os primeiros 10 que estão em 'wanted'
        int printed = 0;
        for (Show s : catalog) {
            if (wanted.contains(s.showId)) {
                s.imprimir();
                if (++printed == 10) break;
            }
        }
    }

    private static String normalize(String title) {
        // elimina todos os caracteres iniciais que não sejam letras
        int i = 0, n = title.length();
        while (i < n && !Character.isLetter(title.charAt(i))) i++;
        return title.substring(i);
    }

    private static List<Show> lerCsv(String path) throws Exception {
        List<Show> res = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 1; i < lines.size(); i++) {
            res.add(parseShow(lines.get(i)));
        }
        return res;
    }

    private static Show parseShow(String line) {
        List<String> f = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') inQuotes = !inQuotes;
            else if (c == ',' && !inQuotes) {
                f.add(sb.toString().trim());
                sb.setLength(0);
            } else sb.append(c);
        }
        f.add(sb.toString().trim());

        String id      = getOrNaN(f,0),
               type    = getOrNaN(f,1),
               title   = getOrNaN(f,2),
               dir     = getOrNaN(f,3),
               castF   = getOrNaN(f,4),
               country = getOrNaN(f,5),
               date    = (f.size()>6 && !f.get(6).isEmpty()) ? f.get(6) : "March 1, 1900",
               rating  = getOrNaN(f,8),
               duration= getOrNaN(f,9),
               listedF = getOrNaN(f,10);
        int year = 0;
        try { year = Integer.parseInt(getOrNaN(f,7)); } catch(Exception e){}

        return new Show(
            id,
            type,
            title,
            dir,
            splitAndSort(castF),
            country,
            date,
            year,
            rating,
            duration,
            splitAndSort(listedF)
        );
    }

    private static String getOrNaN(List<String> v, int i) {
        return i >= v.size() || v.get(i).isEmpty() ? "NaN" : v.get(i);
    }

    private static String[] splitAndSort(String field) {
        if ("NaN".equals(field)) return new String[0];
        String[] a = field.split(",\\s*");
        Arrays.sort(a, String.CASE_INSENSITIVE_ORDER);
        return a;
    }

    static class Show {
        String showId, type, title, director;
        String[] cast, listedIn;
        String country, dateAdded, rating, duration;
        int releaseYear;

        Show(String id, String t, String ti, String d,
             String[] c, String p, String da, int ry,
             String r, String du, String[] li) {
            showId     = id;
            type       = t;
            title      = ti;
            director   = d;
            cast       = c;
            country    = p;
            dateAdded  = da;
            releaseYear= ry;
            rating     = r;
            duration   = du;
            listedIn   = li;
        }

        void imprimir() {
            String cs = cast.length==0 ? "[NaN]" : Arrays.toString(cast);
            String ls = listedIn.length==0 ? "[NaN]" : Arrays.toString(listedIn);
            System.out.printf(
              "=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##%n",
              showId, title, type, director,
              cs, country, dateAdded,
              releaseYear, rating, duration,
              ls
            );
        }
    }
}
