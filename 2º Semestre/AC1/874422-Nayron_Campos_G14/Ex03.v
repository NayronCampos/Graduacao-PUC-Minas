// Nayron Campos - Matrícula: 874422
// Exercicio 03 

module ex03(output reg [4:0] q, input clk, input load);
    always @(posedge clk) begin
        if (load)
            q <= 5'b00001; // Carrega 1 no primeiro estágio
        else
            q <= {q[0], q[4:1]}; // Deslocamento circular para direita
    end
endmodule

module tb_ex03;
    reg clk, load;
    wire [4:0] q;
    ex03 dut(q, clk, load);

    initial begin
        $monitor("Time = %0t | q = %b", $time, q);
        clk = 0; load = 1; #5;
        clk = 1; #5;
        load = 0;
        repeat (10) begin
            clk = 0; #5;
            clk = 1; #5;
        end
        $finish;
    end
endmodule
