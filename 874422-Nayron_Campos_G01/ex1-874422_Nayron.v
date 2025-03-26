/*
 Guia_0101.v
 Autor: Nayron Campos 874422
 Descrição: Este código demonstra a conversão de um número decimal para binário em Verilog.
*/

module Guia_0101; 

  integer x = 360;   // Variável inteira que armazena um número decimal
  reg [7:0] b = 0;  // Registrador de 8 bits para armazenar um número binário

  initial begin 
    $display("Guia_0101 - Testes"); // Exibe um título na saída

    $display("x = %d", x);  // Exibe o valor decimal de x
    $display("b = %8b", b); // Exibe o valor binário de b antes da conversão

    b = x; // Atribui o valor decimal de x ao registrador b (conversão automática para binário)

    $display("b = %8b", b); // Exibe o valor binário de b após a conversão
  end

endmodule // Fim 
