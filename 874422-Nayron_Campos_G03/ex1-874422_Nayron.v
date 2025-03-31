/*
  ex1-874422_Nayron.v - Cálculo do Complemento de 1 e 2
  Autor: 874422 - Nayron Campos
  Data: [Data]
*/

module ex1_874422_Nayron;
// Definição de dados
reg [5:0] a = 6'b1011; // Número de entrada
reg [5:0] c1, c2; // Complementos

initial begin
    $display("ex1_874422_Nayron - Cálculo do Complemento de 1 e 2");
    c1 = ~a; // Complemento de 1
    c2 = ~a + 1; // Complemento de 2
    
    $display("Número: %6b", a);
    $display("Complemento de 1: %6b", c1);
    $display("Complemento de 2: %6b", c2);
end
endmodule
