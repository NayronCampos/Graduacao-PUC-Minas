import java.util.Scanner;

public class quick_sort {

    public static void ordenacao(int[] dados, int esq, int dir){
        int i= esq, j= dir, pivo = dados[(esq+dir)/2];

        while(i<=j){
            while(dados[i]<pivo){
                i++;
            }
            while(dados[j]>pivo){
                j--;
            }
            if(i<=j){
                int tmp = dados[i];
                dados[i] = dados[j];
                dados[j] = tmp;
                i++;j--;
            }
        }
        if(esq<j){
            ordenacao(dados, esq, j);
        }
        if(i<dir){
            ordenacao(dados, i, dir);
        }
       

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] valores = new int[n];

        for(int i=0;i<n;i++){
            valores[i] = scanner.nextInt();
        }
        int esq=0, dir=n-1;

        ordenacao(valores, esq, dir);

        for(int i=0;i<n;i++){
            System.out.println(valores[i]); 
        }
    }
}
