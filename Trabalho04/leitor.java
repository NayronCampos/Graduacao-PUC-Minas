import java.io.*;

//Fazendo a leitura dos dados através de um arquivo, no caso ele converte para o "código" da operação pra ser mandado pro arduino 
public class leitor {

    public static void main(String[] args) {
        String arquivo = "entrada.txt"; // Caso a entrada seja dada por um arquivo externo

        char x = '\0';
        char y = '\0';
        String op = "";

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.equalsIgnoreCase("inicio") || linha.equalsIgnoreCase("fim")) {
                    continue;
                }
                //Inicio ao fim

                if (linha.contains("=")) {
                    String[] parte = linha.split("=", 2);
                    String variavel = parte[0].trim();
                    String valor = parte[1].trim();

                    switch (variavel) {
                        case "X":
                            if (!valor.isEmpty()) {
                                x = valor.charAt(0);
                            }
                            break;

                        case "Y":
                            if (!valor.isEmpty()) {
                                y = valor.charAt(0);
                            }
                            break;

                        case "W":
                            switch (valor) {
                                case "umL;":
                                    op = "0";
                                    break;
                                case "zeroL;":
                                    op = "1";
                                    break;
                                case "AonB;":
                                    op = "2";
                                    break;
                                case "nAonB;":
                                    op = "3";
                                    break;
                                case "AeBn;":
                                    op = "4";
                                    break;
                                case "nB;":
                                    op = "5";
                                    break;
                                case "nA;":
                                    op = "6";
                                    break;
                                case "nAxnB;":
                                    op = "7";
                                    break;
                                case "AxB;":
                                    op = "8";
                                    break;
                                case "copiaA;":
                                    op = "9";
                                    break;
                                case "copiaB;":
                                    op = "A";
                                    break;
                                case "AeB;":
                                    op = "B";
                                    break;
                                case "AenB;":
                                    op = "C";
                                    break;
                                case "nAeB;":
                                    op = "D";
                                    break;
                                case "AoB;":
                                    op = "E";
                                    break;
                                case "nAeBn;":
                                    op = "F";
                                    break;
                                default:
                                    op = "Erro: Não reconhecido";
                            }

                            System.out.println("" + x + y + op);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro com seu arquivo: " + e.getMessage());
        }
    }
}
