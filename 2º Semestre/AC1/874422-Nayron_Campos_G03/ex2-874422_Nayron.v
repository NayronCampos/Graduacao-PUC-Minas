/*
  ex2-874422_Nayron.v - Complementos para diferentes bases
  Autor: 874422 - Nayron Campos
  Data: [Data]
*/

module ex2_874422_Nayron;
// Definição de dados
reg [7:0] a = 8'hC4; // Número hexadecimal
reg [5:0] b = 6'o31; // Número octal
reg [7:0] c1, c2; // Complementos

initial begin
    $display("ex2_874422_Nayron - Complementos para diferentes bases");
    c1 = ~a; // Complemento de 1
    c2 = ~a + 1; // Complemento de 2
    
    $display("Número Hex: %h -> Complemento de 1: %8b, Complemento de 2: %8b", a, c1, c2);
    c1 = ~b; 
    c2 = ~b + 1;
    $display("Número Octal: %o -> Complemento de 1: %6b, Complemento de 2: %6b", b, c1, c2);
end
endmodule
