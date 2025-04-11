// Guia_0507.v
// Nayron Campos Soares
// Matricula 874422

module expr_nor (output wire y, input wire a, input wire b);

    wire not_a, not_b;     // para ~a e ~b
    wire Ptemp, Qtemp;     // saídas intermediárias das NOR para OR
    wire P, Q;             // P = a OR ~b e Q = ~a OR b
    wire not_P, not_Q;     // inversões de P e Q para formar AND

    // 1. Obter as negações:
    nor (not_a, a, a);    
    nor (not_b, b, b);    

    // Primeiro, Ptemp = nor(a, not_b) = ~(a OR ~b)
    nor (Ptemp, a, not_b);

    nor (P, Ptemp, Ptemp);

    // 3. Construir Q = ~a OR b:

    nor (Qtemp, not_a, b);

    nor (Q, Qtemp, Qtemp);

    // 4. Implementar a AND entre P e Q:
    // P AND Q = ~(~P OR ~Q)
    nor (not_P, P, P);  // not_P = ~P
    nor (not_Q, Q, Q);  // not_Q = ~Q

    // y = nor(not_P, not_Q) = ~(not_P OR not_Q) = P AND Q
    nor (y, not_P, not_Q);
endmodule


module testbench;
    reg a, b;
    wire y;

    expr_nor uut (y, a, b);

    initial begin
        $display(" a  b  |  y  ( ~(~a ^ ~b) )");
        $display("-----------------------------");
        a = 0; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 0; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 0; #10; $display(" %b  %b  |  %b", a, b, y);
        a = 1; b = 1; #10; $display(" %b  %b  |  %b", a, b, y);
        $finish;
    end
endmodule
