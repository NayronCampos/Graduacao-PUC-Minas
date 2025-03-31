/*  Crie uma classe Show seguindo todas as regras apresentadas no slide unidade00l conceitosBasicos introducaoOO.pdf. Sua classe ter´a os atributos privado show id:
string, type: string, title: string, director: string, cast: string[], country:
string, date added: date, release year: int, rating: string, duration: string, listed in: string[]. Sua classe tamb´em ter´a pelo menos dois construtores, e os m´etodos gets,
sets, clone, imprimir e ler.
O m´etodo imprimir mostra os atributos do registro (ver cada linha da sa´ıda padr˜ao) e o ler lˆe
os atributos de um registro. Aten¸c˜ao para o arquivo de entrada, pois em alguns registros faltam
valores e esse deve ser substitu´ıdo pelo valor NaN. A entrada padr˜ao ´e composta por v´arias linhas
e cada uma cont´em um n´umero inteiro indicando o show id do Show a ser lido.
A ´ultima linha da entrada cont´em a palavra FIM. A sa´ıda padr˜ao tamb´em cont´em v´arias
linhas, uma para cada registro contido em uma linha da entrada padr˜ao, no seguinte formato: [=> id ## type ## title ## director ## [cast] ## country ## date added ##
release year ## rating ## duration ## [listed in].*/
import java.util.Scanner;

public class tp02_q01{

public class Show{
    private:
    String SHOW_ID;
    String TYPE, TITLE, DIRECTOR, COUNTRY, RATING, DURATION;
    Date DATE_ADDED;
    int RELASE_YEAR
    String[] CAST = new String[];

}

}