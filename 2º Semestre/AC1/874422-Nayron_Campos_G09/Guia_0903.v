`timescale 1ns/1ps

//Guia_0903
//Aluno: Nayron Campos Soares
//Matrícula: 874422

`include "clock.v"

module pulse(
  output reg signal,
  input        clock
);
  reg [1:0] cnt;
  initial begin
    signal = 1'b0;
    cnt    = 2'b00;
  end
  always @(posedge clock) begin
    if (cnt == 2) begin
      cnt    <= 2'b00;
      signal <= 1'b1;
    end else begin
      cnt    <= cnt + 1;
      signal <= 1'b0;
    end
  end
endmodule

module Guia_0903;
  wire clock;
  clock clk_gen(.clk(clock));

  wire pulse_out;
  pulse pulse1(.signal(pulse_out), .clock(clock));

  initial begin
    $dumpfile("Guia_0903.vcd");
    $dumpvars(1, clock, pulse_out);

    #720 $finish;
  end
endmodule
