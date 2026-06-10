public class tp01_q20 {

    static boolean ehVogal(char caractere) {
        return "aeiouAEIOU".indexOf(caractere) != -1;
    }

    static boolean ehFim(String texto) {
        return texto.equals("FIM");
    }

    static boolean soVogais(String texto, int indice) {
        if (indice >= texto.length()) {
            MyIO.print("SIM ");
            return true;
        }
        if (ehVogal(texto.charAt(indice))) {
            return soVogais(texto, indice + 1);
        }
        MyIO.print("NAO ");
        return false;
    }

    static boolean apenasVogais(String texto) {
        return soVogais(texto, 0);
    }

    static boolean soConsoantes(String texto, int indice) {
        if (indice >= texto.length()) {
            MyIO.print("SIM ");
            return true;
        }
        if (!ehVogal(texto.charAt(indice)) && !ehNumero(texto.charAt(indice))) {
            return soConsoantes(texto, indice + 1);
        }
        MyIO.print("NAO ");
        return false;
    }

    static boolean apenasConsoantes(String texto) {
        return soConsoantes(texto, 0);
    }

    static boolean ehNumero(char caractere) {
        return caractere >= '0' && caractere <= '9';
    }

    static boolean soNumeros(String texto, int indice) {
        if (indice >= texto.length()) {
            MyIO.print("SIM ");
            return true;
        }
        if (ehNumero(texto.charAt(indice))) {
            return soNumeros(texto, indice + 1);
        }
        MyIO.print("NAO ");
        return false;
    }

    static boolean apenasNumeros(String texto) {
        return soNumeros(texto, 0);
    }

    static boolean soNumerosReais(String texto, int indice, int separadores) {
        if (indice >= texto.length()) {
            MyIO.print("SIM\n");
            return true;
        }
        char caractere = texto.charAt(indice);
        if (caractere == '.' || caractere == ',') {
            separadores++;
            if (separadores > 1) {
                MyIO.print("NAO\n");
                return false;
            }
        } else if (!ehNumero(caractere)) {
            MyIO.print("NAO\n");
            return false;
        }
        return soNumerosReais(texto, indice + 1, separadores);
    }

    static boolean apenasNumerosReais(String texto) {
        return soNumerosReais(texto, 0, 0);
    }

    // Função recursiva para processar cada linha de entrada
    static void processarInput() {
        String texto = MyIO.readLine();
        if (ehFim(texto)) {
            return;
        }
        apenasVogais(texto);
        apenasConsoantes(texto);
        apenasNumeros(texto);
        apenasNumerosReais(texto);
        processarInput();
    }

    public static void main(String[] args) {
        processarInput();
    }
}
