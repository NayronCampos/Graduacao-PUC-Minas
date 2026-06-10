/*
 Guia_0205.v
 874422 - Nayron Campos
*/
module Guia_0205;
// Define as variáveis
reg [7:0] a = 8'b000_1010; // Número binário 'a' (10 em decimal)
reg [7:0] b = 8'b000_1100; // Número binário 'b' (12 em decimal)
reg [7:0] c; // Variável para armazenar o resultado das operações

// Bloco inicial
initial
begin : main
  // Exibe os valores de a e b em formato binário
  $display("Guia_0205 - Tests");
  $display("a = %8b", a);
  $display("b = %8b", b);

  // Soma de a e b
  c = a + b;
  $display("c = a + b = %8b", c);
  
  // Subtração de a por b
  c = a - b;
  $display("c = a - b = %8b", c);
  
  // Subtração de b por a
  c = b - a;
  $display("c = b - a = %8b", c);
  
  // Multiplicação de a por b
  c = a * b;
  $display("c = a * b = %8b", c);
  
  // Divisão de b por a (resultado inteiro)
  c = b / a;
  $display("c = b / a = %8b", c);
  
  // Módulo de b por a (resto da divisão)
  c = b % a;
  $display("c = b %% a = %8b", c);
end // Fim do bloco inicial
endmodule // Fim do módulo
