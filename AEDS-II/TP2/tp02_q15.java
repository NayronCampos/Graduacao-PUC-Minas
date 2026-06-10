import java.io.File;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class tp02_q15 {

    public static void main(String[] args) throws Exception {

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        List<Show> catalogo = carregar("/tmp/disneyplus.csv");

        Scanner sc = new Scanner(System.in, "UTF-8");
        List<Show> consulta = new ArrayList<>();
        while (sc.hasNextLine()) {
            String id = sc.nextLine();
            if (id.equals("FIM")) break;
            for (Show s : catalogo) {
                if (s.id.equals(id)) {
                    consulta.add(s);
                    break;
                }
            }
        }
        sc.close();

        ordenarParcial(consulta, 10);

        for (int i = 0; i < 10 && i < consulta.size(); i++) {
            consulta.get(i).imprimir();
        }
    }


    private static void ordenarParcial(List<Show> v, int k) {
        int n = v.size();
        for (int i = 0; i < k && i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (v.get(j).titulo.compareTo(v.get(min).titulo) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                Collections.swap(v, i, min);
            }
        }
    }

    private static List<Show> carregar(String caminho) throws Exception {
        List<Show> lista = new ArrayList<>();
        Scanner sc = new Scanner(new File(caminho), "UTF-8");
        if (sc.hasNextLine()) sc.nextLine(); 
        while (sc.hasNextLine()) {
            String linha = sc.nextLine().trim();
            if (!linha.isEmpty()) lista.add(parse(linha));
        }
        sc.close();
        return lista;
    }


    private static Show parse(String linha) {

        String[] f = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < f.length; i++) {
            f[i] = f[i].trim();
            if (f[i].startsWith("\"") && f[i].endsWith("\""))
                f[i] = f[i].substring(1, f[i].length()-1);
            if (f[i].isEmpty()) f[i] = "NaN";
        }
        String id       = f[0],
               tipo     = f[1],
               titulo   = f[2],
               diretor  = f[3],
               pais     = f[5],
               dataTxt  = f[6],
               nota     = f[8],
               duracao  = f[9];
        int ano;
        try { ano = Integer.parseInt(f[7]); }
        catch (Exception e) { ano = 0; }
        String[] elenco = montaArray(f[4]),
                 cats   = montaArray(f[10]);
        Date dt;
        try {
            dt = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(dataTxt);
        } catch(ParseException e) {
            try {
                dt = new SimpleDateFormat("dd/MM/yyyy").parse(dataTxt);
            } catch(Exception ex) {
                dt = new Date(0);
            }
        }
        return new Show(id, tipo, titulo, diretor, elenco, pais, dt, ano, nota, duracao, cats);
    }


    private static String[] montaArray(String s) {
        if (s.equals("NaN")) return new String[] { "NaN" };
        String[] t = s.split(",");
        for (int i = 0; i < t.length; i++) t[i] = t[i].trim();
        Arrays.sort(t);
        return t;
    }


    static class Show {
        String id, tipo, titulo, diretor;
        String[] elenco, cats;
        String pais, nota, duracao;
        Date data;
        int ano;

        Show(String id, String tipo, String titulo, String diretor,
             String[] elenco, String pais, Date data, int ano,
             String nota, String duracao, String[] cats) {
            this.id = id; this.tipo = tipo; this.titulo = titulo;
            this.diretor = diretor; this.elenco = elenco;
            this.pais = pais; this.data = data; this.ano = ano;
            this.nota = nota; this.duracao = duracao; this.cats = cats;
        }

        void imprimir() {
            String dt = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).format(data);
            System.out.printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##%n",
                id,
                titulo,
                tipo,
                diretor,
                String.join(", ", elenco),
                pais,
                dt,
                ano,
                nota,
                duracao,
                String.join(", ", cats)
            );
        }
    }
}
