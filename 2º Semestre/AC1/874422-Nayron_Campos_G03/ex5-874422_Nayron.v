/*
  ex5-874422_Nayron.v - Operações Aritméticas e Armazenamento
  Autor: 874422 - Nayron Campos
  Data: [Data]
*/

module ex5_874422_Nayron;
// Definição de dados
reg signed [7:0] a = 8'b00011010; // 26 em decimal
reg signed [7:0] b = 8'b00000101; // 5 em decimal
reg signed [7:0] soma, subtracao;

initial begin
    $display("ex5_874422_Nayron - Operações Aritméticas e Armazenamento");
    soma = a + b;
    subtracao = a - b;
    
    $display("Soma: %d + %d = %d", a, b, soma);
    $display("Subtração: %d - %d = %d", a, b, subtracao);
    $display("Binário Soma: %8b + %8b = %8b", a, b, soma);
    $display("Binário Subtração: %8b - %8b = %8b", a, b, subtracao);
end
endmodule
