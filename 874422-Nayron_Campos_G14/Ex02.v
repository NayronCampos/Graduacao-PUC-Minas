// 02 - Nayron Campos - Matrícula: 874422

module dff (output reg q, output reg qnot,
            input d, input clk,
            input preset, input clear);

    always @(posedge clk) begin
        if (clear) begin q <= 0; qnot <= 1; end
        else if (preset) begin q <= 1; qnot <= 0; end
        else begin q <= d; qnot <= ~d; end
    end
endmodule

module shift_left_all_preset (
    input clk, input load, input clear,
    output [4:0] Q
);
    wire [4:0] d, qnot;

    assign d[0] = load ? 1'b1 : Q[1];
    assign d[1] = load ? 1'b1 : Q[2];
    assign d[2] = load ? 1'b1 : Q[3];
    assign d[3] = load ? 1'b1 : Q[4];
    assign d[4] = load ? 1'b1 : 1'b0;

    dff ff0(Q[0], qnot[0], d[0], clk, load, clear);
    dff ff1(Q[1], qnot[1], d[1], clk, load, clear);
    dff ff2(Q[2], qnot[2], d[2], clk, load, clear);
    dff ff3(Q[3], qnot[3], d[3], clk, load, clear);
    dff ff4(Q[4], qnot[4], d[4], clk, load, clear);
endmodule

module tb_shift_left_all_preset;
    reg clk = 0, load = 1, clear = 0;
    wire [4:0] Q;

    shift_left_all_preset uut(clk, load, clear, Q);

    always #5 clk = ~clk;

    initial begin
        $monitor("TQ = %b", Q);

        #10 load = 0;
        #50 $finish;
    end
endmodule
