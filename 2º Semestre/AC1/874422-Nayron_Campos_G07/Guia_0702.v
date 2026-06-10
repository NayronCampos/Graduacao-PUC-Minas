// -------------------------
// Guia_0702 - LU (OR, NOR)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input  a,
  input  b,
  input  sel,       // 0: OR; 1: NOR
  output saida_or,
  output saida_nor,
  output saida_sel
);
  or   O1(saida_or, a, b);
  nor  N1(saida_nor, a, b);
  wire not_sel, mask_or, mask_nor;
  not  N2(not_sel, sel);
  and  A2(mask_or, saida_or, not_sel);
  and  A3(mask_nor, saida_nor, sel);
  or   O2(saida_sel, mask_or, mask_nor);
endmodule

module test_LU;
  reg a, b, sel;
  wire saida_or, saida_nor, saida_sel;
  LU UUT(.a(a), .b(b), .sel(sel), .saida_or(saida_or), .saida_nor(saida_nor), .saida_sel(saida_sel));
  initial begin

    $display("Guia_0702 - Nayron Campos - 874422");
    $display("a b sel | OR NOR SEL");
    a=0; b=0; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=0; b=0; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=0; b=1; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=0; b=1; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=1; b=0; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=1; b=0; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=1; b=1; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    a=1; b=1; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_or,saida_nor,saida_sel);
    $finish;
  end
endmodule
