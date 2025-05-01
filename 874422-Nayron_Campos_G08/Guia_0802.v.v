// -------------------------
// Guia_0802 - AU com Subtrator Completo
// Nome: Nayron Campos Soares
// Matricula: 874422
// -------------------------

// Módulo halfSubtractor: calcula a "meia-diferença"
// Fórmulas:
//   diff   = a XOR b
//   borrow = (~a) & b
module halfSubtractor (
    output diff,
    output borrow,
    input  a,
    input  b
);
    xor  (diff, a, b);
    not  (na, a);
    and  (borrow, na, b);
endmodule

// Módulo fullSubtractor: calcula a subtração completa com borrow de entrada
// Fórmulas:
//   diff = a XOR b XOR bin
//   borrow_out = ( (~a & b) ) + ( (~(a XOR b)) & bin )
module fullSubtractor (
    output diff,
    output bout,
    input  a,
    input  b,
    input  bin
);
    wire diff1, borrow1, borrow2;
    
    // Primeiro estágio: subtração de a e b
    halfSubtractor HS1(diff1, borrow1, a, b);
    // Segundo estágio: subtração de diff1 e o borrow de entrada
    halfSubtractor HS2(diff, borrow2, diff1, bin);
    // O borrow de saída é a OR dos dois borrows
    or (bout, borrow1, borrow2);
endmodule

// Módulo principal: Subtrator de 6 bits
// Entradas: 
//    minuend (6 bits) e subtrahend (6 bits)
// Saídas:
//    diff (6 bits de resultado) e borrowOut (borrow final)
module Guia_0802 (
    output [5:0] diff,
    output       borrowOut,
    input  [5:0] minuend,
    input  [5:0] subtrahend
);
    wire b0, b1, b2, b3, b4;
    
    // Bit 0: usa halfSubtractor (não há borrow de entrada)
    halfSubtractor HS0(diff[0], b0, minuend[0], subtrahend[0]);
    
    // Bits 1 a 5: usa fullSubtractor, encadeando o borrow
    fullSubtractor FS1(diff[1], b1, minuend[1], subtrahend[1], b0);
    fullSubtractor FS2(diff[2], b2, minuend[2], subtrahend[2], b1);
    fullSubtractor FS3(diff[3], b3, minuend[3], subtrahend[3], b2);
    fullSubtractor FS4(diff[4], b4, minuend[4], subtrahend[4], b3);
    fullSubtractor FS5(diff[5], borrowOut, minuend[5], subtrahend[5], b4);
endmodule

// Testbench para simular o subtrator
module test_Guia_0802;
    reg  [5:0] minuend, subtrahend;
    wire [5:0] diff;
    wire       borrowOut;
    
    Guia_0802 UUT(diff, borrowOut, minuend, subtrahend);
    
    initial begin
        $display("Guia_0802");
        $display("minuend   subtrahend   diff   borrowOut");
        
        // Teste 1: 10 - 3
        // Ex.: 10 = 6'b00_1010,  3 = 6'b00_0011
        minuend = 6'b001010; subtrahend = 6'b000011; #10;
        $display("%b   %b   %b   %b", minuend, subtrahend, diff, borrowOut);
        
        // Teste 2: 3 - 10 (resultado negativo com borrow)
        minuend = 6'b000011; subtrahend = 6'b001010; #10;
        $display("%b   %b   %b   %b", minuend, subtrahend, diff, borrowOut);
        
        // Teste 3: 15 - 5
        minuend = 6'b001111; subtrahend = 6'b000101; #10;
        $display("%b   %b   %b   %b", minuend, subtrahend, diff, borrowOut);
        
        // Teste 4: 0 - 1
        minuend = 6'b000000; subtrahend = 6'b000001; #10;
        $display("%b   %b   %b   %b", minuend, subtrahend, diff, borrowOut);
        
        $finish;
    end
endmodule
