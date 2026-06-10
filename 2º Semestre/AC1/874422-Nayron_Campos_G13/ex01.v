//Nayron Campos Soares
//Matrícula: 874422

`timescale 1ns/1ps


module jk_ff (
    input  J,
    input  K,
    input  clk,   
    input  clr,    
    input  pre,    
    output reg Q
);
    always @(posedge clk or posedge clr or posedge pre) begin
        if (clr)
            Q <= 1'b0;
        else if (pre)
            Q <= 1'b1;
        else case ({J,K})
            2'b10: Q <= 1'b1;   // set
            2'b01: Q <= 1'b0;   // reset
            2'b11: Q <= ~Q;     
            default: /*00*/     Q <= Q;
        endcase
    end
endmodule


module async_down_counter6 (
    input        clk,    
    input        clr,    
    input        pre,    
    output [5:0] Q       
);

    wire ONE = 1'b1;


    wire c1 = Q[0];
    wire c2 = Q[1];
    wire c3 = Q[2];
    wire c4 = Q[3];
    wire c5 = Q[4];


    jk_ff ff0(
        .J   (ONE), .K   (ONE),
        .clk (clk), .clr (clr), .pre(pre),
        .Q   (Q[0])
    );


    jk_ff ff1(
        .J   (ONE), .K   (ONE),
        .clk (c1),  .clr (clr), .pre(pre),
        .Q   (Q[1])
    );


    jk_ff ff2(
        .J   (ONE), .K   (ONE),
        .clk (c2),  .clr (clr), .pre(pre),
        .Q   (Q[2])
    );


    jk_ff ff3(
        .J   (ONE), .K   (ONE),
        .clk (c3),  .clr (clr), .pre(pre),
        .Q   (Q[3])
    );


    jk_ff ff4(
        .J   (ONE), .K   (ONE),
        .clk (c4),  .clr (clr), .pre(pre),
        .Q   (Q[4])
    );


    jk_ff ff5(
        .J   (ONE), .K   (ONE),
        .clk (c5),  .clr (clr), .pre(pre),
        .Q   (Q[5])
    );

endmodule


module tb_async_down_counter6;
    reg clk, clr, pre;
    wire [5:0] Q;


    async_down_counter6 dut (
        .clk(clk),
        .clr(clr),
        .pre(pre),
        .Q(Q)
    );

    initial begin

        clk = 1'b0;
        clr = 1'b0;
        pre = 1'b0;
        #5;


        pre = 1'b1;
        #5;
        pre = 1'b0;
        #5;


        clr = 1'b0;
        #5;


        repeat (16) begin
            #5 clk = 1'b1;   
            #1 $display("  %b", Q);
            #4 clk = 1'b0;
        end

        $finish;
    end
endmodule
