//Nayron Campos Soares
//Matrícula: 874422
module mux (
    input a,  
    input b, 
    input c,  
    output sa 
);

    assign sa = a ? b : c;  

endmodule

module tb_mux;

    reg a, b;  
    wire sa;   


    mux uut (
        .a(a),
        .b(b),
        .c(~b), 
        .sa(sa)
    );

    initial begin

        $monitor("a = %b, b = %b | sa = %b", a, b, sa);


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
