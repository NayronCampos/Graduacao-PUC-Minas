import java.util.Scanner;

public class insertion_sort {
    
public static void ordenacao(int[] vetor, int x){

for(int i=1; i<x;i++){
int tmp = vetor[i];
int j = i-1;
while(j>=0 && tmp<vetor[j]){
    vetor[j+1] = vetor[j];
    j--;
}
vetor[j+1] = tmp;

}
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