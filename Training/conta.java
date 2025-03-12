public class conta {
    int valor=0;
    int saque;
    int deposito;

    public int insertvalor(int adddeposito){
        deposito=adddeposito;
        return valor += deposito;
    }
    public int sacar(int saquii){
        saque=saquii;
        return valor -= saque;
    }

   

    public static void main (String[] args){
            System.out.println("Bem vindo a sua conta!");
            conta Nayron = new conta();
            
            System.out.println(Nayron.insertvalor(180));
            System.out.println(Nayron.sacar(18));






    }
}