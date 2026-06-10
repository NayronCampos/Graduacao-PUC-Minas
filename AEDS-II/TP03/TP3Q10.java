import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Programa {
    String codigo, tipo, nome, diretor, elenco, origem, dataEntrada, classificacao, duracao, categorias;
    int anoLancamento;

    public Programa(String codigo, String tipo, String nome, String diretor,
                    String elenco, String origem, String dataEntrada,
                    int anoLancamento, String classificacao, String duracao, String categorias) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.nome = nome;
        this.diretor = diretor;
        this.elenco = ordenarCampo(elenco);
        this.origem = origem;
        this.dataEntrada = (dataEntrada == null || dataEntrada.isEmpty() || dataEntrada.equals("NaN"))
                            ? "March 1, 1900" : dataEntrada;
        this.anoLancamento = anoLancamento;
        this.classificacao = classificacao;
        this.duracao = duracao;
        this.categorias = ordenarCampo(categorias);
    }

    private String ordenarCampo(String campo) {
        if (campo == null || campo.equals("NaN") || campo.trim().isEmpty()) {
            return "";
        }
        String[] partes = campo.split(",\\s*");
        Arrays.sort(partes, String.CASE_INSENSITIVE_ORDER);
        return String.join(", ", partes);
    }

    public void mostrar() {
        System.out.printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
                codigo, nome, tipo, diretor, elenco, origem, dataEntrada,
                anoLancamento, classificacao, duracao, categorias);
    }

    public LocalDate obterData() {
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
            return LocalDate.parse(dataEntrada, formato);
        } catch (Exception e) {
            return LocalDate.of(1900, 1, 1);
        }
    }
}

class Celula {
    Programa valor;
    Celula anterior, proximo;

    Celula(Programa prog) {
        valor = prog;
    }
}

public class TP3Q10 {
    static Celula inicio, fim;
    static long comparacoes = 0;

    public static void inserirFinal(Programa prog) {
        Celula nova = new Celula(prog);
        if (inicio == null) {
            inicio = fim = nova;
        } else {
            fim.proximo = nova;
            nova.anterior = fim;
            fim = nova;
        }
    }

    public static Celula dividir(Celula esquerda, Celula direita) {
        Programa pivo = direita.valor;
        LocalDate dataPivo = pivo.obterData();
        Celula i = esquerda.anterior;

        for (Celula j = esquerda; j != direita; j = j.proximo) {
            comparacoes++;
            LocalDate dataAtual = j.valor.obterData();
            if (dataAtual.isBefore(dataPivo) || 
                (dataAtual.isEqual(dataPivo) && j.valor.nome.compareTo(pivo.nome) <= 0)) {
                i = (i == null) ? esquerda : i.proximo;
                Programa temp = i.valor;
                i.valor = j.valor;
                j.valor = temp;
            }
        }

        i = (i == null) ? esquerda : i.proximo;
        Programa temp = i.valor;
        i.valor = direita.valor;
        direita.valor = temp;
        return i;
    }

    public static void quickSortInterno(Celula esquerda, Celula direita) {
        if (direita != null && esquerda != direita && esquerda != direita.proximo) {
            Celula p = dividir(esquerda, direita);
            quickSortInterno(esquerda, p.anterior);
            quickSortInterno(p.proximo, direita);
        }
    }

    public static void ordenar() {
        quickSortInterno(inicio, fim);
    }

    public static void carregarArquivo(String caminho, List<Programa> lista) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha = br.readLine();
        while ((linha = br.readLine()) != null) {
            if (linha.trim().isEmpty()) continue;

            String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            for (int i = 0; i < campos.length; i++) {
                campos[i] = campos[i].replaceAll("\"", "");
            }

            String id = campos.length > 0 ? campos[0] : "NaN";
            String tipo = campos.length > 1 ? campos[1] : "NaN";
            String nome = campos.length > 2 ? campos[2] : "NaN";
            String diretor = campos.length > 3 ? campos[3] : "NaN";
            String elenco = campos.length > 4 ? campos[4] : "";
            String pais = campos.length > 5 ? campos[5] : "NaN";
            String data = campos.length > 6 && !campos[6].isEmpty() ? campos[6] : "March 1, 1900";
            int ano = 0;
            try { ano = Integer.parseInt(campos[7]); } catch (Exception e) {}
            String classificacao = campos.length > 8 ? campos[8] : "NaN";
            String tempo = campos.length > 9 ? campos[9] : "NaN";
            String categorias = campos.length > 10 ? campos[10] : "";

            lista.add(new Programa(id, tipo, nome, diretor, elenco, pais, data, ano, classificacao, tempo, categorias));
        }
        br.close();
    }

    public static Programa buscarPrograma(List<Programa> base, String id) {
        for (Programa p : base) {
            if (p.codigo.equals(id)) return p;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner entrada = new Scanner(System.in);
        List<Programa> baseDeDados = new ArrayList<>();
        carregarArquivo("/tmp/disneyplus.csv", baseDeDados);

        String codigo;
        while (entrada.hasNext() && !(codigo = entrada.next()).equals("FIM")) {
            Programa prog = buscarPrograma(baseDeDados, codigo);
            if (prog != null) inserirFinal(prog);
        }

        long inicioTempo = System.currentTimeMillis();
        ordenar();
        long fimTempo = System.currentTimeMillis();

        for (Celula atual = inicio; atual != null; atual = atual.proximo) {
            atual.valor.mostrar();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("867656_quicksort3.txt"));
        bw.write("867656\t" + (fimTempo - inicioTempo) + "\t" + comparacoes);
        bw.close();
        entrada.close();
    }
}
