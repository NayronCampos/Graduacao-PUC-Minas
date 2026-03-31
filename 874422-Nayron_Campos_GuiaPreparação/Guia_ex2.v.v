//2025-1_arq1_preparacao_01
// 874422
// Nayron Campos Soares

module f (output s, input x, input y);
  wire w1, w2, w3, w4, w5;
  
  not NOT_1 (w1, x);         // w1 = ~x
  not NOT_2 (w2, y);         // w2 = ~y
  and AND_1 (w3, y, w2);     // w3 = y & ~y = 0
  or  OR_1  (w4, w1, x);     // w4 = ~x | x = 1
  not NOT_3 (w5, w4);        // w5 = ~1 = 0
  and AND_2 (s, w3, w5);     // s = 0 & 0 = 0
  
endmodule
module test;
//Teste
  reg x, y;
  wire s;

  f dut(s, x, y); 

  initial begin
    $display("x y | s");
    $display("---------");
    x = 0; y = 0; #1 $display("%b %b | %b", x, y, s);
    x = 0; y = 1; #1 $display("%b %b | %b", x, y, s);
    x = 1; y = 0; #1 $display("%b %b | %b", x, y, s);
    x = 1; y = 1; #1 $display("%b %b | %b", x, y, s);
  end

endmodule
