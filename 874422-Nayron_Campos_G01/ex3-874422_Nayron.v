/*
 Guia_0103.v
 999999 - Xxx Yyy Zzz
*/
module Guia_0103;

 integer x = 13; // Número decimal
 reg [7:0] b = 0; // Registrador de 8 bits

 initial
 begin : main
   $display ( "Guia_0103 - Tests" ); // Exibe o título
   $display ( "x = %d" , x ); // Mostra x em decimal
   $display ( "b = %8b", b ); // Mostra b em binário antes da conversão
   b = x; // Atribui x a b (conversão para binário)
   $display ( "b = %B (2) = %o (8) = %x (16)", b, b, b ); // Mostra b em várias bases numéricas
 end

endmodule // Guia_0103
