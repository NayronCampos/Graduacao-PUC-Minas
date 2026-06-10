/*
 Guia_0202.v
 874422 - Nayron Campos Soares
*/
module Guia_0202;
// Define as variáveis
real x = 0.75; // Valor decimal a ser convertido
integer y = 7 ; // Contador para a posição dos bits
reg [7:0] b = 0 ; // Armazena o número binário resultante

// Bloco inicial
initial
begin : main
  // Exibe informações iniciais
  $display("Guia_0202 - Tests");
  $display("x = %f", x);
  $display("b = 0.%8b", b);

  // Loop enquanto x for maior que 0 e y for maior ou igual a 0
  while (x > 0 && y >= 0) 
  begin
    // Se x * 2 for maior ou igual a 1, coloca 1 em b[y] e subtrai 1 de x
    if (x * 2 >= 1)
    begin
      b[y] = 1; // Define o bit como 1
      x = x * 2.0 - 1.0; // Ajusta x subtraindo 1
    end
    // Se x * 2 for menor que 1, coloca 0 em b[y] e multiplica x por 2
    else
    begin
      b[y] = 0; // Define o bit como 0
      x = x * 2.0; // Multiplica x por 2
    end // Fim do if

    // Exibe o valor binário de b a cada iteração
    $display("b = 0.%8b", b);
    y = y - 1; // Decrementa o contador y
  end // Fim do loop
end // Fim do bloco inicial
endmodule // Fim do módulo
