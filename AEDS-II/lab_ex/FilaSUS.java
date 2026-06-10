/*A entrada contém vários casos de teste. Cada caso de teste começa com uma linha com o número inteiro N, 0 < N < 25; o número de pacientes que chegam à triagem. A seguir são N linhas com os valores inteiros H, M e C, com 7 < H < 19, e 0 ≤ M <60, a hora e minuto que o paciente chega à triagem. O paciente da linha i sempre chega antes que, e no máximo junto com, o paciente da linha i + 1. E 0 ≤C ≤ 720 o número de minutos antes do paciente atingir a condição crítica de saúde.

Se atendimento inicia as 07:00 = min 420mm
 */

import java.util.Scanner;

public class FilaSUS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Processa vários casos até EOF
        while (scanner.hasNextInt()) {
            int numeroPacientes = scanner.nextInt();         
            int totalCriticos   = 0;                         
            int proximoAtendimento = 7 * 60;  
            int atendimento;//defino a horaa q o paciente deve ser atendido
               

            for (int i = 0; i < numeroPacientes; i++) {
                int horaChegada = scanner.nextInt();      // H
                int minutoChegada = scanner.nextInt();    // M
                int tempo = scanner.nextInt();            // C

                int chegada = horaChegada * 60 + minutoChegada; //calculo em minutos da hora em que chegou, convertendo tudo

                if (chegada >= proximoAtendimento) {
                    atendimento = chegada;
                } else {
                    atendimento = proximoAtendimento;
                }

                int tempoDeEspera = atendimento - chegada;
                if (tempoDeEspera > tempo) {
                    totalCriticos++;
                }

                if (atendimento % 30 == 0) {
                    proximoAtendimento = atendimento + 30;
                } else {
                    proximoAtendimento = ((atendimento / 30) + 1) * 30;
                }
            }

            System.out.println(totalCriticos);
        }

        scanner.close();
    }
}
