// Guia_Recuperação_ex03.B
// Nayron Campos Soares
// Matricula: 874422

module expressao_b (
    input a,  // Entrada a
    input b,  // Entrada b
    input c,  // Entrada c
    input d,  // Entrada d
    output f  // Saída f
);

    wire na, nb, nc;  // ~a, ~b, ~c

    not(na, a);  
    not(nb, b);  
    not(nc, c);  

    or(f, (na & nc & d), (a & nb), (nb & d));  

endmodule

module tb_expressao_b;

    reg a, b, c, d;  // Entradas de teste
    wire f;           


    expressao_b uut (
        .a(a),
        .b(b),
        .c(c),
        .d(d),
        .f(f)
    );

    initial begin
        $monitor("a = %b, b = %b, c = %b, d = %b, f = %b", a, b, c, d, f);
    

        a = 0; b = 0; c = 0; d = 0;
        #10;

        a = 0; b = 0; c = 0; d = 1;
        #10;

        a = 0; b = 0; c = 1; d = 0;
        #10;

        a = 0; b = 0; c = 1; d = 1;
        #10;

        a = 0; b = 1; c = 0; d = 0;
        #10;

        a = 0; b = 1; c = 0; d = 1;
        #10;

        a = 0; b = 1; c = 1; d = 0;
        #10;

        a = 0; b = 1; c = 1; d = 1;
        #10;

        a = 1; b = 0; c = 0; d = 0;
        #10;

        a = 1; b = 0; c = 0; d = 1;
        #10;

        a = 1; b = 0; c = 1; d = 0;
        #10;

        a = 1; b = 0; c = 1; d = 1;
        #10;

        a = 1; b = 1; c = 0; d = 0;
        #10;

        a = 1; b = 1; c = 0; d = 1;
        #10;

        a = 1; b = 1; c = 1; d = 0;
        #10;

        a = 1; b = 1; c = 1; d = 1;
        #10;

        $finish;
    end

endmodule
