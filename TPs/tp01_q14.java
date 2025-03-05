import java.io.*;

public class tp01_q14 {
    public static void main(String[] args) throws IOException {
        // Leitura da entrada padrão
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(entrada.readLine());

        // Cria/abre o arquivo para escrita (modo "rw" para leitura e escrita)
        RandomAccessFile raf = new RandomAccessFile("arquivo.txt", "rw");

        // Grava os números no arquivo, cada um em uma linha
        for (int i = 0; i < n; i++) {
            double valor = Double.parseDouble(entrada.readLine());
            raf.writeBytes(valor + "\n");
        }
        raf.close(); // Fecha o arquivo após a gravação

        // Reabre o arquivo somente para leitura
        raf = new RandomAccessFile("arquivo.txt", "r");

        // Variável que indica o final do arquivo, que será usado para definir o trecho
        // a ser lido
        long fim = raf.length();

        // Loop para ler o arquivo de trás para frente
        while (fim > 0) {
            long pos = fim - 1;
            // Procura a posição do caractere de nova linha '\n'
            while (pos >= 0) {
                raf.seek(pos);
                if (raf.read() == '\n') {
                    break;
                }
                pos--;
            }
            // A partir da posição encontrada (ou do início do arquivo) lê a linha inteira
            long inicioLinha = (pos == -1 ? 0 : pos + 1);
            raf.seek(inicioLinha);
            String linha = raf.readLine();
            if (linha != null && !linha.equals("")) {
                System.out.println(linha);
            }
            // Atualiza a posição final para continuar a busca na parte anterior do arquivo
            fim = pos;
        }
        raf.close();
    }
}
