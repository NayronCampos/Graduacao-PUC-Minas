// Nayron Campos - Matrícula: 874422
// Exercicio 04 

module ex04(output reg [4:0] q, input clk, input load);
    always @(posedge clk) begin
        if (load)
            q <= 5'b10000; 
        else
            q <= {q[3:0], ~q[4]}; 
    end
endmodule

module tb_ex04;
    reg clk, load;
    wire [4:0] q;
    ex04 dut(q, clk, load);

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