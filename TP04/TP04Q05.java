import java.util.*;
import java.io.*;
import java.text.*;

class Hash {
    public int contReserva;
    public int tamReserva;
    public int tamTabela;
    public Show[] tabela;

    public Hash() {
        this.contReserva = 0;
        this.tamTabela = 21;
        this.tamReserva = 9;
        this.tabela = new Show[(tamReserva + tamTabela)];
    }

    public int parseToAscii(String x) {
        int resp = 0;
        for (int i = 0; i < x.length(); i++) {
            resp += (int) x.charAt(i);
        }
        return resp;
    }

    public int hash(String x) {
        return parseToAscii(x) % tamTabela;
    }

    public void inserir(Show x) {
        if (x == null) {
            throw new RuntimeException("Erro!");
        }

        int i = hash(x.getTitle());

        if (tabela[i] == null) {
            tabela[i] = x;
        } else {
            if (contReserva < tamReserva) {
                tabela[tamTabela + contReserva] = x;
                contReserva++;
            }
        }
    }

    public boolean pesquisa(String x, int[] comparacoes) {
        int i = hash(x);
        boolean resp = false;

        comparacoes[0]++;
        if (tabela[i] != null && tabela[i].getTitle().compareTo(x) == 0) {
            resp = true;
        } else {
            for (int j = tamTabela; j < (tamTabela + contReserva); j++) {
                comparacoes[0]++;
                if (tabela[j] != null && tabela[j].getTitle().compareTo(x) == 0) {
                    resp = true;
                    break;
                }
            }
        }

        System.out.println(" (Posicao: " + i + ") " + (resp ? "SIM" : "NAO"));
        return resp;
    }
}

class Show {
    private String showId;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDateAdded() { return dateAdded; }
    public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String[] getListedIn() { return listedIn; }
    public void setListedIn(String[] listedIn) { this.listedIn = listedIn; }

    public Show() {
        this.showId = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.dateAdded = null;
        this.releaseYear = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listedIn = new String[]{"NaN"};
    }

    public Show(String showId, String type, String title, String director, String[] cast,
                String country, String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = (showId != null && !showId.isEmpty()) ? showId : "NaN";
        this.type = (type != null && !type.isEmpty()) ? type : "NaN";
        this.title = (title != null && !title.isEmpty()) ? title : "NaN";
        this.director = (director != null && !director.isEmpty()) ? director : "NaN";
        this.cast = (cast != null && cast.length >= 0) ? cast : new String[]{"NaN"};
        this.country = (country != null && !country.isEmpty()) ? country : "NaN";
        SimpleDateFormat data = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            this.dateAdded = (dateAdded != null && !dateAdded.isEmpty()) ? data.parse(dateAdded) : null;
        } catch (ParseException e) {
            this.dateAdded = null;
        }
        this.releaseYear = (releaseYear > 0) ? releaseYear : -1;
        this.rating = (rating != null && !rating.isEmpty()) ? rating : "NaN";
        this.duration = (duration != null && !duration.isEmpty()) ? duration : "NaN";
        this.listedIn = (listedIn != null && listedIn.length >= 0) ? listedIn : new String[]{"NaN"};
    }

    public void imprimir() {
        SimpleDateFormat data = new SimpleDateFormat("MMMM d, yyyy");
        System.out.print("=> " + getShowId());
        System.out.print(" ## " + getTitle());
        System.out.print(" ## " + getType());
        System.out.print(" ## " + getDirector());
        System.out.print(" ## " + Arrays.toString(getCast()));
        System.out.print(" ## " + getCountry());
        System.out.print(" ## " + (getDateAdded() != null ? data.format(getDateAdded()) : "NaN"));
        System.out.print(" ## " + (getReleaseYear() != -1 ? getReleaseYear() : "NaN"));
        System.out.print(" ## " + getRating());
        System.out.print(" ## " + getDuration());
        System.out.println(" ## " + Arrays.toString(getListedIn()) + " ##");
    }

    public static ArrayList<Show> ler() {
        ArrayList<Show> listaShow = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader("/tmp/disneyplus.csv"))) {
            String linha = "";
            r.readLine(); // cabeçalho
            while ((linha = r.readLine()) != null) {
                Show show = new Show();
                show.atribuir(linha);
                listaShow.add(show);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaShow;
    }

    public void atribuir(String linha) {
        String[] str = linhas(linha);
        SimpleDateFormat formato = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        setShowId(str[0] != null ? str[0] : "NaN");
        setType(str[1] != null ? str[1] : "NaN");
        setTitle(str[2] != null ? str[2] : "NaN");
        setDirector(str[3] != null ? str[3] : "NaN");
        setCast(str[4] != null ? ordenar(str[4]) : new String[]{"NaN"});
        setCountry(str[5] != null ? str[5] : "NaN");
        try {
            if (str[6] != null && !str[6].isEmpty()) {
                setDateAdded(formato.parse(str[6]));
            } else {
                setDateAdded(null);
            }
        } catch (Exception e) {
            setDateAdded(null);
        }
        setReleaseYear(str[7] != null ? Integer.parseInt(str[7]) : -1);
        setRating(str[8] != null ? str[8] : "NaN");
        setDuration(str[9] != null ? str[9] : "NaN");
        setListedIn(str[10] != null ? ordenar(str[10]) : new String[]{"NaN"});
    }

    public static String[] ordenar(String str) {
        String[] array = str.split(", ");
        Arrays.sort(array);
        return array;
    }

    public static String[] linhas(String linha) {
        String[] str = new String[11];
        Arrays.fill(str, "");
        int aux = 0, i = 0;
        while (i < linha.length() && aux != 11) {
            char letra = linha.charAt(i);
            if (letra == ',' && (i + 1 >= linha.length() || linha.charAt(i + 1) != ' ')) {
                if (str[aux].isEmpty()) str[aux] = null;
                aux++;
            } else {
                if (letra == '"') {
                    i++;
                    while (i < linha.length() && linha.charAt(i) != '"') {
                        str[aux] += linha.charAt(i);
                        i++;
                    }
                } else {
                    str[aux] += letra;
                }
            }
            i++;
        }
        return str;
    }

    public static void arquivoLog(double duracao, int[] comparacoes) {
        String matricula = "874422";
        try {
            PrintWriter w = new PrintWriter(matricula + "_hashReserva.txt");
            w.printf("%s\t%d\t%fms", matricula, comparacoes[0], duracao);
            w.close();
        } catch (IOException e) {
            System.err.println("Erro" + e.getMessage());
        }
    }
}

public class TP04Q05 {
    public static void main(String[] args) {
        int[] comparacoes = {0};
        Scanner input = new Scanner(System.in);
        ArrayList<Show> listaShow = Show.ler();

        String id = input.nextLine();
        Hash tabelaHash = new Hash();

        while (!id.equals("FIM")) {
            for (Show s : listaShow) {
                if (s.getShowId().equals(id)) {
                    tabelaHash.inserir(s);
                    break;
                }
            }
            id = input.nextLine();
        }

        String[] listaPesquisa = new String[100];
        int j = 0;
        String titulo = input.nextLine();

        while (!titulo.equals("FIM")) {
            listaPesquisa[j++] = titulo;
            titulo = input.nextLine();
        }

        long inicioTempo = System.nanoTime();
        for (int i = 0; i < j; i++) {
            tabelaHash.pesquisa(listaPesquisa[i], comparacoes);
        }
        long fimTempo = System.nanoTime();

        double duracao = (fimTempo - inicioTempo) / 1_000_000.0;
        Show.arquivoLog(duracao, comparacoes);
        input.close();
    }
}
