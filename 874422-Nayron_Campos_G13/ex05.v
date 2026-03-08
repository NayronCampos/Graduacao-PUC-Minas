// Nome: Nayron Campos Soares
// Matrícula: 874422
// Ex 5

`timescale 1ns/1ps

module t_ff (
    input T,
    input clk,
    input clr,
    output reg Q
);
    always @(posedge clk) begin
        if (clr)
            Q <= 1'b0;
        else if (T)
            Q <= ~Q;
    end
endmodule

module sync_mod7_counter (
    input clk,
    input rst,
    output [2:0] Q
);
    wire T0, T1, T2;
    wire q0, q1, q2;

    assign T0 = 1'b1;
    assign T1 = q0;
    assign T2 = q0 & q1;

    wire is7 = q2 & q1 & q0;
    wire clr_sync = is7 | rst;

    t_ff ff0(.T(T0), .clk(clk), .clr(clr_sync), .Q(q0));
    t_ff ff1(.T(T1), .clk(clk), .clr(clr_sync), .Q(q1));
    t_ff ff2(.T(T2), .clk(clk), .clr(clr_sync), .Q(q2));

    assign Q = {q2, q1, q0};
endmodule


module tb_sync_mod7_counter;
    reg clk = 0;
    reg rst = 1;
    wire [2:0] Q;

    sync_mod7_counter uut(.clk(clk), .rst(rst), .Q(Q));

    // Clock gerado a cada 5 ns
    always #5 clk = ~clk;

    initial begin
        $display("Time   Q2 Q1 Q0");
        #10 rst = 0;  // libera reset no primeiro pulso de clock
    end

    always @(posedge clk) begin
        $display("%4dns   %b  %b  %b", $time, Q[2], Q[1], Q[0]);
    end

    initial begin
        #100 $finish;
    end
endmodule
