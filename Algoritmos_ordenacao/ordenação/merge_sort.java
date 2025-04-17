import java.util.Scanner;

public class merge_sort {
    
    public static void ordenacao(int[] vetor, int x){

        
        for(int i=0;i<x;i++){
            System.out.println(vetor[i]); 
        }
        
        }    public static void main(String[] args){
                Scanner scanner = new Scanner(System.in);
        
                int n = scanner.nextInt();
                int[] valores = new int[n];
        
                for(int i=0;i<n;i++){
                    valores[i] = scanner.nextInt();
                }
        
                ordenacao(valores, n);
            }

    
}
