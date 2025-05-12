import java.util.Scanner;


public class ex4 {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);    

        int n = scan.nextInt();
        scan.nextLine();

        String[] idioma = new String[n];
        String[] mensagem = new String[n];

        for(int i = 0; i<n; i++){
            idioma[i] = scan.nextLine();
            mensagem[i] = scan.nextLine();
        }

        String lingua;
        int x= scan.nextInt();
        scan.nextLine();
        String[] nome = new String[x];


        for(int i=0; i<x;i++){
        nome[i] = scan.nextLine();
        lingua = scan.nextLine();
        
        System.out.println(nome[i]);
        for(int j=0; j<n; j++){
            if(idioma[j].equals(lingua)){
                System.out.println(mensagem[j]);
                System.out.print("\n");
                break;
            }
        }
        }

       

    }
}
