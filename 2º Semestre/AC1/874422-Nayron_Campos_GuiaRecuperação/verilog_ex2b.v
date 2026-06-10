//Nayron Campos Soares
//Matrícula: 874422

module nand_gate (
    input a, b,  
    output out   
);

    assign out = ~(a & b);  

endmodule

module sb (
    input a, b, c,  
    output sb        
);

    wire nand1, nand2, nand3, nand4;  // Fios intermediários


    nand_gate u1 (
        .a(a),
        .b(a),
        .out(nand1)
    );


    nand_gate u2 (
        .a(nand1),
        .b(b),
        .out(nand2)
    );


    nand_gate u3 (
        .a(b),
        .b(b),
        .out(nand3)
    );


    nand_gate u4 (
        .a(c),
        .b(nand3),
        .out(nand4)
    );


    nand_gate u5 (
        .a(nand2),
        .b(nand4),
        .out(sb)
    );

endmodule

module tb_sb;

    reg a, b, c;
    wire sb;       


    sb uut (
        .a(a),
        .b(b),
        .c(c),
        .sb(sb)
    );

    initial begin

        $monitor("a = %b, b = %b, c = %b | sb = %b", a, b, c, sb);


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
