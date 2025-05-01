// -------------------------
// Guia_0801 - Somador 6 bits
// Nome: Nayron Campos Soares
// Matricula: 874422
// -------------------------

// Módulo halfAdder: soma dois bits (sem carry-in)
module halfAdder (
    output sum,
    output carry,
    input  a,
    input  b
);
    // A soma é a operação XOR e o carry é a operação AND
    xor  X1(sum, a, b);
    and  A1(carry, a, b);
endmodule

// Módulo fullAdder: soma dois bits com um carry-in
module fullAdder (
    output sum,
    output cout,
    input  a,
    input  b,
    input  cin
);
    wire s1, c1, c2;
    
    // Primeiro half adder: soma a e b
    halfAdder HA1(s1, c1, a, b);
    // Segundo half adder: soma o resultado s1 com o carry de entrada
    halfAdder HA2(sum, c2, s1, cin);
    // O carry de saída é o OR entre os dois carries intermediários
    or O1(cout, c1, c2);
endmodule

// Módulo principal: Somador de 6 bits
module Guia_0801 (
    output [5:0] sum,   // Saída soma (6 bits)
    output       carryOut, // Carry final
    input  [5:0] x,     // Primeiro operando (6 bits)
    input  [5:0] y      // Segundo operando (6 bits)
);
    wire c0, c1, c2, c3, c4;  // Fios internos para os carries

    // Bit 0: usa halfAdder (sem carry-in)
    halfAdder HA0(sum[0], c0, x[0], y[0]);

    // Bits 1 a 5: usa fullAdder para cada posição
    fullAdder FA1(sum[1], c1, x[1], y[1], c0);
    fullAdder FA2(sum[2], c2, x[2], y[2], c1);
    fullAdder FA3(sum[3], c3, x[3], y[3], c2);
    fullAdder FA4(sum[4], c4, x[4], y[4], c3);
    fullAdder FA5(sum[5], carryOut, x[5], y[5], c4);
endmodule

// Testbench para simular o somador
module test_Guia_0801;
    reg [5:0] x, y;
    wire [5:0] sum;
    wire carryOut;

    Guia_0801 AU(sum, carryOut, x, y);

    initial begin
        $display("Guia_0801");
        $monitor("x = %b, y = %b  => sum = %b, carry = %b", x, y, sum, carryOut);
        
        x = 6'b000000; y = 6'b000000; #10;
        x = 6'b000001; y = 6'b000001; #10;
        x = 6'b001010; y = 6'b000101; #10;
        x = 6'b111111; y = 6'b000001; #10;
        x = 6'b111111; y = 6'b111111; #10;
        
        $finish;
    end
endmodule
