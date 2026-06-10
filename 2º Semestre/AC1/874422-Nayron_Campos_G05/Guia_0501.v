// Guia_0501.v
// Nayron Campos Soares
// Matricula: 874422

module Guia_0501 (
    input a,  // Entrada a
    input b,  // Entrada b
    output y  // Saída y
);

    wire na;  // ~a

    // Calcula ~a usando NOR
    nor(na, a, a);  

    // Calcula y = ~(~a + b) usando NOR
    nor(y, na, b);  

endmodule

module tb_Guia_0501;

    reg a, b;  // Entradas de teste
    wire y;    // Saída do módulo

    // Instancia o módulo Guia_0501
    Guia_0501 uut (
        .a(a),
        .b(b),
        .y(y)
    );

    initial begin
        $monitor("a = %b, b = %b, y = %b", a, b, y);
    
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
