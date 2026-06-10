// 05 - Nayron Campos - Matrícula: 874422

module parallel_to_serial (
    input clk, input load, input clear,
    input [4:0] D,
    output Qout
);
    reg [4:0] shift_reg;

    assign Qout = shift_reg[4];

    always @(posedge clk) begin
        if (clear)
            shift_reg <= 0;
        else if (load)
            shift_reg <= D;
        else
            shift_reg <= shift_reg << 1;
    end
endmodule

module tb_parallel_to_serial;
    reg clk = 0, load = 1, clear = 0;
    reg [4:0] D = 5'b10101;
    wire Qout;

    parallel_to_serial uut(clk, load, clear, D, Qout);

    always #5 clk = ~clk;

    initial begin
        $display("05) PARALLEL TO SERIAL");
        $monitor("Qout = %b", Qout);

        #10 load = 0;
        #50 $finish;
    end
endmodule
