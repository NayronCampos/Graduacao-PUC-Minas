package verde;
import java.util.Random;  
import java.util.Scanner;  

public class Aleatoria {
    public static void main (String args[]) throws Exception{
        Scanner scan = new Scanner(System.in);

        String frase;
        frase = scan.nextLine();

        if(frase.length()==0){
            throw new Exception("Mensagem não pode ser vazia");
        }
        else if(frase.length()==1){
            throw new Exception("Mensagem não pode ser um carcter só");
        }
        else if(frase.length()==2){
            throw new Exception("Mensagem precisa de ao menos mais de dois caracteres");
        }
        
        Random gerador = new Random();
        int c1 = gerador.nextInt(frase.length());
        char letra1 = frase.charAt(c1);
        char letra2 = (char) (gerador.nextInt(26) + 97);
                System.out.println("Letra escolhida da frase:" + letra1);
                System.out.println("Letra escolhida para substituir:" + letra2);

            int tamFrase = frase.length();
            int contador=0;

            while(tamFrase!=0){
              StringBuilder fraseEditavel = new StringBuilder(frase);

                if (fraseEditavel.charAt(contador) == letra1) {
                fraseEditavel.setCharAt(contador, letra2);
                }
                tamFrase--;
                contador++;

                frase = fraseEditavel.toString();


            }
            System.out.println(frase);

/* 
        Random gerador = new Random();
        gerador.setSeed(5);
        int i1 = gerador.nextInt(frase.length());
        int i2 = gerador.nextInt(frase.length());    

        char c1 = frase.charAt(i1);
        char c2 = frase.charAt(i2);
    

        System.out.println(c1 + " " + c2);
*/
    
    }
}
