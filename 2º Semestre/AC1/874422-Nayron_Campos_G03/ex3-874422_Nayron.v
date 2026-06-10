/*
  ex3-874422_Nayron.v - Conversão de Complemento de 2 para Decimal
  Autor: 874422 - Nayron Campos
  Data: [Data]
*/

module ex3_874422_Nayron;
// Definição de dados
reg signed [7:0] a = 8'b10110101; // Número em complemento de 2
integer decimal;

initial begin
    $display("ex3_874422_Nayron - Conversão de Complemento de 2 para Decimal");
    decimal = a; // Conversão automática para decimal
    
    $display("Número Binário: %8b -> Decimal: %d", a, decimal);
end
endmodule
