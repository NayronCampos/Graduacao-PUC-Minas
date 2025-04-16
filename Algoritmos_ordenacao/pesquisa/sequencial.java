import java.util.Scanner;

public class sequencial{

//função

public static boolean have(int[] dados, int x, int i){

    if (i >= dados.length) {
        return false;
    }
    
    if(dados[i] == x){
        return true;
    }
    return have(dados, x, i+1);
}
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n;

        n = scanner.nextInt();
        int[] vetor = new int[n];
        
        for(int i=0;i<n;i++){
            vetor[i]=scanner.nextInt();
        }
        
        //pesquisa:

        System.out.println("Digite o numero a procurar:");
        int valor = scanner.nextInt();

        for(int i=0;i<n;i++){
            if(vetor[i] == valor){
                System.out.println("Have");
                i=n;
            }
            
        }
        System.out.println(have(vetor, valor, 0));
    }
}