// -------------------------
// Guia_0805 - Complemento de 2 para 6 bits
// Nome: Nayron Campos Soares
// Matricula: 874422
// -------------------------

// Módulo oneComplement: calcula o complemento de 1 (inversão bit a bit)
module oneComplement(
    output [5:0] out,
    input  [5:0] in
);
    // Cada bit invertido
    not (out[0], in[0]);
    not (out[1], in[1]);
    not (out[2], in[2]);
    not (out[3], in[3]);
    not (out[4], in[4]);
    not (out[5], in[5]);
endmodule

// Módulo twoComplement6: calcula o complemento de 2 a partir do complemento de 1 + 1
module twoComplement6(
    output [5:0] twoComp,
    input  [5:0] in
);
    wire [5:0] oneComp;
    oneComplement OC(oneComp, in);
    
    // Vamos somar 1 (000001) ao complemento de 1 usando lógica de somador em cascata:
    // Para o bit 0 (LSB): usamos um half-adder com constante 1.
    // Para os bits 1 a 5: usamos a soma: soma = oneComp[i] XOR carry, carry = oneComp[i] & carry.
    wire c0, c1, c2, c3, c4;
    
    // Bit 0:
    xor (twoComp[0], oneComp[0], 1'b1);
    and (c0, oneComp[0], 1'b1);
    
    // Bit 1:
    xor (twoComp[1], oneComp[1], c0);
    and (c1, oneComp[1], c0);
    
    // Bit 2:
    xor (twoComp[2], oneComp[2], c1);
    and (c2, oneComp[2], c1);
    
    // Bit 3:
    xor (twoComp[3], oneComp[3], c2);
    and (c3, oneComp[3], c2);
    
    // Bit 4:
    xor (twoComp[4], oneComp[4], c3);
    and (c4, oneComp[4], c3);
    
    // Bit 5:
    xor (twoComp[5], oneComp[5], c4);
    // Não precisamos do carry final
endmodule

// Testbench para simulação
module test_Guia_0805;
    reg  [5:0] in;
    wire [5:0] twoComp;
    
    twoComplement6 TC(twoComp, in);
    
    initial begin
        $display("Guia_0805 ");
        $display(" in      twoComp");
        $monitor("%b   %b", in, twoComp);
        
   
        in = 6'b000000; #10;  // 0 -> 0
        in = 6'b000001; #10;  // 1 -> 111111 (representa -1 em 6 bits com sinal)
        in = 6'b000010; #10;  // 2 -> 111110 (representa -2)
        in = 6'b001010; #10;  // 10 -> complemento de 10
        in = 6'b010101; #10;  
        in = 6'b111111; #10;  // 63 -> 000001 (em aritmética de 2's complement, 63 negativo)
        
        $finish;
    end
endmodule
