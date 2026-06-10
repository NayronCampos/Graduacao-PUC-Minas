// -------------------------
// Guia_0706 - Comparador 4 bits
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input  [3:0] a,
  input  [3:0] b,
  input        sel,  // 1: igual; 0: diferente
  output       s
);
  // Compara cada bit com XNOR para igualdade
  wire x0, x1, x2, x3;
  xnor X0(x0, a[0], b[0]);
  xnor X1(x1, a[1], b[1]);
  xnor X2(x2, a[2], b[2]);
  xnor X3(x3, a[3], b[3]);
  // Se todos os bits forem iguais, a saída eq será 1
  wire eq;
  and A_eq(eq, x0, x1, x2, x3);

  // Compara cada bit com XOR para detectar diferença
  wire y0, y1, y2, y3;
  xor X4(y0, a[0], b[0]);
  xor X5(y1, a[1], b[1]);
  xor X6(y2, a[2], b[2]);
  xor X7(y3, a[3], b[3]);
  // Se algum par for diferente, diff será 1
  wire diff;
  or O_diff(diff, y0, y1, y2, y3);

  // Multiplexador simples: se sel=1, s = eq; se sel=0, s = diff
  wire not_sel, m1, m2;
  not N0(not_sel, sel);
  and A_mux1(m1, eq, sel);
  and A_mux2(m2, diff, not_sel);
  or O_mux(s, m1, m2);
endmodule

// -------------------------
// Testbench para Guia_0706.v
// -------------------------
module test_LU;
  reg  [3:0] a, b;
  reg        sel;
  wire       s;
  
  LU UUT(.a(a), .b(b), .sel(sel), .s(s));
  
  initial begin
    $display("Guia_0706 - Nayron Campos - 874422");
    $display("   a       b    sel | s");
    // Teste de igualdade (sel = 1)
    a = 4'b0000; b = 4'b0000; sel = 1; #10; $display("%b %b %b | %b", a, b, sel, s);
    a = 4'b0101; b = 4'b0101; sel = 1; #10; $display("%b %b %b | %b", a, b, sel, s);
    a = 4'b1111; b = 4'b1111; sel = 1; #10; $display("%b %b %b | %b", a, b, sel, s);
    // Teste de desigualdade (sel = 0)
    a = 4'b0000; b = 4'b0001; sel = 0; #10; $display("%b %b %b | %b", a, b, sel, s);
    a = 4'b1010; b = 4'b0101; sel = 0; #10; $display("%b %b %b | %b", a, b, sel, s);
    a = 4'b1001; b = 4'b1001; sel = 0; #10; $display("%b %b %b | %b", a, b, sel, s);
    $finish;
  end
endmodule
