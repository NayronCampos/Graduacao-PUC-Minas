import java.io.*;
import java.util.*;

class Show {
    String id, type, title, director, country, dateAdded, rating, duration;
    String[] cast, listedIn;
    int releaseYear;

    public Show() {
        id = type = title = director = country = dateAdded = rating = duration = "NaN";
        cast = listedIn = new String[0];
        releaseYear = 0;
    }

   public void imprimir() {
    System.out.print("=> " + id + " ## " + title + " ## " + type + " ## " + director + " ## [");
    for (int i = 0; i < cast.length; i++) {
        System.out.print(cast[i].trim());
        if (i < cast.length - 1) System.out.print(", ");
    }
    System.out.print("] ## " + country + " ## " + dateAdded + " ## ");
    System.out.print(releaseYear + " ## " + rating + " ## " + duration + " ## [");
    for (int i = 0; i < listedIn.length; i++) {
        System.out.print(listedIn[i].trim());
        if (i < listedIn.length - 1) System.out.print(", ");
    }
    System.out.println("] ##");
}


    public Show clone() {
        Show c = new Show();
        c.id = id;
        c.type = type;
        c.title = title;
        c.director = director;
        c.cast = cast.clone();
        c.country = country;
        c.dateAdded = dateAdded;
        c.releaseYear = releaseYear;
        c.rating = rating;
        c.duration = duration;
        c.listedIn = listedIn.clone();
        return c;
    }

    public static Show fromCSV(String line) {
        Show show = new Show();
        String[] fields = splitCSV(line);

        show.id = getField(fields, 0);
        show.type = getField(fields, 1);
        show.title = getField(fields, 2);
        show.director = getField(fields, 3);
        show.cast = ordenar(getField(fields, 4).split(","));
        show.country = getField(fields, 5);
        show.dateAdded = getField(fields, 6).isEmpty() ? "March 1, 1900" : getField(fields, 6);
        show.releaseYear = getField(fields, 7).isEmpty() ? 0 : Integer.parseInt(getField(fields, 7));
        show.rating = getField(fields, 8);
        show.duration = getField(fields, 9);
        show.listedIn = ordenar(getField(fields, 10).split(","));

        return show;
    }

    private static String[] ordenar(String[] array) {
        Arrays.sort(array, Comparator.comparing(String::trim));
        return array;
    }

  private static String getField(String[] fields, int index) {
    if (index < fields.length) {
        String f = fields[index].trim().replaceAll("^\"|\"$", "").replaceAll("\"\"", "\"");
        return f.isEmpty() ? "NaN" : f;
    }
    return "NaN";
}


    private static String[] splitCSV(String line) {
        List<String> campos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean entreAspas = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                entreAspas = !entreAspas;
            } else if (c == ',' && !entreAspas) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        campos.add(atual.toString());
        return campos.toArray(new String[0]);
    }
}

class ListaSequencial {
    private Show[] array;
    private int n;

    public ListaSequencial() {
        this(1000);
    }

    public ListaSequencial(int tamanho) {
        array = new Show[tamanho];
        n = 0;
    }

    public void inserirInicio(Show show) {
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = show;
        n++;
    }

    public void inserirFim(Show show) {
        array[n++] = show;
    }

    public void inserir(Show show, int pos) {
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = show;
        n++;
    }

    public Show removerInicio() {
        Show temp = array[0];
        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return temp;
    }

    public Show removerFim() {
        return array[--n];
    }

    public Show remover(int pos) {
        Show temp = array[pos];
        for (int i = pos; i < n - 1; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return temp;
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            array[i].imprimir();
        }
    }
}

public class TP3Q01 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in, "UTF-8");
        Map<String, Show> base = carregarBase("/tmp/disneyplus.csv");
        ListaSequencial lista = new ListaSequencial();

        // Inserção inicial
        while (sc.hasNext()) {
            String id = sc.nextLine().trim();
            if (id.equals("FIM")) break;
            Show s = base.get(id);
            if (s != null) lista.inserirFim(s.clone());
        }

        // Processa comandos
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String[] comando = sc.nextLine().split(" ");
            switch (comando[0]) {
                case "II":
                    lista.inserirInicio(base.get(comando[1]).clone());
                    break;
                case "IF":
                    lista.inserirFim(base.get(comando[1]).clone());
                    break;
                case "I*":
                    lista.inserir(base.get(comando[2]).clone(), Integer.parseInt(comando[1]));
                    break;
                case "RI":
                    System.out.println("(R) " + lista.removerInicio().title);
                    break;
                case "RF":
                    System.out.println("(R) " + lista.removerFim().title);
                    break;
                case "R*":
                    System.out.println("(R) " + lista.remover(Integer.parseInt(comando[1])).title);
                    break;
            }
        }

        // Exibe resultado final
        lista.mostrar();
        sc.close();
    }

    public static Map<String, Show> carregarBase(String caminho) throws IOException {
        Map<String, Show> mapa = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"))) {
            br.readLine(); // Ignora cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                Show s = Show.fromCSV(linha);
                mapa.put(s.id, s);
            }
        }
        return mapa;
    }
}
