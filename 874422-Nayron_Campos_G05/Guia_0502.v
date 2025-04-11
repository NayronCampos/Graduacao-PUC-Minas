// Guia_0502.v
//Nayron Campos Soares
// Matricula: 874422

module Guia_0502 (
    input a,    // Entrada a
    input b,    // Entrada b
    output y    // Saída y
);

    wire n_b;   // Sinal para ~b

    // Gera ~b usando apenas uma porta NAND
    nand(n_b, b, b); 

    // Calcula y = ~(a & ~b) usando porta NAND
    nand(y, a, n_b); // y = ~(a & n_b) = ~(a & ~b)

endmodule

module tb_Guia_0502;

    reg a, b;  
    wire y;     

    // Instancia o módulo Guia_0502
    Guia_0502 uut (
        .a(a),
        .b(b),
        .y(y)
    );

    initial begin
        $monitor("a=%b, b=%b, y=%b", a, b, y);

        
        a = 0; b = 0;
        #10;
        a = 0; b = 1;
        #10;
        a = 1; b = 0;
        #10;
        a = 1; b = 1;
        #10;

        $finish;
    end

endmodule
