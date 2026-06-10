module PoS (output S,
 input X, Y);
 
 //maxtermos
assign S = ( X | ~Y ) & ( ~X | ~Y );
endmodule // PoS

module SoP (output s,
 input x, y);
 // mintermos
assign s = ( ~x & ~y ) | ( X & ~Y ) ;
endmodule // SoP

// Teste

module test_module;
reg x, y, z;
wire s1, s2;


SoP SOP1 (s1, x, y);
PoS POS1 (s2, x, y);


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
endmodule // test_module