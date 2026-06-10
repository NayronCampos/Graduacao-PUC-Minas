import java.util.Scanner;

class fatorial{

    public static int fat(int x){
    if(x==0 || x==1){
        return 1;
    }
    return x * fat(x-1);

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();
        System.out.println("O fatorial do valor: " + n + " eh " + fat(n));
    }
}