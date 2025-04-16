import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Show {
 private String showId, type, title, director, country, rating, duration;
 private String[] cast, listedIn;
  private Date dateAdded;
   private int releaseYear;

    public Show() {
     showId = type = title = director = country = rating = duration = "NaN";
      cast = new String[] { "NaN" };
       listedIn = new String[] { "NaN" };
      dateAdded = null;
  releaseYear = -1;
    }

    public void ler(String linha) {
        String[] f = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        showId   = f.length > 0 && !f[0].trim().isEmpty() ? f[0].trim() : "NaN";
        type     = f.length > 1 && !f[1].trim().isEmpty() ? f[1].trim() : "NaN";
        title    = f.length > 2 && !f[2].trim().isEmpty() ? f[2].trim().replace("\"", "") : "NaN";
        director = f.length > 3 && !f[3].trim().isEmpty() ? f[3].trim() : "NaN";
        if(f.length > 4 && !f[4].trim().isEmpty()) {
            String aux = f[4].replaceAll("[\\[\\]]", "").trim();
            cast = aux.isEmpty() ? new String[] { "NaN" } : aux.split(",\\s*");
            for (int i = 0; i < cast.length; i++) {
    cast[i] = cast[i].replace("\"", "");
     }

        } else {
            cast = new String[] { "NaN" };
        }
        country = f.length > 5 && !f[5].trim().isEmpty() ? f[5].trim() : "NaN";

        // Trata o campo da data; remove aspas se houver
 String dataStr = "";
        if(f.length > 6 && !f[6].trim().isEmpty()){
            dataStr = f[6].replace("\"", "").trim();
        }
 SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
    try {
            dateAdded = !dataStr.isEmpty() ? sdf.parse(dataStr) : null;
        } catch(ParseException e) {
            dateAdded = null;
        }
        releaseYear = f.length > 7 && !f[7].trim().isEmpty() ? Integer.parseInt(f[7].trim()) : -1;
        rating  = f.length > 8 && !f[8].trim().isEmpty() ? f[8].trim() : "NaN";
        duration = f.length > 9 && !f[9].trim().isEmpty() ? f[9].trim() : "NaN";
        if(f.length > 10 && !f[10].trim().isEmpty()) {
        String aux = f[10].replaceAll("[\\[\\]]", "").trim();
            listedIn = aux.isEmpty() ? new String[] { "NaN" } : aux.split(",\\s*");
for (int i = 0; i < listedIn.length; i++) {
    listedIn[i] = listedIn[i].replace("\"", "");
}

        } else {
            listedIn = new String[] { "NaN" };
        }
    }

    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
      String data = dateAdded != null ? sdf.format(dateAdded) : "NaN";
    System.out.println("=> " + showId + " ## " + title + " ## " + type + " ## " + director + " ## " + "[" + String.join(", ", cast) + "]" + " ## " + country + " ## " + data + " ## " + releaseYear + " ## " + rating + " ## " + duration  + " ## " + "[" + String.join(", ", listedIn) + "] ##");
    }

    public static ArrayList<Show> lerArquivo() {
        ArrayList<Show> lista = new ArrayList<>();
        try {
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
         br.readLine(); // pular cabeçalho
        String linha;
 while ((linha = br.readLine()) != null) {
                Show s = new Show();
                s.ler(linha);
                lista.add(s);
 }
            br.close();
        } catch (Exception e) { }
        return lista;
    }

    public static void main(String[] args) {
  ArrayList<Show> shows = lerArquivo();
 Scanner sc = new Scanner(System.in);
 String id = sc.nextLine();
        while (!id.equals("FIM")) {
      boolean achou = false;
            for (Show s : shows) {
                if (s.showId.equals(id)) {
                 s.imprimir();
                 achou = true;
                 break;
                }
            }
            if (!achou) System.out.println("ID não encontrado: " + id);
         id = sc.nextLine();
        }
        sc.close();
    }
}
