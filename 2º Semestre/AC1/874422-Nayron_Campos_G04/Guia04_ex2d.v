//874422 - Nayron Campos Soares

module fxy (output s1, output s2, input x, y);
assign s1 = ~( x & ~y ) | ~( x | ~y ) ;
assign s2 = ( ~x & y ) ;
endmodule // fxy

//Testes
module test_module;
reg x, y, z;
wire s1;


fxy FXY1 (s1, s2, x, y);
 // valores iniciais
initial begin: start
 x=1'bx; y=1'bx; z=1'bx; // indefinidos
end

initial begin: main

 $display("Test boolean expression");

 // monitoramento
 $display(" x  y =  s1 s2");
 $monitor("%2b %2b = %2b %2b", x, y, s1, s2);
 // sinalizacao
 #1 x=0; y=0;
 #1 x=0; y=1;
 #1 x=1; y=0;
 #1 x=1; y=1;

end
endmodule 