import java.util.Scanner;

public class bubble_sort {

    public static void ordenacao(int[] vetor, int x){
        for(int i=(x-1);i>0;i--){
            for(int j=0;j<i;j++){
                if(vetor[j] > vetor[j+1]){
                    int tmp = vetor[j];
                vetor[j] = vetor[j+1];
                vetor[j+1] = tmp;
                }
            }
        }
        for(int i=0;i<x;i++){
            System.out.println(vetor[i]); 
        }

    }
    public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] valores = new int[n];

        for(int i=0;i<n;i++){
            valores[i] = scanner.nextInt();
        }

        ordenacao(valores, n);
    }
    
}
