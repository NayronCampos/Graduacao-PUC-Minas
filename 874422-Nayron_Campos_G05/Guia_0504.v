// Guia_0504.v
// Nayron Campos Soares
// Matricula 874422

module Guia_0504 (
    input a,     // Entrada a
    input b,     // Entrada b
    output y     // Saída y = ~(a & ~b)
);

    wire n_b;    // Sinal para ~b

    // Gera ~b usando uma porta NAND (inversor)
    nand(n_b, b, b);  

    // Calcula y = ~(a & ~b) usando porta NAND
    nand(y, a, n_b);  

endmodule

module tb_Guia_0504;

    reg a, b;    // Entradas para teste
    wire y;      // Saída do módulo


    Guia_0504 uut (
        .a(a),
        .b(b),
        .y(y)
    );

    initial begin
        $monitor(" a=%b, b=%b, y=%b", a, b, y);
        
        
        a = 0; b = 0; #10;
        a = 0; b = 1; #10;
        a = 1; b = 0; #10;
        a = 1; b = 1; #10;
        
        $finish;
    end
    endmodule
