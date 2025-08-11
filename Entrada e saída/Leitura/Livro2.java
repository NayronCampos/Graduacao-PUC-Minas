// titulo autor preço
package Leitura;

import java.text.DecimalFormat;

public class Livro2 {
    protected int IdLivro;
    protected String titulo;
    protected String autor;
    protected float preco;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    public Livro2(int i, String t, String a, float p){
        IdLivro = i;
        titulo =t;
        autor = a;
        preco =p;
    }

    public String toString(){
        return "\nID:" + IdLivro +
               "\nTítulo:" + titulo +
               "\nAutor:" + autor +
               "\nPreço: R$" + df.format(preco);

    }

}