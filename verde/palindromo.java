package verde;
import java.util.Scanner;


public class palindromo {
    public static void main(String[] args) throws Exception {
        String palavra;
        boolean palin;

        Scanner scan = new Scanner(System.in);
        palavra = scan.nextLine();
        int tam = palavra.length();


        palin = palindromo_rec(palavra, tam, 0);
        System.out.println(palin);
    }
    


    static boolean palindromo_rec(String palavra, int tam, int i) throws Exception{
        if(tam==0){
            throw new Exception("Palavra não identificada");
        }
        if(i>=tam){
            return true;
        }

        if(palavra.charAt(i)!=palavra.charAt(tam-1)){
            return false;
        }
        
    return palindromo_rec(palavra, tam-1, i+1);
    }
}