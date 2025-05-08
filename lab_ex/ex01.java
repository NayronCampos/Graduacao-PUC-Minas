/*A entrada contém vários casos de teste. Cada caso de teste começa com uma linha com o número inteiro N, 0 < N < 25; o número de pacientes que chegam à triagem. A seguir são N linhas com os valores inteiros H, M e C, com 7 < H < 19, e 0 ≤ M <60, a hora e minuto que o paciente chega à triagem. O paciente da linha i sempre chega antes que, e no máximo junto com, o paciente da linha i + 1. E 0 ≤C ≤ 720 o número de minutos antes do paciente atingir a condição crítica de saúde.

Se atendimento inicia as 07:00 = min 420mm
 */

import java.util.Scanner;

public class ex01{



    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n, h, m, c, count=0, diference;
        
        n = scan.nextInt();

        for(int i=0; i<n; i++){
            h = scan.nextInt();
            m = scan.nextInt();
            c = scan.nextInt();

            //tranformando em minutos para dps converter nas horas
            h= (h*60) + m + 30;
            m = h%60;
            h/=60;

            // 08:15 -- 08:30 // 08:31 -- 09:00
            if(m>=00 && m<=30){
                m=30;
            }
            else{
                m=0;
                h+=1;
            }

            
            System.out.println(h);
            System.out.println(m);
        }
           /*  if((h*60) + m + 30 < (h*60) + m + c ){
                count++;
            }
        }
        System.out.println("thats a count:" + count);*/
        }
    }
