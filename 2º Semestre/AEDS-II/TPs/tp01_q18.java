class tp01_q18 {

    public static String cifrarRecursivo(int chave, int pos, String texto, String resultado) {
        if (pos == 0) { // Caso base: quando se atinge o primeiro caractere.
            char caractereCifrado = (char) (texto.charAt(pos) + chave);
            resultado += caractereCifrado;
            return resultado;
        }
        char caractereCifrado = (char) (texto.charAt(pos) + chave);
        resultado = cifrarRecursivo(chave, pos - 1, texto, resultado);
        resultado += caractereCifrado; // Concatena o caractere cifrado mantendo a ordem correta.
        return resultado;
    }

    public static String cifrar(String texto) {
        // Função que cifra a string, adicionando 3 à tabela ASCII para cada caractere.
        String resultado = "";
        resultado = cifrarRecursivo(3, texto.length() - 1, texto, resultado);
        return resultado;
    }

    static boolean igual(String texto) {
        return texto.length() == 3 && texto.charAt(0) == 'F' && texto.charAt(1) == 'I' && texto.charAt(2) == 'M';
    }

    public static void main(String[] args) {

        while (true) {
            String texto = MyIO.readLine();
            if (igual(texto)) {
                break;
            }
            MyIO.println(cifrar(texto));
        }
    }
}
