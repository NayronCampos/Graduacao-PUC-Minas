// Guia_0505.v
// Nayron Campos Soares
// Matricula 874422

module expr_nand (output wire y, input wire a, input wire b);
    // Uma porta NAND com entradas a e b gera: y = ~(a & b)
    nand (y, a, b);
endmodule

// Testbench para simulação
module testbench;
    reg a, b;
    wire y;

    expr_nand uut (y, a, b);

    initial begin
        $display(" a  b  |  y  ( ~(a & b) )");
        $display("-------------------------");
        a = 0; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 0; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        $finish;
    end
endmodule
