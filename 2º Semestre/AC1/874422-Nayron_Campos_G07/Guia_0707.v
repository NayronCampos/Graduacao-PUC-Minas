// -------------------------
// Guia_0707 - Comparador de 4 bits (Menor/Maior)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input  [3:0] a,
  input  [3:0] b,
  input        sel,  // 0: verifica se a < b; 1: verifica se a > b
  output       s
);

  // Gerar sinais de igualdade para os bits superiores
  // eq3 = (a[3] & b[3]) OR ((~a[3]) & (~b[3]))
  wire not_a3, not_b3, eq3;
  not N1(not_a3, a[3]);
  not N2(not_b3, b[3]);
  wire t_eq3_1, t_eq3_2;
  and A_eq3_1(t_eq3_1, a[3], b[3]);
  and A_eq3_2(t_eq3_2, not_a3, not_b3);
  or  O_eq3(eq3, t_eq3_1, t_eq3_2);

  // eq2 = (a[2] & b[2]) OR ((~a[2]) & (~b[2]))
  wire not_a2, not_b2, eq2;
  not N3(not_a2, a[2]);
  not N4(not_b2, b[2]);
  wire t_eq2_1, t_eq2_2;
  and A_eq2_1(t_eq2_1, a[2], b[2]);
  and A_eq2_2(t_eq2_2, not_a2, not_b2);
  or  O_eq2(eq2, t_eq2_1, t_eq2_2);

  // eq1 = (a[1] & b[1]) OR ((~a[1]) & (~b[1]))
  wire not_a1, not_b1, eq1;
  not N5(not_a1, a[1]);
  not N6(not_b1, b[1]);
  wire t_eq1_1, t_eq1_2;
  and A_eq1_1(t_eq1_1, a[1], b[1]);
  and A_eq1_2(t_eq1_2, not_a1, not_b1);
  or  O_eq1(eq1, t_eq1_1, t_eq1_2);

  // Condição para "a > b":
  // Cond1: a[3] & ~b[3]
  wire cond1;
  and A_gt1(cond1, a[3], not_b3);
  
  // Cond2: eq3 & (a[2] & ~b[2])
  wire cond2;
  and A_gt2(cond2, eq3, a[2], not_b2);
  
  // Cond3: eq3 & eq2 & (a[1] & ~b[1])
  wire cond3;
  and A_gt3(cond3, eq3, eq2, a[1], not_b1);
  
  // Cond4: eq3 & eq2 & eq1 & (a[0] & ~b[0])
  wire not_b0, cond4;
  not N7(not_b0, b[0]);
  and A_gt4(cond4, eq3, eq2, eq1, a[0], not_b0);
  
  wire gt;
  or  O_gt(gt, cond1, cond2, cond3, cond4);

  // Condição para "a < b":
  // Cond5: ~a[3] & b[3]
  wire cond5;
  and A_lt1(cond5, not_a3, b[3]);
  
  // Cond6: eq3 & (~a[2] & b[2])
  wire cond6;
  and A_lt2(cond6, eq3, not_a2, b[2]);
  
  // Cond7: eq3 & eq2 & (~a[1] & b[1])
  wire cond7;
  and A_lt3(cond7, eq3, eq2, not_a1, b[1]);
  
  // Cond8: eq3 & eq2 & eq1 & (~a[0] & b[0])
  wire not_a0, cond8;
  not N8(not_a0, a[0]);
  and A_lt4(cond8, eq3, eq2, eq1, not_a0, b[0]);
  
  wire lt;
  or  O_lt(lt, cond5, cond6, cond7, cond8);

  // Multiplexador: se sel=1, s = gt; se sel=0, s = lt
  wire not_sel, out_gt, out_lt;
  not N_mux(not_sel, sel);
  and A_mux1(out_gt, gt, sel);
  and A_mux2(out_lt, lt, not_sel);
  or  O_mux(s, out_gt, out_lt);

endmodule

// -------------------------
// Testbench para Guia_0707.v
// -------------------------
module test_LU;
  reg  [3:0] a, b;
  reg        sel;
  wire       s;
  
  LU UUT(.a(a), .b(b), .sel(sel), .s(s));
  
  initial begin
    $display("Guia_0707 - Comparador 4 bits (Menor/Maior) - 874422");
    $display("   a      b   sel | s");
    // Caso 1: a < b, usando sel = 0 (menor)
    a = 4'b0101; b = 4'b1010; sel = 0; #10; // 5 < 10
    $display("%b %b %b | %b", a, b, sel, s);
    // Caso 2: a > b, usando sel = 1 (maior)
    a = 4'b1100; b = 4'b1010; sel = 1; #10; // 12 > 10
    $display("%b %b %b | %b", a, b, sel, s);
    // Caso 3: a = b (igual) – ambas condições devem resultar em 0
    a = 4'b1001; b = 4'b1001; sel = 0; #10;
    $display("%b %b %b | %b", a, b, sel, s);
    a = 4'b1001; b = 4'b1001; sel = 1; #10;
    $display("%b %b %b | %b", a, b, sel, s);
    // Caso 4: a < b, mas com sel = 1 (maior) – condição falsa
    a = 4'b0011; b = 4'b0100; sel = 1; #10;
    $display("%b %b %b | %b", a, b, sel, s);
    // Caso 5: a > b, mas com sel = 0 (menor) – condição falsa
    a = 4'b0110; b = 4'b0101; sel = 0; #10;
    $display("%b %b %b | %b", a, b, sel, s);
    
    $finish;
  end
endmodule
