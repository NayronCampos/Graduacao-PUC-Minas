// Nayron Campos Soares
// Matrícula: 874422

module ex_05(

input a,
input b,
output s

);
wire not_a, not_b, xnor_ab, nor_out;

not gate1(not_a, a);
not gate2(not_b, b);

assign xnor_ab = ~(a^b);
assign nor_out = ~(not_a | not_b);
assign s = ~(xnor_ab & nor_out);

endmodule

`timescale 1ns / 1ps

module test_ex_05;  // Início do módulo de teste

    reg a, b;       
    wire f;         
    

    ex_05 uut (
        .s(s),     
        .a(a),    
        .b(b)     
    );
    
    // Saída
    initial begin

        $display("ta\tb\ts");

        $monitor("t%b\t%b\t%b", a, b, s);
        
        
        a = 0; b = 0; #10;
        

        a = 0; b = 1; #10;
        

        a = 1; b = 0; #10;
        

        a = 1; b = 1; #10;
        

        $finish;
    end

endmodule
