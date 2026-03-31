// Expressao_a.v
// Nayron Campos Soares
// Matricula: 874422

module expressao_a (
    input a,  
    input b,  
    input d,  
    output f  
);

    wire na, nb;  


    not(na, a);  
    not(nb, b);  


    and(na_and_d, na, d);    
    and(a_and_nb, a, nb);    
    or(f, na_and_d, a_and_nb);  
endmodule

module tb_expressao_a;

    reg a, b, d;  
    wire f;       


    expressao_a uut (
        .a(a),
        .b(b),
        .d(d),
        .f(f)
    );

    initial begin

        $monitor("b = %b, a = %b, d = %b | f = %b", b, a, d, f);
    
        // Testa todas as combinações de a, b, d

        b = 0; a = 0; d = 0;
        #10;

        b = 0; a = 0; d = 1;
        #10;

        b = 0; a = 1; d = 0;
        #10;

        b = 0; a = 1; d = 1;
        #10;

        b = 1; a = 0; d = 0;
        #10;

        b = 1; a = 0; d = 1;
        #10;

        b = 1; a = 1; d = 0;
        #10;

        b = 1; a = 1; d = 1;
        #10;

        $finish;
    end

endmodule
