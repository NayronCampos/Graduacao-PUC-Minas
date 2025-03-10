import java.io.*;
import java.net.*;

public class tp01_q13 { 
    static final char[] caracteresEspeciais = { 225, 233, 237, 243,
            250, 224, 232, 236, 242, 249, 227, 245, 226,
            234, 238, 244, 251 };

    static int[] contagemVogais;

    public static void main(String[] args) {
        MyIO.setCharset("UTF-8");
        String nomeArquivo = "";
        String urlEndereco = "";
        String conteudoHtml = "";
        int numConsoantes = 0, numBr = 0, numTable = 0;

        do {
            nomeArquivo = MyIO.readLine();
            if (!isFim(nomeArquivo)) {
                urlEndereco = MyIO.readLine();
                conteudoHtml = obterHtml(urlEndereco);

                contarVogais(conteudoHtml);
                numConsoantes = contarConsoantes(conteudoHtml);
                numBr = contarBr(conteudoHtml);
                numTable = contarTable(conteudoHtml);

                contagemVogais[0] -= numTable;
                contagemVogais[1] -= numTable;
                numConsoantes -= (2 * numBr + 3 * numTable);

                imprimirVariaveis();
                System.out.printf("consoante(%s) ",
                        numConsoantes);
                System.out.printf("<br>(%s) ", numBr);
                System.out.printf("<table>(%s) ", numTable);
                System.out.printf("%s\n", nomeArquivo);
            }
        } while (!isFim(nomeArquivo));
    }

    public static boolean isFim(String s) {
        boolean resultado = false;
        if (s.length() == 3 && s.charAt(0) == 'F'
                && s.charAt(1) == 'I'
                && s.charAt(2) == 'M') {
            resultado = true;
        }
        return (resultado);
    }

    public static void contarVogais(String html) {
        contagemVogais = new int[23];
        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            if (c == 'a')
                contagemVogais[0]++;
            else if (c == 'e')
                contagemVogais[1]++;
            else if (c == 'i')
                contagemVogais[2]++;
            else if (c == 'o')
                contagemVogais[3]++;
            else if (c == 'u')
                contagemVogais[4]++;
            else if (isEspecial(c)) {
                for (int j = 0; j < caracteresEspeciais.length; j++) {
                    if (c == caracteresEspeciais[j]) {
                        contagemVogais[j + 5]++;
                        break;
                    }
                }
            }
        }
    }

    public static boolean isEspecial(char c) {
        for (int i = 0; i < caracteresEspeciais.length; i++) {
            if (c == caracteresEspeciais[i]) {
                return true;
            }
        }
        return false;
    }

    public static int contarConsoantes(String html) {
        int resultado = 0;
        String consoantes = "bcdfghjklmnpqrstvwxyz";
        for (int i = 0; i < consoantes.length(); i++) {
            char consoante = consoantes.charAt(i);
            for (int j = 0; j < html.length(); j++) {
                char letraHtml = html.charAt(j);
                if (letraHtml == consoante) {
                    resultado++;
                }
            }
        }
        return resultado;
    }

    public static int contarBr(String html) {
        int resultado = 0;
        for (int i = 0; i < html.length() - 4; i++) {
            if (isEquals(subString(html, i, i + 4), "<br>")) {
                resultado++;
            }
        }
        return resultado;
    }

    public static int contarTable(String html) {
        int resultado = 0;
        for (int i = 0; i < html.length() - 7; i++) {
            if (isEquals(subString(html, i, i + 7), "<table>")) {
                resultado++;
            }
        }
        return resultado;
    }

    public static void imprimirVariaveis() {
        System.out.printf("a(%s) ", contagemVogais[0]);
        System.out.printf("e(%s) ", contagemVogais[1]);
        System.out.printf("i(%s) ", contagemVogais[2]);
        System.out.printf("o(%s) ", contagemVogais[3]);
        System.out.printf("u(%s) ", contagemVogais[4]);
        System.out.printf("%c(%s) ", caracteresEspeciais[0], contagemVogais[5]);
        System.out.printf("%c(%s) ", caracteresEspeciais[1], contagemVogais[6]);
        System.out.printf("%c(%s) ", caracteresEspeciais[2], contagemVogais[7]);
        System.out.printf("%c(%s) ", caracteresEspeciais[3], contagemVogais[8]);
        System.out.printf("%c(%s) ", caracteresEspeciais[4], contagemVogais[9]);
        System.out.printf("%c(%s) ", caracteresEspeciais[5], contagemVogais[10]);
        System.out.printf("%c(%s) ", caracteresEspeciais[6], contagemVogais[11]);
        System.out.printf("%c(%s) ", caracteresEspeciais[7], contagemVogais[12]);
        System.out.printf("%c(%s) ", caracteresEspeciais[8], contagemVogais[13]);
        System.out.printf("%c(%s) ", caracteresEspeciais[9], contagemVogais[14]);
        System.out.printf("%c(%s) ", caracteresEspeciais[10], contagemVogais[15]);
        System.out.printf("%c(%s) ", caracteresEspeciais[11], contagemVogais[16]);
        System.out.printf("%c(%s) ", caracteresEspeciais[12], contagemVogais[17]);
        System.out.printf("%c(%s) ", caracteresEspeciais[13], contagemVogais[18]);
        System.out.printf("%c(%s) ", caracteresEspeciais[14], contagemVogais[19]);
        System.out.printf("%c(%s) ", caracteresEspeciais[15], contagemVogais[20]);
        System.out.printf("%c(%s) ", caracteresEspeciais[16], contagemVogais[21]);
    }

    public static String subString(String s, int start, int end) {
        String resultado = "";
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            resultado = resultado + c;
        }
        return resultado;
    }

    public static int indexOf(char procurar, String s) {
        int indice = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == procurar) {
                indice = i;
                i = s.length();
            }
        }
        return indice;
    }

    public static boolean isEquals(String obj1, String obj2) {
        boolean resultado = true;
        if (obj1.length() != obj2.length()) {
            resultado = false;
        } else {
            for (int i = 0; i < obj1.length() && resultado; i++) {
                if (obj1.charAt(i) != obj2.charAt(i)) {
                    resultado = false;
                }
            }
        }
        return resultado;
    }

    public static String obterHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resposta = "", linha;
        try {
            url = new URL(endereco);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((linha = br.readLine()) != null) {
                resposta += linha + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return resposta;
    }
}
