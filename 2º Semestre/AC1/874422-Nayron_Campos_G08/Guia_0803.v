// -------------------------
// Guia_0803 - Comparador de Igualdade 6 bits
// Nome: Nayron Campos Soares
// Matricula: 874422
// -------------------------
module Guia_0803(
    output s,         // Saída: 1 se os dois operandos forem iguais, 0 caso contrário
    input  [5:0] a,   // Operando A (6 bits)
    input  [5:0] b    // Operando B (6 bits)
);
    // Compara cada par de bits usando XNOR (que resulta em 1 se os bits forem iguais)
    wire eq0, eq1, eq2, eq3, eq4, eq5;
    xnor (eq0, a[0], b[0]);
    xnor (eq1, a[1], b[1]);
    xnor (eq2, a[2], b[2]);
    xnor (eq3, a[3], b[3]);
    xnor (eq4, a[4], b[4]);
    xnor (eq5, a[5], b[5]);
    
    // Se todos os XNORs forem 1 (ou seja, todos os bits forem iguais),
    // a porta AND combinará essas saídas e gerará s = 1.
    and (s, eq0, eq1, eq2, eq3, eq4, eq5);
endmodule

// -------------------------
// Testbench para Guia_0803.v
// -------------------------
module test_Guia_0803;
    reg [5:0] a, b;
    wire s;
    
    // Instancia o comparador de igualdade
    Guia_0803 comp(.s(s), .a(a), .b(b));
    
    initial begin
        $display("Guia_0803");
        $display(" a       b       s");
        $monitor("%b   %b   %b", a, b, s);
        
        // Teste 1: ambos os operandos iguais
        a = 6'b000000; b = 6'b000000; #10;
        // Teste 2: ambos iguais
        a = 6'b101010; b = 6'b101010; #10;
        // Teste 3: ambos iguais (todos 1)
        a = 6'b111111; b = 6'b111111; #10;
        // Teste 4: operandos diferentes
        a = 6'b101010; b = 6'b010101; #10;
        // Teste 5: apenas 1 bit diferente
        a = 6'b100110; b = 6'b100111; #10;
        
        $finish;
    end
endmodule
