// -------------------------
// Guia_0701 - LU (AND, NAND)
// Nome: Nayron Campos
// Matricula: 874422
// -------------------------
module LU(
  input  a,
  input  b,
  input  sel,       // 0: AND; 1: NAND
  output saida_and,
  output saida_nand,
  output saida_sel
);
  and   A1(saida_and, a, b);
  nand  N1(saida_nand, a, b);
  wire not_sel, mask_and, mask_nand;
  not   N2(not_sel, sel);
  and   A2(mask_and, saida_and, not_sel);
  and   A3(mask_nand, saida_nand, sel);
  or    O1(saida_sel, mask_and, mask_nand);
endmodule

module test_LU;
  reg a, b, sel;
  wire saida_and, saida_nand, saida_sel;
  LU UUT(.a(a), .b(b), .sel(sel), .saida_and(saida_and), .saida_nand(saida_nand), .saida_sel(saida_sel));
  initial begin

    $display("Guia_0701 - Nayron Campos - 874422");
    $display("a b sel | AND NAND SEL");
    a=0; b=0; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=0; b=0; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=0; b=1; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=0; b=1; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=1; b=0; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=1; b=0; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=1; b=1; sel=0; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    a=1; b=1; sel=1; #10; $display("%b %b %b | %b %b %b", a,b,sel,saida_and,saida_nand,saida_sel);
    $finish;
  end
endmodule
