import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class tp02_q01 {

    public static class Show {
        private String id;
        private String type;
        private String title;
        private String director;
        private String[] cast;
        private String country;
        private LocalDate dateAdded;
        private int releaseYear;
        private String rating;
        private String duration;
        private String[] listedIn;

        // Construtor vazio
        public Show() {
            this.id = "";
            this.type = "";
            this.title = "";
            this.director = "";
            this.cast = new String[0];
            this.country = "";
            this.dateAdded = LocalDate.now();
            this.releaseYear = 0;
            this.rating = "";
            this.duration = "";
            this.listedIn = new String[0];
        }

        // Método para ler os dados de uma linha do CSV
        public void ler(String linha) {
            String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            // Usando trim e substituindo campos vazios por "NaN"
            this.id = partes[0].trim();
            this.type = partes[1].trim();
            this.title = partes[2].trim().isEmpty() ? "NaN" : partes[2].trim();
            this.director = partes[3].trim().isEmpty() ? "NaN" : partes[3].trim();

            // Cast
            if (!partes[4].trim().isEmpty()) {
                this.cast = partes[4].trim().split(", ");
            } else {
                this.cast = new String[] { "NaN" };
            }

            this.country = partes[5].trim().isEmpty() ? "NaN" : partes[5].trim();

            // Data
            if (!partes[6].trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", java.util.Locale.ENGLISH);
                this.dateAdded = LocalDate.parse(partes[6].trim(), formatter);
            } else {
                this.dateAdded = LocalDate.of(1111, 1, 1); // valor simbólico
            }

            // Ano de lançamento
            try {
                this.releaseYear = Integer.parseInt(partes[7].trim());
            } catch (Exception e) {
                this.releaseYear = 0;
            }

            this.rating = partes[8].trim().isEmpty() ? "NaN" : partes[8].trim();
            this.duration = partes[9].trim().isEmpty() ? "NaN" : partes[9].trim();

            // Listed In
            if (!partes[10].trim().isEmpty()) {
                this.listedIn = partes[10].trim().split(", ");
            } else {
                this.listedIn = new String[] { "NaN" };
            }
        }

        public void imprimir() {
            System.out.print("=> " + id + " ## " + type + " ## " + title + " ## " + director + " ## [");
            for (int i = 0; i < cast.length; i++) {
                System.out.print(cast[i]);
                if (i < cast.length - 1) System.out.print(", ");
            }
            System.out.print("] ## " + country + " ## " + dateAdded + " ## " + releaseYear + " ## ");
            System.out.print(rating + " ## " + duration + " ## [");
            for (int i = 0; i < listedIn.length; i++) {
                System.out.print(listedIn[i]);
                if (i < listedIn.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> ids = new ArrayList<>();

        // Lê entrada até "FIM"
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break;
            ids.add(linha.trim());
        }

        sc.close();

        ArrayList<Show> lista = new ArrayList<>();

        // Lê o arquivo
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        String linha = br.readLine(); // pula o cabeçalho

        while ((linha = br.readLine()) != null) {
            for (String id : ids) {
                if (linha.startsWith(id + ",")) {
                    Show s = new Show();
                    s.ler(linha);
                    lista.add(s);
                    break;
                }
            }
        }

        br.close();

        // Imprime os registros na ordem correta
        for (String id : ids) {
            for (Show s : lista) {
                if (s.id.equals(id)) {
                    s.imprimir();
                    break;
                }
            }
        }
    }
}
