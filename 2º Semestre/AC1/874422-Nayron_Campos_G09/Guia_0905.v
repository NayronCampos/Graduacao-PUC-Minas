`timescale 1ns/1ps
`include "clock.v"

//Guia_0905
//Aluno: Nayron Campos Soares
//Matrícula: 874422

module pulse (
  output reg signal,
  input        clock
);
  initial signal = 1'b0;
  always @(posedge clock) begin
    signal = 1'b1;
    #3     signal = 1'b0;
  end
endmodule



module Guia_0905;
  wire clock;
  wire p;

  clock clk_gen(.clk(clock));

  pulse U1 (.signal(p), .clock(clock));

  initial begin
    $dumpfile("Guia_0905.vcd");
    $dumpvars(1, clock, p);

    #240 $finish;
  end
endmodule
