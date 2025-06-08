import java.io.*;
import java.util.*;

public class TP3Q09 {

    public static class Programa {
        public String id, tipo, titulo, diretor, pais, dataAdicao, classificacao, duracao;
        public String[] elenco;
        public String[] categorias;
        public int anoLancamento;

        public Programa(String linha) {
            String[] partes = dividirLinhaCSV(linha);
            id = partes[0];
            tipo = partes[1];
            titulo = partes[2];
            diretor = partes[3];
            elenco = partes[4].split(", ");
            pais = partes[5];
            dataAdicao = partes[6];
            anoLancamento = Integer.parseInt(partes[7]);
            classificacao = partes[8];
            duracao = partes[9];
            categorias = partes[10].split(", ");
        }

        private String[] dividirLinhaCSV(String linha) {
            ArrayList<String> campos = new ArrayList<>();
            boolean dentroAspas = false;
            StringBuilder atual = new StringBuilder();
            for (int i = 0; i < linha.length(); i++) {
                char c = linha.charAt(i);
                if (c == '"') {
                    dentroAspas = !dentroAspas;
                } else if (c == ',' && !dentroAspas) {
                    campos.add(atual.toString());
                    atual = new StringBuilder();
                } else {
                    atual.append(c);
                }
            }
            campos.add(atual.toString());
            return campos.toArray(new String[0]);
        }

        public String formatado() {
            return "[" + id + "] ## " + titulo + " ## " + tipo + " ## " + diretor + " ## " +
                Arrays.toString(elenco).replaceAll("\\[|\\]", "") + " ## " + pais + " ## " + dataAdicao + " ## " +
                anoLancamento + " ## " + classificacao + " ## " + duracao + " ## " +
                Arrays.toString(categorias).replaceAll("\\[|\\]", "") + " ##";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader leitor = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        ArrayList<Programa> listaProgramas = new ArrayList<>();

        String linha;
        leitor.readLine(); // cabeçalho
        while ((linha = leitor.readLine()) != null) {
            listaProgramas.add(new Programa(linha));
        }
        leitor.close();

        Scanner sc = new Scanner(System.in);
        ArrayList<Programa> encontrados = new ArrayList<>();

        while (sc.hasNext()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;
            for (Programa p : listaProgramas) {
                if (p.titulo.equals(entrada)) {
                    encontrados.add(p);
                    break;
                }
            }
        }

        Collections.sort(encontrados, Comparator.comparing(p -> p.titulo));

        for (Programa p : encontrados) {
            System.out.println("(R) " + p.titulo);
        }

        sc.close();
    }
}
