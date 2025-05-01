// -------------------------
// Guia_0804 - Comparador de Desigualdade 6 bits
// Nome: Nayron Campos Soares
// Matricula: 874422
// -------------------------
module Guia_0804(
    output s,         // Saída: 1 se os operandos forem diferentes, 0 se forem iguais
    input  [5:0] a,   // Operando A (6 bits)
    input  [5:0] b    // Operando B (6 bits)
);
    // Detecta a diferença em cada bit com XOR:
    wire d0, d1, d2, d3, d4, d5;
    xor (d0, a[0], b[0]);
    xor (d1, a[1], b[1]);
    xor (d2, a[2], b[2]);
    xor (d3, a[3], b[3]);
    xor (d4, a[4], b[4]);
    xor (d5, a[5], b[5]);
    
    // A porta OR combina os resultados: se algum bit for diferente, s = 1.
    or (s, d0, d1, d2, d3, d4, d5);
endmodule

// -------------------------
// Testbench para Guia_0804.v
// -------------------------
module test_Guia_0804;
    reg [5:0] a, b;
    wire s;
    
    // Instancia o comparador
    Guia_0804 comp(.s(s), .a(a), .b(b));
    
    initial begin
        $display("Guia_0804");
        $display("   a       b       s");
        $monitor("%b   %b   %b", a, b, s);
        
        // Teste 1: Operandos iguais -> s deve ser 0
        a = 6'b000000; b = 6'b000000; #10;
        // Teste 2: Operandos iguais
        a = 6'b101010; b = 6'b101010; #10;
        // Teste 3: Operandos iguais (todos 1)
        a = 6'b111111; b = 6'b111111; #10;
        // Teste 4: Operandos diferentes -> s deve ser 1
        a = 6'b101010; b = 6'b010101; #10;
        // Teste 5: Apenas 1 bit diferente -> s deve ser 1
        a = 6'b100110; b = 6'b100111; #10;
        
        $finish;
    end
endmodule
