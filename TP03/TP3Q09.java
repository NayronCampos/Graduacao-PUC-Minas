import java.io.*;
import java.util.*;

public class TP3Q09 {

    public static class Programa {
        String id, tipo, titulo, diretor, pais, dataAdicao, classificacao, duracao;
        String[] elenco;
        String[] categorias;
        int anoLancamento;

        public Programa(String linha) {
            String[] partes = dividirCSV(linha);
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

        private String[] dividirCSV(String linha) {
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

    public static class Celula {
        Programa elemento;
        Celula prox;

        Celula(Programa elemento) {
            this.elemento = elemento;
            this.prox = null;
        }
    }

    public static class Pilha {
        private Celula topo;

        public Pilha() {
            topo = null;
        }

        public void empilhar(Programa p) {
            Celula nova = new Celula(p);
            nova.prox = topo;
            topo = nova;
        }

        public Programa desempilhar() throws Exception {
            if (topo == null) throw new Exception("Pilha vazia");
            Programa p = topo.elemento;
            topo = topo.prox;
            return p;
        }

        public void mostrar() {
            mostrarRec(topo);
        }

        private void mostrarRec(Celula c) {
            if (c != null) {
                mostrarRec(c.prox);
                System.out.println(c.elemento.formatado());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        ArrayList<Programa> base = new ArrayList<>();
        String linha;
        br.readLine(); // cabeçalho
        while ((linha = br.readLine()) != null) {
            base.add(new Programa(linha));
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        Pilha pilha = new Pilha();

        while (sc.hasNextLine()) {
            String comando = sc.nextLine();
            if (comando.equals("FIM")) break;
            if (comando.startsWith("I ")) {
                String titulo = comando.substring(2);
                for (Programa p : base) {
                    if (p.titulo.equals(titulo)) {
                        pilha.empilhar(p);
                        break;
                    }
                }
            } else if (comando.equals("R")) {
                Programa removido = pilha.desempilhar();
                System.out.println("(R) " + removido.titulo);
            }
        }

        pilha.mostrar();
        sc.close();
    }
}
