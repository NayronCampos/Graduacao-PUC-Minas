/*
 Guia_0102.v
 874422 - Nayron
*/
module Guia_0102;

 integer x = 0; // Variável decimal inicializada com 0
 reg [7:0] b = 8'b0111011; // Registrador de 8 bits armazenando um número binário

 initial
 begin : main
   $display ( "Guia_0102 - Tests" ); // Exibe um título na saída
   $display ( "x = %d" , x ); // Mostra o valor inicial de x (decimal)
   $display ( "b = %8b", b ); // Mostra b em binário
   x = b; // Atribui o valor binário de b para x (conversão para decimal)
   $display ( "b = %d", x ); // Exibe o valor decimal de b após a conversão
 end

endmodule
