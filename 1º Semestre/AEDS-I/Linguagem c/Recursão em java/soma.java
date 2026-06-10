import java.util.Scanner;

public class soma{

    public static int soma(int x){
        if(x==0){
            return 0;
        }
        return x + soma(x-1);

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int n;
        n = scanner.nextInt();
        System.out.println(soma(n));
        
    }
}