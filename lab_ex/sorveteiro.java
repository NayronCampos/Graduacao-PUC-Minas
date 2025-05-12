import java.util.Scanner;

public class sorveteiro {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int teste = 1;

        while (true) {
            // lê comprimento da praia e número de sorveteiros
            int metros       = sc.nextInt();
            int sorveteiros  = sc.nextInt();
            // condição de término
            if (metros == 0 && sorveteiros == 0) break;

            // aloca os vetores de intervalos
            int[] inicio = new int[sorveteiros];
            int[] fim    = new int[sorveteiros];

            // lê cada intervalo
            for (int i = 0; i < sorveteiros; i++) {
                inicio[i] = sc.nextInt();
                fim[i]    = sc.nextInt();
            }

            // ordena por início (e, em caso de empate, por fim)
            for (int i = 0; i < sorveteiros - 1; i++) {
                for (int j = i + 1; j < sorveteiros; j++) {
                    if (inicio[j] < inicio[i] ||
                        (inicio[j] == inicio[i] && fim[j] < fim[i])) {
                        // troca inícios
                        int t = inicio[i];
                        inicio[i] = inicio[j];
                        inicio[j] = t;
                        // troca fins
                        t = fim[i];
                        fim[i] = fim[j];
                        fim[j] = t;
                    }
                }
            }

            // imprime o cabeçalho
            System.out.println("Teste " + teste++);
            // faz o merge dos intervalos e imprime
            if (sorveteiros > 0) {
                int curIni = inicio[0];
                int curFim = fim[0];
                for (int i = 1; i < sorveteiros; i++) {
                    if (inicio[i] <= curFim) {
                        // sobrepõe, estendo o curFim se necessário
                        if (fim[i] > curFim) {
                            curFim = fim[i];
                        }
                    } else {
                        // intervalo acabado, imprime e começa novo
                        System.out.println(curIni + " " + curFim);
                        curIni = inicio[i];
                        curFim = fim[i];
                    }
                }
                // imprime o último intervalo
                System.out.println(curIni + " " + curFim);
            }
            System.out.println();  // linha em branco entre testes
        }

        sc.close();
    }
}
