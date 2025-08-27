package Escrita;
// titulo autor preço

import java.text.DecimalFormat;

public class Livro {
    protected int IdLivro;
    protected String titulo;
    protected String autor;
    protected float preco;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    public Livro(int i, String t, String a, float p) {
        IdLivro = i;
        titulo = t;
        autor = a;
        preco = p;
    }

    public Livro() {
        IdLivro = -1;
        titulo = " ";
        autor = " ";
        preco = 0F;
    }

    public String toString() {
        return "\nID:" + IdLivro +
                "\nTítulo:" + titulo +
                "\nAutor:" + autor +
                "\nPreço: R$" + df.format(preco);

    }

}