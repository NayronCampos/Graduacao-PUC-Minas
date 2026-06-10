// Nome: Nayron Campos Soares
// Matrícula: 874422
// Ex03

`timescale 1ns/1ps

module jk_ff (
    input J, K,
    input clk,
    input clr,     // clear assíncrono ativo-alto
    output reg Q
);
    always @(posedge clk or posedge clr) begin
        if (clr)
            Q <= 0;
        else case ({J, K})
            2'b10: Q <= 1;
            2'b01: Q <= 0;
            2'b11: Q <= ~Q;
            default: Q <= Q;
        endcase
    end
endmodule

module async_decadic_counter5 (
    input clk,
    input rst,
    output [4:0] Q
);
    wire ONE = 1'b1;


    wire q0, q1, q2, q3, q4;

    wire clk1 = ~q0;
    wire clk2 = ~q1;
    wire clk3 = ~q2;
    wire clk4 = ~q3;

    // Detector de 1010 (decimal 10):
    wire detect10;
    assign detect10 = (~q4) & q3 & (~q2) & q1 & (~q0);


    wire clr_all = rst | detect10;

    jk_ff ff0(.J(ONE), .K(ONE), .clk(clk),   .clr(clr_all), .Q(q0));
    jk_ff ff1(.J(ONE), .K(ONE), .clk(clk1),  .clr(clr_all), .Q(q1));
    jk_ff ff2(.J(ONE), .K(ONE), .clk(clk2),  .clr(clr_all), .Q(q2));
    jk_ff ff3(.J(ONE), .K(ONE), .clk(clk3),  .clr(clr_all), .Q(q3));
    jk_ff ff4(.J(ONE), .K(ONE), .clk(clk4),  .clr(clr_all), .Q(q4));

    assign Q = {q4, q3, q2, q1, q0};
endmodule


module tb_async_decadic_counter5;
    reg clk, rst;
    wire [4:0] Q;

    async_decadic_counter5 uut(.clk(clk), .rst(rst), .Q(Q));

    initial begin
        clk = 0; rst = 1; #5;
        rst = 0;

        $display("Time   Q4 Q3 Q2 Q1 Q0 (Decimal)");
        repeat (20) begin
            #5 clk = 1;
            #1 $display("%3dns  %b  (%0d)", $time, Q, Q);
            #4 clk = 0;
        end
        $finish;
    end
endmodule
