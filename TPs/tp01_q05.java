import java.util.Scanner;

public class tp01_q05 {

    static boolean[] obterValoresBooleanos(String entrada) {
        int tamanho = entrada.charAt(0) - '0';
        boolean[] valores = new boolean[tamanho];
        int indice = 0;

        for (int i = 0; i < tamanho * 2 + 1; i++) {
            if (entrada.charAt(i) == '1') {
                valores[indice++] = true;
            } else if (entrada.charAt(i) == '0') {
                valores[indice++] = false;
            }
        }
        return valores;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String entrada = scanner.nextLine();
            if (entrada.equals("0")) {
                scanner.close();
                break;
            }

            boolean[] valores = obterValoresBooleanos(entrada);
            entrada = substituirOperadores(entrada, valores);
            entrada = calcularExpressaoBooleana(entrada);
            System.out.println(entrada);
        }
    }

    public static String calcularExpressaoBooleana(String expressao) {
        int qtdOperadores = 0;
        for (char c : expressao.toCharArray()) {
            if (c == '^' || c == 'V' || c == '!') {
                qtdOperadores++;
            }
        }

        int[] posicoesOperadores = new int[qtdOperadores];
        int indice = 0;
        for (int i = expressao.length() - 1; i >= 0; i--) {
            if (expressao.charAt(i) == '^' || expressao.charAt(i) == 'V' || expressao.charAt(i) == '!') {
                posicoesOperadores[indice++] = i;
            }
        }

        for (int i : posicoesOperadores) {
            if (expressao.charAt(i) == '^') {
                expressao = aplicarAnd(expressao, i);
            } else if (expressao.charAt(i) == 'V') {
                expressao = aplicarOr(expressao, i);
            } else if (expressao.charAt(i) == '!') {
                expressao = aplicarNot(expressao, i);
            }
        }
        return expressao;
    }

    public static String aplicarAnd(String expressao, int indice) {
        int count = 0, positivos = 0, i = indice;
        while (expressao.charAt(i) != ')') {
            if (Character.isDigit(expressao.charAt(i))) {
                count++;
                if (expressao.charAt(i) == '1')
                    positivos++;
            }
            i++;
        }
        return expressao.substring(0, indice) + (positivos == count ? '1' : '0') + expressao.substring(i + 1);
    }

    public static String aplicarOr(String expressao, int indice) {
        int count = 0, positivos = 0, i = indice;
        while (expressao.charAt(i) != ')') {
            if (Character.isDigit(expressao.charAt(i))) {
                count++;
                if (expressao.charAt(i) == '1')
                    positivos++;
            }
            i++;
        }
        return expressao.substring(0, indice) + (positivos > 0 ? '1' : '0') + expressao.substring(i + 1);
    }

    public static String aplicarNot(String expressao, int indice) {
        int i = indice;
        while (expressao.charAt(i) != ')') {
            if (Character.isDigit(expressao.charAt(i))) {
                char novoValor = (expressao.charAt(i) == '1') ? '0' : '1';
                return expressao.substring(0, indice) + novoValor + expressao.substring(i + 2);
            }
            i++;
        }
        return expressao;
    }

    public static String substituirOperadores(String expressao, boolean[] valores) {
        expressao = expressao.replace("and", "^").replace("or", "V").replace("not", "!");
        expressao = substituirVariaveis(expressao, valores);
        return limparExpressao(expressao);
    }

    public static String substituirVariaveis(String expressao, boolean[] valores) {
        StringBuilder novaExpressao = new StringBuilder();
        for (char c : expressao.toCharArray()) {
            if (c >= 'A' && c <= 'C') {
                int index = c - 'A';
                novaExpressao.append(valores[index] ? '1' : '0');
            } else {
                novaExpressao.append(c);
            }
        }
        return novaExpressao.toString();
    }

    public static String limparExpressao(String expressao) {
        StringBuilder novaExpressao = new StringBuilder();
        boolean encontrouOperador = false;
        for (char c : expressao.toCharArray()) {
            if (c == '^' || c == 'V' || c == '!') {
                encontrouOperador = true;
            }
            if (encontrouOperador && c != ' ' && c != ',') {
                novaExpressao.append(c);
            }
        }
        return novaExpressao.toString();
    }
}
