/*
 Guia_0204.v
 874422 - Nayron Campos
*/
module Guia_0204;
// Define as variáveis
real x = 0.625; // Valor decimal a ser exibido
reg [7:0] b = 8'b1010_0000; // Valor binário para exibição
integer q [3:0]; // Vetor de 4 inteiros

// Bloco inicial
initial
begin : main
  // Exibe o valor de x em formato decimal
  $display("Guia_0204 - Tests");
  $display("x = %f", x);
  
  // Exibe o valor de b como fração binária
  $display("b = 0.%8b", b);
  
  // Exibe o valor de b como número hexadecimal (separando em dois grupos de 4 bits)
  $display("b = 0.%x%x (16)", b[7:4], b[3:0]);
  
  // Atribui os grupos de 2 bits de b aos elementos do vetor q
  q[3] = b[7:6]; // Bits mais significativos
  q[2] = b[5:4]; 
  q[1] = b[3:2]; 
  q[0] = b[1:0]; // Bits menos significativos
  
  // Exibe b agrupado em 4 grupos de 2 bits
  $display("b = 0.%2b %2b %2b %2b (2)", b[7:6], b[5:4], b[3:2], b[1:0]);
  
  // Exibe os valores de q em formato decimal
  $display("q = 0.%2d %2d %2d %2d (4)", q[3], q[2], q[1], q[0]);
end // Fim do bloco inicial
endmodule // Fim do módulo
