import java.util.*;
import java.io.*;
import java.text.*;

class Nodo {
    public Exibicao elemento;
    public Nodo direito;
    public Nodo esquerdo;
    
    Nodo(Exibicao elemento) {
        this.elemento = elemento;
        this.direito = null;
        this.esquerdo = null;
    }
    
    Nodo(Exibicao elemento, Nodo direito, Nodo esquerdo) {
        this.elemento = elemento;
        this.direito = direito;
        this.esquerdo = esquerdo;
    }
}

class Arvore {
    public Nodo raiz;

    public Arvore() {
        this.raiz = null;
    }

    public void inserir(Exibicao x) {
        raiz = inserir(x, raiz);
    }

    public Nodo inserir(Exibicao x, Nodo i) {
        if (i == null) {
            return new Nodo(x);
        } else if (x.getTitulo().compareTo(i.elemento.getTitulo()) < 0) {
            i.esquerdo = inserir(x, i.esquerdo);
        } else if (x.getTitulo().compareTo(i.elemento.getTitulo()) > 0) {
            i.direito = inserir(x, i.direito);
        }
        
        return i;
    }
    
    public boolean pesquisar(String x, int[] comparacoes) {
        System.out.print("=>raiz  ");
        boolean resultado = pesquisar(x, raiz, comparacoes);
        if (resultado) {
            System.out.println("SIM");
        } else {
            System.out.println("NAO");
        }
        return resultado;
    }
    
    public boolean pesquisar(String x, Nodo i, int[] comparacoes) {
        if (i == null) {
            return false;
        } else {
            comparacoes[0]++;
            if (x.compareTo(i.elemento.getTitulo()) < 0) {
                System.out.print("esq ");
                return pesquisar(x, i.esquerdo, comparacoes);
            } else if (x.compareTo(i.elemento.getTitulo()) > 0) {
                System.out.print("dir ");
                return pesquisar(x, i.direito, comparacoes);
            } else {
                return true;
            } 
        }
    }
}

public class Exibicao {
    private String idExibicao;
    private String tipo;
    private String titulo;
    private String diretor;
    private String[] elenco;
    private String pais;
    private Date dataAdicionada;
    private int anoLancamento;
    private String classificacao;
    private String duracao;
    private String[] listadoEm;

    public String getIdExibicao() {
        return idExibicao;
    }

    public void setIdExibicao(String idExibicao) {
        this.idExibicao = idExibicao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String[] getElenco() {
        return elenco;
    }

    public void setElenco(String[] elenco) {
        this.elenco = elenco;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getDataAdicionada() {
        return dataAdicionada;
    }

    public void setDataAdicionada(Date dataAdicionada) {
        this.dataAdicionada = dataAdicionada;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String[] getListadoEm() {
        return listadoEm;
    }

    public void setListadoEm(String[] listadoEm) {
        this.listadoEm = listadoEm;
    }

    public Exibicao() {
        this.idExibicao = "NaN";
        this.tipo = "NaN";
        this.titulo = "NaN";
        this.diretor = "NaN";
        this.elenco = new String[]{"NaN"};
        this.pais = "NaN";
        this.dataAdicionada = null;
        this.anoLancamento = -1;
        this.classificacao = "NaN";
        this.duracao = "NaN";
        this.listadoEm = new String[]{"NaN"};
    }

    public Exibicao(String idExibicao, String tipo, String titulo, String diretor, String[] elenco, String pais, String dataAdicionada, int anoLancamento, String classificacao, String duracao, String[] listadoEm) {
        this.idExibicao = (idExibicao != null && !idExibicao.isEmpty()) ? idExibicao : "NaN";
        this.tipo = (tipo != null && !tipo.isEmpty()) ? tipo : "NaN";
        this.titulo = (titulo != null && !titulo.isEmpty()) ? titulo : "NaN";
        this.diretor = (diretor != null && !diretor.isEmpty()) ? diretor : "NaN";
        this.elenco = (elenco != null && elenco.length >= 0) ? elenco : new String[]{"NaN"};
        this.pais = (pais != null && !pais.isEmpty()) ? pais : "NaN";
        SimpleDateFormat formatoData = new SimpleDateFormat("MMMM dd, yyyy");
        if (dataAdicionada != null && !dataAdicionada.isEmpty()) {
            try {
                this.dataAdicionada = formatoData.parse(dataAdicionada);
            } catch (ParseException e) {
                System.out.println("Erro ao adicionar a data");
                this.dataAdicionada = null;
            }
        } else {
            this.dataAdicionada = null;
        }
        this.anoLancamento = (anoLancamento > 0) ? anoLancamento : -1;
        this.duracao = (duracao != null && !duracao.isEmpty()) ? duracao : "NaN";
        this.listadoEm = (listadoEm != null && listadoEm.length >= 0) ? listadoEm : new String[]{"NaN"};
    }

    public void imprimir() {
        SimpleDateFormat formatoData = new SimpleDateFormat("MMMM d, yyyy");
        System.out.print("=> " + getIdExibicao());
        System.out.print(" ## " + getTitulo());
        System.out.print(" ## " + getTipo());
        System.out.print(" ## " + getDiretor());
        System.out.print(" ## " + Arrays.toString(getElenco()));
        System.out.print(" ## " + getPais());
        System.out.print(" ## " + (getDataAdicionada() != null ? formatoData.format(getDataAdicionada()) : "NaN"));
        System.out.print(" ## " + (getAnoLancamento() != -1 ? getAnoLancamento() : "NaN"));
        System.out.print(" ## " + getClassificacao());
        System.out.print(" ## " + getDuracao());
        System.out.println(" ## " + Arrays.toString(getListadoEm()) + " ##");
    }

    public static ArrayList<Exibicao> ler() {
        ArrayList<Exibicao> listaExibicao = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader("/tmp/disneyplus.csv"))) {
            String linha = "";
            r.readLine();
            while ((linha = r.readLine()) != null) {
                Exibicao exibicao = new Exibicao();
                exibicao.atribuir(linha);
                listaExibicao.add(exibicao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaExibicao;
    }

    public void atribuir(String linha) {
        String[] str = new String[11];
        Arrays.fill(str, "");
        str = separarLinha(linha);
        SimpleDateFormat formato = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

        setIdExibicao((str[0] != null) ? str[0] : "NaN");
        setTipo((str[1] != null) ? str[1] : "NaN");
        setTitulo((str[2] != null) ? str[2] : "NaN");
        setDiretor((str[3] != null) ? str[3] : "NaN");
        if (str[4] != null) {
            setElenco(ordenaArray(str[4]));
        } else {
            setElenco(new String[]{"NaN"});
        }
        setPais((str[5] != null) ? str[5] : "NaN");
        try {
            if (str[6] != null && !str[6].isEmpty()) {
                Date data = formato.parse(str[6]);
                setDataAdicionada(data);
            } else if (str[6] == null) {
                Date data = formato.parse("March 1, 1900");
                setDataAdicionada(data);
            }
        } catch (Exception e) {
            System.out.println("Erro ao adicionar a data: " + str[6]);
            this.dataAdicionada = null;
        }
        setAnoLancamento((str[7] != null) ? Integer.parseInt(str[7]) : -1);
        setClassificacao((str[8] != null) ? str[8] : "NaN");
        setDuracao((str[9] != null) ? str[9] : "NaN");
        if (str[10] != null) {
            setListadoEm(ordenaArray(str[10]));
        } else {
            setListadoEm(new String[]{"NaN"});
        }
    }

    public static String[] ordenaArray(String str) {
        int temp = 0, tam = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                tam++;
            }
        }

        String[] array = new String[tam + 1];
        Arrays.fill(array, "");

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ',') {
                array[temp] += str.charAt(i);
            } else if (str.charAt(i) == ',') {
                i++; 
                temp++;
            }
        }

        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    String aux = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = aux;
                }
            }
        }
        return array;
    }

    public static String[] separarLinha(String linha) {
        String[] str = new String[11];
        Arrays.fill(str, "");
        int aux = 0, i = 0;
        while (i < linha.length() && aux != 11) {
            char letra = linha.charAt(i);
            char letra2 = 'c';
            if (i + 1 < linha.length()) {
                letra2 = linha.charAt(i + 1);
            }
            if (letra == ',' && letra2 != ' ') {
                if (str[aux].isEmpty()) {
                    str[aux] = null;
                }
                aux++;
            } else {
                if (letra != '"') {
                    str[aux] += letra;
                } else {
                    i++;
                    letra = linha.charAt(i);
                    while (i < linha.length() && linha.charAt(i) != '"') {
                        str[aux] += letra;
                        i++;
                        letra = linha.charAt(i);
                    }
                }
            }
            i++;
        }
        return str;
    }

    public Exibicao clone() {
        Exibicao copia = new Exibicao();
        copia.setIdExibicao(this.idExibicao);
        copia.setTipo(this.tipo);
        copia.setTitulo(this.titulo);
        copia.setDiretor(this.diretor);
        copia.setElenco(Arrays.copyOf(this.elenco, this.elenco.length));
        copia.setPais(this.pais);
        copia.setDataAdicionada(this.dataAdicionada != null ? new Date(this.dataAdicionada.getTime()) : null);
        copia.setAnoLancamento(this.anoLancamento);
        copia.setClassificacao(this.classificacao);
        copia.setDuracao(this.duracao);
        copia.setListadoEm(Arrays.copyOf(this.listadoEm, this.listadoEm.length));
        return copia;
    }

    public static void arquivoLog(double duracao, int[] comparacoes) {
        String matricula = "874422";
        try {
            PrintWriter w = new PrintWriter(matricula + "_arvoreBinaria.txt");
            w.printf("%s\t%d\t%fms", matricula, comparacoes[0], duracao);
            w.close();
        } catch (IOException e) {
            System.err.println("Erro" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int[] comparacoes = {0};
        int j = 0;
        Scanner input = new Scanner(System.in);
        ArrayList<Exibicao> listaExibicao = ler();
        String id = "";
        
        Exibicao[] lista = new Exibicao[2000];
        String[] listaPesquisa = new String[100];
        Arvore arvore = new Arvore();
        
        id = input.nextLine();
        while (!id.equals("FIM")) {
            for (int i = 0; i < listaExibicao.size(); i++) {
                if (listaExibicao.get(i).getIdExibicao().equals(id)) {
                    lista[j] = listaExibicao.get(i);
                    arvore.inserir(listaExibicao.get(i));
                    j++;
                }
            }
            id = input.nextLine();
        }
        
        j = 0;
        String titulo = input.nextLine();
        while (!titulo.equals("FIM")) {
            listaPesquisa[j] = titulo;
            j++;
            titulo = input.nextLine();
        }
        
        long inicioTempo = System.nanoTime();
        for (int i = 0; i < j; i++) {
            arvore.pesquisar(listaPesquisa[i], comparacoes);
        }
        long fimTempo = System.nanoTime();
        
        double duracao = (fimTempo - inicioTempo) / 1_000_000.0; 

        arquivoLog(duracao, comparacoes);
        input.close();
    }
}
