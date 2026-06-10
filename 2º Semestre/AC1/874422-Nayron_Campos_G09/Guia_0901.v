`timescale 1ns/1ps

//Guia_0901
//Aluno: Nayron Campos Soares
//Matrícula: 874422

module clock(output reg clk);
  initial clk = 1'b0;
  always #12 clk = ~clk;
endmodule

module pulse(output reg signal, input clock);
  initial    signal = 1'b0;
  always @(clock) begin
    signal = 1'b1; #3 signal = 1'b0;
    #3 signal = 1'b1; #3 signal = 1'b0;
  end
endmodule

module trigger(output reg signal, input on, input clock);
  initial signal = 1'b0;
  always @(posedge clock) begin
    if (on) begin
      #60 signal = 1'b1;
      #60 signal = 1'b0;
    end
  end
endmodule

module Guia_0901;
  wire clock;
  clock clk_gen(.clk(clock));

  wire p1, t1;
  reg  p = 1'b0;

  pulse   pulse1  (.signal(p1), .clock(clock));
  trigger trigger1(.signal(t1), .on(p), .clock(clock));

  initial begin
    $dumpfile("Guia_0901.vcd");
    $dumpvars(1, clock, p1, p, t1);
  end

  initial begin
    #60  p = 1'b1;
    #120 p = 1'b0;
    #180 p = 1'b1;
    #240 p = 1'b0;
    #300 p = 1'b1;
    #360 p = 1'b0;
    #376 $finish;
  end
endmodule
