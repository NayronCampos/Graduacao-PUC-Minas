// Guia_0505.v
// Nayron Campos Soares
// Matricula 874422

module xnor_nand (output wire y, input wire a, input wire b);

    wire not_a, not_b;             // ¬a e ¬b
    wire nand_ab, a_and_b;         // a NAND b e (a AND b) invertido para AND
    wire nand_not_a_b, not_a_and_not_b; // ¬a NAND ¬b e (¬a AND ¬b) invertido para AND
    wire inv1, inv2;               // Para implementar a OR com NAND

    // 1. Obter as negações usando NAND
    nand (not_a, a, a);           
    nand (not_b, b, b);           

    // 2. Obter a função AND de a e b:
    nand (nand_ab, a, b);          

    nand (a_and_b, nand_ab, nand_ab); // a_and_b = NAND(nand_ab, nand_ab) = a · b

    // 3. Obter a função AND de ¬a e ¬b:
    nand (nand_not_a_b, not_a, not_b); // nand_not_a_b = ¬(¬a · ¬b)
    nand (not_a_and_not_b, nand_not_a_b, nand_not_a_b); // not_a_and_not_b = ¬a · ¬b

    // 4. Implementar a operação OR dos dois termos:
    // OR = (a · b) + (¬a · ¬b)
    nand (inv1, a_and_b, a_and_b);          // inv1 = ¬(a_and_b) 
    nand (inv2, not_a_and_not_b, not_a_and_not_b); // inv2 = ¬(not_a_and_not_b)
    // Agora, a OR é:
    nand (y, inv1, inv2);  // y = NAND(inv1, inv2) = (a · b) + (¬a · ¬b) = XNOR
endmodule


module testbench;
    reg a, b;
    wire y;

    xnor_nand uut (y, a, b);

    initial begin
        $display(" a  b  |  y (XNOR)");
        $display("-------------------");
        a = 0; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 0; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        $finish;
    end
endmodule
