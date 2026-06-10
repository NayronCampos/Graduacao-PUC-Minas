`timescale 1ns/1ps
`include "clock.v"

//Guia_0904
//Aluno: Nayron Campos Soares
//Matrícula: 874422

module pulse4x(
  output reg signal,
  input        clock
);
  // contador módulo 4
  reg [1:0] cnt = 2'd0;
  initial   signal = 1'b0;

  always @(posedge clock) begin
    if (cnt == 2'd0) begin

      signal = 1'b1;
      #24    signal = 1'b0;
    end
    cnt <= cnt + 2'd1;
  end
endmodule


module Guia_0904;
  wire clock;
  wire p4;

  clock clk_gen(.clk(clock));

  pulse4x U1(.signal(p4), .clock(clock));

  initial begin
    $dumpfile("Guia_0904.vcd");
    $dumpvars(1, clock, p4);
 
    #960 $finish;
  end
endmodule
