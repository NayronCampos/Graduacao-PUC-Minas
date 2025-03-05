import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

public class ArquivoReverso {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int quantidade = entrada.nextInt();

        try {
            RandomAccessFile arquivo = new RandomAccessFile("dados.txt", "rw");

            for (int i = 0; i < quantidade; i++) {
                double numero = entrada.nextDouble();
                arquivo.writeDouble(numero);
            }

            arquivo.close();
            arquivo = new RandomAccessFile("dados.txt", "r");

            long posicao = arquivo.length() - 8;

            while (posicao >= 0) {
                arquivo.seek(posicao);
                double numero = arquivo.readDouble();
                System.out.printf("%.3f%n", numero);
                posicao -= 8;
            }

            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao manipular o arquivo.");
        }

        entrada.close();
    }
}
