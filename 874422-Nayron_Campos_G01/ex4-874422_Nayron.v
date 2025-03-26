/*
 Guia_0104.v
 874422 - Nayron Campos
*/
module Guia_0104;

  integer x = 45;     // x em decimal
  reg [7:0] b = 0;    // registrador de 8 bits para armazenar o valor binário

  initial begin : main
    $display("Guia_0104 - Tests");                  // Título dos testes
    $display("x = %d", x);                          // Exibe x em decimal
    $display("b = %8b", b);                         // Exibe b em binário (inicialmente 0)
    b = x;                                         // Atribui o valor de x a b (conversão para binário)
    // Exibe os 4 bits superiores e inferiores de b, tanto em binário quanto em hexadecimal
    $display("b = [%4b] [%4b] = %x %x", b[7:4], b[3:0], b[7:4], b[3:0]);
  end

endmodule
