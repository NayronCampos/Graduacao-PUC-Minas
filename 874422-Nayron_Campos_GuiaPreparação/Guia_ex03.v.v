module mux3 (output s, input a, input b, input c);
  wire not_a, not_b, not_c;
  wire m1, m2;

  // Inversores
  not (not_a, a);
  not (not_b, b);
  not (not_c, c);

  // MUX1: se c = 0 então m1 = a; se c = 1 então m1 = b.
  assign m1 = (~c & a) | (c & b);

  // MUX2: se c = 0 então m2 = ~b; se c = 1 então m2 = ~a.
  assign m2 = (~c & not_b) | (c & not_a);

  // MUX Final: chave = not_c
  // Se not_c = 0 (c = 1) então s = M1; se not_c = 1 (c = 0) então s = M2.
  assign s = (not_c & m2) | (c & m1);
endmodule
module test_mux3;

  reg a, b, c;
  wire s;

  // Instancia o módulo
  mux3 uut (s, a, b, c);

  initial begin
    $display(" a | b | c | s ");
    $display("---------------");
    
    // Vamos testar todas as combinações (mesmo que 'a' não influencie o resultado final)
    a = 0; b = 0; c = 0; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 1
    a = 0; b = 0; c = 1; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 0
    a = 0; b = 1; c = 0; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 0
    a = 0; b = 1; c = 1; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 1
    a = 1; b = 0; c = 0; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 1
    a = 1; b = 0; c = 1; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 0
    a = 1; b = 1; c = 0; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 0
    a = 1; b = 1; c = 1; #1 $display(" %b | %b | %b | %b", a, b, c, s); // Espera s = 1

    $finish;
  end

endmodule
