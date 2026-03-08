// Nome: Nayron Campos Soares
// Matrícula: 874422
// Ex04

`timescale 1ns/1ps

module jk_ff (
    input J, K,
    input clk,
    input clr,
    input pre,
    output reg Q
);
    always @(posedge clk or posedge clr or posedge pre) begin
        if (clr)
            Q <= 1'b0;
        else if (pre)
            Q <= 1'b1;
        else case ({J,K})
            2'b10: Q <= 1'b1;
            2'b01: Q <= 1'b0;
            2'b11: Q <= ~Q;
            default: Q <= Q;
        endcase
    end
endmodule

module async_down_mod11_5bit (
    input clk,
    input rst,
    output [4:0] Q
);
    wire ONE = 1'b1;

    wire q0, q1, q2, q3, q4;

    // ripple-down: cada FF dispara quando o anterior sobe
    wire clk1 = q0;
    wire clk2 = q1;
    wire clk3 = q2;
    wire clk4 = q3;

    wire detect5 = ~q4 & ~q3 & q2 & ~q1 & q0;

    // clear desabilitado (não zera), usamos apenas preset ao detectar 5
    wire pre4 = 1'b0;               // Q4 = 0 (sempre fixo)
    wire pre3 = detect5 | rst;      // Q3 = 1
    wire pre2 = detect5 | rst;      // Q2 = 1
    wire pre1 = detect5 | rst;      // Q1 = 1
    wire pre0 = detect5 | rst;      // Q0 = 1

    jk_ff ff0(.J(ONE), .K(ONE), .clk(clk),   .clr(1'b0), .pre(pre0), .Q(q0));
    jk_ff ff1(.J(ONE), .K(ONE), .clk(clk1),  .clr(1'b0), .pre(pre1), .Q(q1));
    jk_ff ff2(.J(ONE), .K(ONE), .clk(clk2),  .clr(1'b0), .pre(pre2), .Q(q2));
    jk_ff ff3(.J(ONE), .K(ONE), .clk(clk3),  .clr(1'b0), .pre(pre3), .Q(q3));
    jk_ff ff4(.J(ONE), .K(ONE), .clk(clk4),  .clr(1'b1), .pre(pre4), .Q(q4)); 

    assign Q = {q4, q3, q2, q1, q0};
endmodule


module tb_async_down_mod11_5bit;
    reg clk, rst;
    wire [4:0] Q;

    async_down_mod11_5bit uut(.clk(clk), .rst(rst), .Q(Q));

    initial begin
        clk = 0; rst = 1; #5; rst = 0;
        $display("Time   Q4 Q3 Q2 Q1 Q0 (Decimal)");
        repeat (20) begin
            #5 clk = 1;
            #1 $display("%3dns  %b  (%0d)", $time, Q, Q);
            #4 clk = 0;
        end
        $finish;
    end
endmodule
