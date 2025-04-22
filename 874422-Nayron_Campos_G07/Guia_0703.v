// -------------------------
// Guia_0703 - LU (AND/NAND e OR/NOR)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input  a,
  input  b,
  input  sel_gate,  // 0: AND/OR; 1: NAND/NOR
  input  sel_group, // 1: grupo AND/NAND; 0: grupo OR/NOR
  output s
);
  // Grupo 1: AND e NAND
  wire and_out, nand_out, group1;
  and   A1(and_out, a, b);
  nand  N1(nand_out, a, b);
  wire not_sel_gate;
  not   N2(not_sel_gate, sel_gate);
  wire g1a, g1b;
  and   A2(g1a, and_out, not_sel_gate);
  and   A3(g1b, nand_out, sel_gate);
  or    O1(group1, g1a, g1b);
  
  // Grupo 2: OR e NOR
  wire or_out, nor_out, group2;
  or    O2(or_out, a, b);
  nor   N3(nor_out, a, b);
  wire g2a, g2b;
  and   A4(g2a, or_out, not_sel_gate);
  and   A5(g2b, nor_out, sel_gate);
  or    O3(group2, g2a, g2b);
  
  // Seleção final entre os grupos
  wire not_sel_group, final1, final2;
  not   N4(not_sel_group, sel_group);
  and   A6(final1, group1, sel_group);
  and   A7(final2, group2, not_sel_group);
  or    O4(s, final1, final2);
endmodule

module test_LU;
  reg a, b, sel_gate, sel_group;
  wire s;
  LU UUT(.a(a), .b(b), .sel_gate(sel_gate), .sel_group(sel_group), .s(s));
  initial begin

    $display("Guia_0703 - Nayron Campos - 874422");
    $display("a b sel_gate sel_group | s");
    a=0; b=0;
      sel_gate=0; sel_group=0; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=1; sel_group=0; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=0; sel_group=1; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=1; sel_group=1; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
    a=1; b=1;
      sel_gate=0; sel_group=0; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=1; sel_group=0; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=0; sel_group=1; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
      sel_gate=1; sel_group=1; #10; $display("%b %b %b %b | %b", a,b,sel_gate,sel_group,s);
    $finish;
  end
endmodule
