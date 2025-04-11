// Guia_0505.v
// Nayron Campos Soares
// Matricula 874422


module xor_nor (output wire y, input wire a, input wire b);
    wire not_a, not_b;
    wire term1, term2;
    wire xnor_result;

    // Gerar as negações de a e b
    nor (not_a, a, a);    // not_a = a NOR a = ¬a
    nor (not_b, b, b);    // not_b = b NOR b = ¬b

    // Gerar os termos para o XNOR:
    // term1 = b NOR (a NOR a) = b NOR ¬a
    // term2 = a NOR (b NOR b) = a NOR ¬b
    nor (term1, b, not_a);
    nor (term2, a, not_b);

   
    nor (xnor_result, term1, term2);

    // Inverter o XNOR para obter a XOR:
    nor (y, xnor_result, xnor_result);
endmodule

module testbench;
    reg a, b;
    wire y;

    xor_nor uut (y, a, b);

    initial begin
        $display("A B | Y (XOR)");
        $display("-------------");
        a = 0; b = 0; #10; $display("%b %b | %b", a, b, y);
        a = 0; b = 1; #10; $display("%b %b | %b", a, b, y);
        a = 1; b = 0; #10; $display("%b %b | %b", a, b, y);
        a = 1; b = 1; #10; $display("%b %b | %b", a, b, y);
        $finish;
    end
endmodule
