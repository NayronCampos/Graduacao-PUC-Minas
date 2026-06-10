import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class tp01_q14 {
    public static void main(String[] args) {
        String nomeArquivo = "numeros.bin";

        try (Scanner scanner = new Scanner(System.in);
             RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "rw")) {

            int n = scanner.nextInt(); 

            for (int i = 0; i < n; i++) {
                String entrada = scanner.next(); // Lê como string

                // Se o número começa com ponto, adiciona o 0 antes
                if (entrada.charAt(0) == '.') {
                    entrada = "0" + entrada;
                }

                try {
                    double numero = Double.parseDouble(entrada);
                    arquivo.writeDouble(numero); // Escreve como double
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Entrada inválida.");
                    return;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao manipular arquivo: " + e.getMessage());
            return;
        }

        try (RandomAccessFile arquivo = new RandomAccessFile(nomeArquivo, "r")) {
            long tamanhoArquivo = arquivo.length();
            long posicaoAtual = tamanhoArquivo - Double.BYTES; 

            while (posicaoAtual >= 0) {
                arquivo.seek(posicaoAtual);
                double num = arquivo.readDouble();

                if (num == Math.floor(num)) {
                    System.out.println((long) num); 
                } else {
                    System.out.println(num); 
                }

                posicaoAtual -= Double.BYTES;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
