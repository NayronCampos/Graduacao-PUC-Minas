

 import java.io.*;
 import java.util.*;
 
 public class TP2Q07 {
 
     private static int comparacoes = 0;
     private static int movimentacoes = 0;
 
     public static void main(String[] args) throws Exception {
         List<Show> all = loadCSV("/tmp/disneyplus.csv");
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
         String line;
         List<String> ids = new ArrayList<>();
         while ((line = br.readLine()) != null && !line.equals("FIM")) {
             ids.add(line);
         }
         br.close();
 
         Show[] arr = new Show[ids.size()];
         int idx = 0;
         for (String id : ids) {
             for (Show s : all) {
                 if (s.showId.equals(id)) {
                     arr[idx++] = s.clone();
                     break;
                 }
             }
         }
 
         long t0 = System.nanoTime();
         // Insertion sort by type, tie-breaker title
         for (int i = 1; i < idx; i++) {
             Show key = arr[i];
             int j = i - 1;
             while (j >= 0) {
                 comparacoes++;
                 int cmpType = arr[j].type.compareTo(key.type);
                 int cmp = (cmpType != 0) ? cmpType : arr[j].title.compareTo(key.title);
                 if (cmp > 0) {
                     arr[j + 1] = arr[j];
                     movimentacoes++;
                     j--;
                 } else {
                     break;
                 }
             }
             arr[j + 1] = key;
             movimentacoes++;
         }
         long t1 = System.nanoTime();
         double tempo = (t1 - t0) / 1e9;
 
         for (int i = 0; i < idx; i++) {
             arr[i].imprimir();
         }
 
         try (PrintWriter log = new PrintWriter(new FileWriter("874422 insercao.txt"))) {
             log.printf("874422\t%d\t%d\t%.6f%n", comparacoes, movimentacoes, tempo);
         }
     }
 
     private static List<Show> loadCSV(String path) throws IOException {
         List<Show> list = new ArrayList<>();
         try (BufferedReader br = new BufferedReader(new FileReader(path))) {
             String line = br.readLine();
             while ((line = br.readLine()) != null) {
                 list.add(parseShow(line));
             }
         }
         return list;
     }
 
     private static Show parseShow(String line) {
         List<String> parts = new ArrayList<>();
         boolean inQuotes = false;
         StringBuilder sb = new StringBuilder();
         for (char c : line.toCharArray()) {
             if (c == '"') inQuotes = !inQuotes;
             else if (c == ',' && !inQuotes) {
                 parts.add(sb.toString().trim());
                 sb.setLength(0);
             } else {
                 sb.append(c);
             }
         }
         parts.add(sb.toString().trim());
 
         String showId    = getOrNaN(parts, 0);
         String type      = getOrNaN(parts, 1);
         String title     = getOrNaN(parts, 2);
         String director  = getOrNaN(parts, 3);
         String[] cast    = sortArray(getOrNaN(parts, 4));
         String country   = getOrNaN(parts, 5);
         String dateAdded = (parts.size() > 6 && !parts.get(6).isEmpty()) ? parts.get(6) : "March 1, 1900";
         int year;
         try {
             year = Integer.parseInt(getOrNaN(parts, 7));
         } catch (NumberFormatException e) {
             year = 0;
         }
         String rating   = getOrNaN(parts, 8);
         String duration = getOrNaN(parts, 9);
         String[] listed = sortArray(getOrNaN(parts, 10));
 
         return new Show(showId, type, title, director, cast, country, dateAdded, year, rating, duration, listed);
     }
 
     private static String getOrNaN(List<String> p, int idx) {
         return (idx >= p.size() || p.get(idx).isEmpty()) ? "NaN" : p.get(idx);
     }
 
     private static String[] sortArray(String field) {
         if ("NaN".equals(field)) return new String[0];
         String[] arr = field.split(",\\s*");
         Arrays.sort(arr);
         return arr;
     }
 
     static class Show implements Cloneable {
         String showId, type, title, director;
         String[] cast, listedIn;
         String country, dateAdded, rating, duration;
         int releaseYear;
 
         Show(String showId, String type, String title, String director,
              String[] cast, String country, String dateAdded,
              int releaseYear, String rating, String duration, String[] listedIn) {
             this.showId = showId; this.type = type; this.title = title;
             this.director = director; this.cast = cast; this.country = country;
             this.dateAdded = dateAdded; this.releaseYear = releaseYear;
             this.rating = rating; this.duration = duration; this.listedIn = listedIn;
         }
 
         void imprimir() {
             System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s%n",
                 showId, title, type, director,
                 Arrays.toString(cast), country, dateAdded,
                 releaseYear, rating, duration, Arrays.toString(listedIn));
         }
 
         @Override
         protected Show clone() {
             return new Show(showId, type, title, director,
                 Arrays.copyOf(cast, cast.length), country, dateAdded,
                 releaseYear, rating, duration, Arrays.copyOf(listedIn, listedIn.length));
         }
     }
 }
 