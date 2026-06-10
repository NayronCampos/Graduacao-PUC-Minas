/*
 Guia_0203.v
 874422 - Nayron Campos
*/
module Guia_0203;
// Define as variáveis
real x = 0.625; // Valor decimal a ser exibido
reg [7:0] b = 8'b1010_0000 ; // Valor binário para exibição

// Bloco inicial
initial
begin : main
  // Exibe o valor de x em formato decimal
  $display("Guia_0203 - Tests");
  $display("x = %f", x);
  
  // Exibe o valor de b como fração binária
  $display("b = 0.%8b", b);
  
  // Exibe o valor de b como número hexadecimal (separando em dois grupos de 4 bits)
  $display("b = 0.%x%x (16)", b[7:4], b[3:0]);
  
  // Exibe o valor de b como número octal (separando em três grupos de 3 bits)
  $display("b = 0.%o%o%o (8)", b[7:5], b[4:2], b[1:0]);
end // Fim do bloco inicial
endmodule // Fim do módulo
