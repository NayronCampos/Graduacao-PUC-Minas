import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

class pessoa{
    private String nome;
    private String id;

    public pessoa(String nome){
        this.nome=nome;
        this.id=NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                                           NanoIdUtils.DEFAULT_ALPHABET,
                                           10);
    }

    public void print(){
        System.out.println("Nome " + nome + " e seu ID: " + id);
    }

}

    public class Principal{
    public static void main(String[] args){
        pessoa p1 = new pessoa("João");
        pessoa p2 = new pessoa("Maria");

        p1.print();
        p2.print();
    }
}
