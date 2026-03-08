// Nome: Nayron Campos Soares
// Matrícula: 874422
//Exercício 2

`timescale 1ns/1ps

module jk_ff (
    input J, K,
    input clk,
    input clr,
    output reg Q
);
    always @(posedge clk or posedge clr) begin
        if (clr)
            Q <= 0;
        else case ({J,K})
            2'b10: Q <= 1;
            2'b01: Q <= 0;
            2'b11: Q <= ~Q;
            default: Q <= Q;
        endcase
    end
endmodule

module async_up_counter6 (
    input clk,
    input clr,
    output [5:0] Q
);
    wire ONE = 1'b1;

    // clocks invertidos para contagem crescente (ripple com ~Q)
    wire c1 = ~Q[0];
    wire c2 = ~Q[1];
    wire c3 = ~Q[2];
    wire c4 = ~Q[3];
    wire c5 = ~Q[4];

    jk_ff ff0(.J(ONE), .K(ONE), .clk(clk),  .clr(clr), .Q(Q[0]));
    jk_ff ff1(.J(ONE), .K(ONE), .clk(c1),   .clr(clr), .Q(Q[1]));
    jk_ff ff2(.J(ONE), .K(ONE), .clk(c2),   .clr(clr), .Q(Q[2]));
    jk_ff ff3(.J(ONE), .K(ONE), .clk(c3),   .clr(clr), .Q(Q[3]));
    jk_ff ff4(.J(ONE), .K(ONE), .clk(c4),   .clr(clr), .Q(Q[4]));
    jk_ff ff5(.J(ONE), .K(ONE), .clk(c5),   .clr(clr), .Q(Q[5]));
endmodule

module tb_async_up_counter6;
    reg clk, clr;
    wire [5:0] Q;

    async_up_counter6 uut(.clk(clk), .clr(clr), .Q(Q));

    initial begin
        clk = 0;
        clr = 1; #5;
        clr = 0;

        $display("Time   Q5 Q4 Q3 Q2 Q1 Q0");
        repeat (20) begin
            #5 clk = 1;
            #1 $display("%3dns  %b", $time, Q);
            #4 clk = 0;
        end
        $finish;
    end
endmodule
