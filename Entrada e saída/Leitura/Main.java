package Leitura;
import java.io.DataOutputStream;
import java.io.FileOutputStream;    

public class Main {
    
    public static void main(String[] args){

        Livro2 l1 = new Livro2(1, "Cálculo I", "Stuart", 15.8F);
        Livro2 l2 = new Livro2(2, "Programação", "August", 25.0F);

        System.out.println(l1);
        System.out.println(l2);

        FileOutputStream arq;
        DataOutputStream dos;

        try{

            arq = new FileOutputStream("dados/livros.db");
            dos = new DataOutputStream(arq);

            dos.writeInt(l1.IdLivro);
            dos.writeUTF(l1.titulo);
            dos.writeUTF(l1.autor);
            dos.writeFloat(l1.preco);

            
            dos.writeInt(l2.IdLivro);
            dos.writeUTF(l2.titulo);
            dos.writeUTF(l2.autor);
            dos.writeFloat(l2.preco);
        } catch (Exception e){

            e.printStackTrace();
        }
    }
}
