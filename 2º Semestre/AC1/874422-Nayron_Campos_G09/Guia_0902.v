`timescale 1ns/1ps

//Guia_0902
//Aluno: Nayron Campos Soares
//Matrícula: 874422

module clock(
  output reg clk
);
  // inicializa o clock em 0
  initial clk = 1'b0;
  // inverte a cada 12 unidades de tempo (período = 24)
  always #12 clk = ~clk;
endmodule



`include "clock.v"

// pulse1: dispara 3 pulsos de 4 u.t. cada em posedge clock
module pulse1(
  output reg signal,
  input        clock
);
  initial signal = 1'b0;
  always @(posedge clock) begin
    signal = 1'b1;   #4 signal = 1'b0;
    #4    signal = 1'b1;   #4 signal = 1'b0;
    #4    signal = 1'b1;   #4 signal = 1'b0;
  end
endmodule

module pulse2(
  output reg signal,
  input        clock
);
  initial signal = 1'b0;
  always @(posedge clock) begin
    signal = 1'b1;
    #5 signal = 1'b0;
  end
endmodule

module pulse3(
  output reg signal,
  input        clock
);
  initial signal = 1'b0;
  always @(negedge clock) begin
    signal = 1'b1;    #15 signal = 1'b0;
    #15   signal = 1'b1;
  end
endmodule

module pulse4(
  output reg signal,
  input        clock
);
  initial signal = 1'b0;
  always @(negedge clock) begin
    signal = 1'b1;    #20 signal = 1'b0;
    #20   signal = 1'b1;    #20 signal = 1'b0;
  end
endmodule


module Guia_0902;
  wire clock;
  clock clk_gen(.clk(clock));

  wire p1, p2, p3, p4;

  pulse1 pls1(.signal(p1), .clock(clock));
  pulse2 pls2(.signal(p2), .clock(clock));
  pulse3 pls3(.signal(p3), .clock(clock));
  pulse4 pls4(.signal(p4), .clock(clock));

  initial begin
    $dumpfile("Guia_0902.vcd");
    $dumpvars(1, clock, p1, p2, p3, p4);
    #480 $finish;
  end
endmodule
