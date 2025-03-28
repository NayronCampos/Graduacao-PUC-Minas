/*
 Guia_0201.v
 874422 - Nayron Campos
*/
module Guia_0201;
real x = 0 ; // Resultado decimal
real power2 = 1.0; // Potência de 2
integer y = 7 ; 
reg [7:0] b = 8'b10100000; // Fração binária de 8 bits (Big Endian)

initial
begin : main
  $display("Guia_0201 - Tests");
  $display("x = %f", x);
  $display("b = 0.%8b", b);
  
  // Loop para processar cada bit de b
  while (y >= 0) 
  begin
    power2 = power2 / 2.0; // Divisão de power2 por 2 em cada iteração
    if (b[y] == 1) // Se o bit for 1, adiciona power2 a x
      x = x + power2;
    $display("x = %f", x); // Exibe o valor de x a cada iteração
    y = y - 1; // Decrementa o contador y
  end 
end 
endmodule 
