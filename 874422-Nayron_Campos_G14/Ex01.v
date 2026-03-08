// Exercício 01 
// Nayron Campos
// 874422

`timescale 1ns/1ps

module dff (output reg q, output reg qnot,
            input d, input clk,
            input preset, input clear);
  always @(posedge clk) begin
    if (clear) begin q <= 0; qnot <= 1; end
    else if (preset) begin q <= 1; qnot <= 0; end
    else begin q <= d; qnot <= ~d; end
  end
endmodule

module shift_left_unitary (
  input clk,
  input ld,
  input clr,
  output [4:0] Q
);
  wire [4:0] q, qnot;
  wire [4:0] d;

  assign d[0] = 1'b0;              // Q0 entra 0 sempre
  assign d[1] = q[0];
  assign d[2] = q[1];
  assign d[3] = q[2];
  assign d[4] = q[3];

  dff ff0(q[0], qnot[0], d[0], clk, ld, clr);
  dff ff1(q[1], qnot[1], d[1], clk, 1'b0, clr);
  dff ff2(q[2], qnot[2], d[2], clk, 1'b0, clr);
  dff ff3(q[3], qnot[3], d[3], clk, 1'b0, clr);
  dff ff4(q[4], qnot[4], d[4], clk, 1'b0, clr);

  assign Q = q;
endmodule

module tb_shift_left_unitary;
  reg clk = 0;
  reg ld = 0;
  reg clr = 1;
  wire [4:0] Q;

  shift_left_unitary uut (.clk(clk), .ld(ld), .clr(clr), .Q(Q));

  always #5 clk = ~clk;

  initial begin
    $display("Time\tQ4 Q3 Q2 Q1 Q0");
    clr = 1; ld = 0; #10;
    clr = 0; ld = 1; #10;  // ativa preset em Q0
    ld = 0;

    repeat (6) begin
      @(posedge clk);
      $display("%4dns\t%b %b %b %b %b", $time, Q[4], Q[3], Q[2], Q[1], Q[0]);
    end
    $finish;
  end
endmodule
