import java.util.Arrays;
import java.util.Scanner;

public class binario{

//função

public static boolean have(int[] dados, int x, int esq, int dir){
    int meio = (esq+dir)/2;

    if(esq>dir){
        return false;
    }

    if(x == dados[meio]){
        return true;
      }

      else if(x>dados[meio]){

        return have(dados, x, meio+1 , dir );
      }

      else{
        
        return have(dados, x, esq, meio-1 );
      }
    
}
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n;

        n = scanner.nextInt();
        int[] vetor = new int[n];
        
        for(int i=0;i<n;i++){
            vetor[i]=scanner.nextInt();
        }

        Arrays.sort(vetor);

        for(int i=0;i<n;i++){
            System.out.printf(vetor[i]+ " ");
        }

        //pesquisa:

        System.out.println("Digite o numero a procurar:");
        int valor = scanner.nextInt();
        int esq = 0, dir = n-1;
      
        System.out.println(have(vetor, valor, esq, dir));
    }
}