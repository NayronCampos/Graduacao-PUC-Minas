import java.io.*;
import java.net.*;

public class tp01_q13 {

    public static String baixarHTML(String urlEndereco) {
        StringBuilder conteudo = new StringBuilder();
        try {
            URL url = new URL(urlEndereco);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(url.openStream()));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            leitor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conteudo.toString();
    }

    public static void contarPadroes(String html, int[] contadores) {
        contarVogais(html, contadores);
        contarBr(html, contadores);
        contarTable(html, contadores);
    }

    public static void contarVogais(String texto, int[] contadores) {

        String vogais = "aeiouáéíóúàèìòùãõâêîôû";
        for (char c : texto.toCharArray()) {
            char lower = Character.toLowerCase(c);
            int index = vogais.indexOf(lower);
            if (index != -1) {
                contadores[index]++;
            } else if (Character.isLetter(lower)) {
                contadores[22]++; // Contador de consoantes
            }
        }
    }

    public static void contarBr(String texto, int[] contadores) {
        // Procura ocorrências de "<br>"
        contadores[23] += texto.split("<br>", -1).length - 1;
    }

    public static void contarTable(String texto, int[] contadores) {
        // Procura ocorrências de "<table>"
        contadores[24] += texto.split("<table>", -1).length - 1;
    }

    public static void imprimirResultado(int[] contadores, String nomePagina) {
        System.out.printf(
                "a(%d) e(%d) i(%d) o(%d) u(%d) á(%d) é(%d) í(%d) ó(%d) ú(%d) " +
                        "à(%d) è(%d) ì(%d) ò(%d) ù(%d) ã(%d) õ(%d) â(%d) ê(%d) î(%d) ô(%d) û(%d) " +
                        "consoante(%d) <br>(%d) <table>(%d) %s\n",
                contadores[0], contadores[1], contadores[2], contadores[3], contadores[4],
                contadores[5], contadores[6], contadores[7], contadores[8], contadores[9],
                contadores[10], contadores[11], contadores[12], contadores[13], contadores[14],
                contadores[15], contadores[16], contadores[17], contadores[18], contadores[19],
                contadores[20], contadores[21], contadores[22], contadores[23], contadores[24],
                nomePagina);
    }

    public static boolean ehFim(String entrada) {
        return entrada.equals("FIM");
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String nomePagina;
        while (!(nomePagina = in.readLine()).equals("FIM")) {
            String url = in.readLine();
            String html = baixarHTML(url);
            int[] contadores = new int[25];
            contarPadroes(html, contadores);
            imprimirResultado(contadores, nomePagina);
        }
    }
}
