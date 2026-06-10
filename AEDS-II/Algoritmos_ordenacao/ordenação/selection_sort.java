import java.util.Scanner;

public class selection_sort {
    
public static void ordenacao(int[] vetor, int x){

    for(int j=0;j<x;j++){
    int posicao = j;
    for(int i=j+1; i<x; i++){
        if(vetor[i] < vetor[posicao]){
           posicao = i;
        }
    }
    int tmp = vetor[j];
    vetor[j] = vetor[posicao];
    vetor[posicao] = tmp;
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
