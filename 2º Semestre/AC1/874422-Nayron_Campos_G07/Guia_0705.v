// -------------------------
// Guia_0705 - LU (NOT, AND, NAND, OR, NOR, XOR, XNOR, extra)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input        a,
  input        b,
  input  [2:0] sel, // 000: NOT, 001: AND, 010: NAND, 011: OR,
                     // 100: NOR, 101: XOR, 110: XNOR, 111: a AND (~b)
  output       s
);
  // Operações lógicas
  wire op0, op1, op2, op3, op4, op5, op6, op7;
  
  not  N1(op0, a);       // NOT a
  and  A1(op1, a, b);     // a AND b
  nand N2(op2, a, b);     // a NAND b
  or   O1(op3, a, b);     // a OR b
  nor  N3(op4, a, b);     // a NOR b
  xor  X1(op5, a, b);     // a XOR b
  xnor XN1(op6, a, b);    // a XNOR b
  
  wire b_not;
  not  N4(b_not, b);
  and  A2(op7, a, b_not); // Extra: a AND (~b)
  
  // Multiplexador 8x1 implementado com portas AND/OR
  wire ns0, ns1, ns2;
  not  N5(ns0, sel[0]);
  not  N6(ns1, sel[1]);
  not  N7(ns2, sel[2]);
  
  wire m0, m1, m2, m3, m4, m5, m6, m7;
  and A8 (m0, op0, ns2, ns1, ns0);      // sel = 000 → NOT a
  and A9 (m1, op1, ns2, ns1, sel[0]);    // sel = 001 → AND
  and A10(m2, op2, ns2, sel[1], ns0);     // sel = 010 → NAND
  and A11(m3, op3, ns2, sel[1], sel[0]);  // sel = 011 → OR
  and A12(m4, op4, sel[2], ns1, ns0);     // sel = 100 → NOR
  and A13(m5, op5, sel[2], ns1, sel[0]);   // sel = 101 → XOR
  and A14(m6, op6, sel[2], sel[1], ns0);   // sel = 110 → XNOR
  and A15(m7, op7, sel[2], sel[1], sel[0]); // sel = 111 → a AND (~b)
  
  or  O2(s, m0, m1, m2, m3, m4, m5, m6, m7);
endmodule

// -------------------------
// Testbench para Guia_0705.v
// -------------------------
module test_LU;
  reg a, b;
  reg [2:0] sel;
  wire s;
  
  LU UUT(.a(a), .b(b), .sel(sel), .s(s));
  
  initial begin
    $display("Guia_0705 - Nayron Campos - 874422");
    $display(" a  b  sel | s");
    
    // Caso 1: a = 0, b = 0
    a = 0; b = 0;
      sel = 3'b000; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b001; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b010; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b011; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b100; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b101; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b110; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b111; #10; $display("%b %b %b | %b", a, b, sel, s);
      
    // Caso 2: a = 0, b = 1
    a = 0; b = 1;
      sel = 3'b000; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b001; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b010; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b011; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b100; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b101; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b110; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b111; #10; $display("%b %b %b | %b", a, b, sel, s);
      
    // Caso 3: a = 1, b = 0
    a = 1; b = 0;
      sel = 3'b000; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b001; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b010; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b011; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b100; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b101; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b110; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b111; #10; $display("%b %b %b | %b", a, b, sel, s);
      
    // Caso 4: a = 1, b = 1
    a = 1; b = 1;
      sel = 3'b000; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b001; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b010; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b011; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b100; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b101; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b110; #10; $display("%b %b %b | %b", a, b, sel, s);
      sel = 3'b111; #10; $display("%b %b %b | %b", a, b, sel, s);
      
    $finish;
  end
endmodule
