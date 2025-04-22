// -------------------------
// Guia_0704 - LU (OR, NOR, XOR, XNOR)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module logic_gates(
  output or_out,
  output nor_out,
  output xor_out,
  output xnor_out,
  input  a,
  input  b
);
  or   O1(or_out, a, b);
  nor  N1(nor_out, a, b);
  xor  X1(xor_out, a, b);
  xnor XN1(xnor_out, a, b);
endmodule

module mux4x1(
  output s,
  input  x0,  // para sel = 00 -> XNOR
  input  x1,  // para sel = 01 -> XOR
  input  x2,  // para sel = 10 -> OR
  input  x3,  // para sel = 11 -> NOR
  input  [1:0] sel
);
  wire ns0, ns1;
  not N1(ns0, sel[0]);
  not N2(ns1, sel[1]);
  wire y0, y1, y2, y3;
  and A1(y0, x0, ns1, ns0);   // 00
  and A2(y1, x1, ns1, sel[0]); // 01
  and A3(y2, x2, sel[1], ns0); // 10
  and A4(y3, x3, sel[1], sel[0]); // 11
  or  O1(s, y0, y1, y2, y3);
endmodule

module LU(
  output s,
  input  a,
  input  b,
  input  [1:0] sel      // 00: XNOR, 01: XOR, 10: OR, 11: NOR
);
  wire or_out, nor_out, xor_out, xnor_out;
  logic_gates LG(.or_out(or_out), .nor_out(nor_out), .xor_out(xor_out), .xnor_out(xnor_out), .a(a), .b(b));
  mux4x1 MUX(.s(s), .x0(xnor_out), .x1(xor_out), .x2(or_out), .x3(nor_out), .sel(sel));
endmodule

module test_LU;
  reg a, b;
  reg [1:0] sel;
  wire s;
  LU UUT(.s(s), .a(a), .b(b), .sel(sel));
  initial begin

    $display("Guia_0704 - Nayron Campos - 874422");
    $display("a b sel | s");
    a=0; b=0;
      sel = 2'b00; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b01; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b10; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b11; #10; $display("%b %b %b | %b", a,b,sel,s);
    a=0; b=1;
      sel = 2'b00; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b01; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b10; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b11; #10; $display("%b %b %b | %b", a,b,sel,s);
    a=1; b=0;
      sel = 2'b00; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b01; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b10; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b11; #10; $display("%b %b %b | %b", a,b,sel,s);
    a=1; b=1;
      sel = 2'b00; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b01; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b10; #10; $display("%b %b %b | %b", a,b,sel,s);
      sel = 2'b11; #10; $display("%b %b %b | %b", a,b,sel,s);
    $finish;
  end
endmodule
