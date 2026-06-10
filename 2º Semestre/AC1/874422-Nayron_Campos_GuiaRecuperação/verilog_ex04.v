// Expressao.v
// Nayron Campos Soares
// Matricula: 874422

module expressao (
    input a,  
    input b,  
    input c,  
    output f  
);

    wire na, bc;  


    not(na, a);  
    

    and(bc, b, c);  


    or(temp, na, bc);  
    not(f, temp);      

endmodule

module tb_expressao;

    reg a, b, c;  
    wire f;      


    expressao uut (
        .a(a),
        .b(b),
        .c(c),
        .f(f)
    );

    initial begin

        $monitor("a = %b, b = %b, c = %b | f = %b", a, b, c, f);
    

        a = 0; b = 0; c = 0;
        #10;

        a = 0; b = 0; c = 1;
        #10;

        a = 0; b = 1; c = 0;
        #10;

        a = 0; b = 1; c = 1;
        #10;

        a = 1; b = 0; c = 0;
        #10;

        a = 1; b = 0; c = 1;
        #10;

        a = 1; b = 1; c = 0;
        #10;

        a = 1; b = 1; c = 1;
        #10;

        $finish;
    end

endmodule
