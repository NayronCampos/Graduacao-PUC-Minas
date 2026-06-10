public class carro{

    String cor;
    int ano;
    int km;
    double peso;
    String nome;

    public carro(String color, int year, int mk, double weight, String name){

        cor=color;
        ano=year;
        km=mk;
        peso=weight;
        nome=name;
    }

    public static void main (String[] args){
            carro uno = new carro("Azul", 1990, 123455, 1234.76, "Uno");
            System.out.println(uno.nome + ", that's a name of my car, its my little baby and my big love, and her has a color " + uno.cor);
            carro corola = new carro ("beige", 2004, 456456, 2345.557, "Corolla");
            System.out.println(corola.nome + ", that's my car to work, he is my son, i go at her to the beach, to work, it's a dick off all trades, and he have the color  " + corola.cor);

    }
}