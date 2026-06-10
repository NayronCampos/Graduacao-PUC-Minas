package verde;
import java.util.Scanner;

public class crifra_cesar {

    static String cesar(String pala, int tam) throws Exception{
         if(pala.length()<3){
            throw new Exception("Palavra não pode ser menor que 3");
        }
        
        if(tam<0){
            return "";
        }

        char letracifrada = (char) (pala.charAt(tam) + 3);

        return cesar(pala, tam - 1) + letracifrada;
    }

    public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);


            String palavra, newPala;
            
            palavra = scan.nextLine();

            int tam = palavra.length();

            newPala = cesar(palavra, tam-1);

            System.out.println(newPala);



        scan.close();
    }
    
}
