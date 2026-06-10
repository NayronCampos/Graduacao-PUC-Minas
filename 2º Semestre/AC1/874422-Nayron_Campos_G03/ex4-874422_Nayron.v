/*
  ex4-874422_Nayron.v - Operações de Subtração com Complemento de 2
  Autor: 874422 - Nayron Campos
  Data: [Data]
*/

module ex4_874422_Nayron;
// Definição de dados
reg signed [7:0] a = 8'b00101100; // 44 em decimal
reg signed [7:0] b = 8'b00001110; // 14 em decimal
reg signed [7:0] resultado;

initial begin
    $display("ex4-874422_Nayron - Operações de Subtração com Complemento de 2");
    resultado = a - b; // Subtração usando complemento de 2
    
    $display("%d - %d = %d", a, b, resultado);
    $display("Binário: %8b - %8b = %8b", a, b, resultado);
end
endmodule
