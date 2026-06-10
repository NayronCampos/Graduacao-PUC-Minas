//874422 Nayron Campos Soares

module SoP (output s, input x, y, z); // mintermos


assign s = (~x&~y&~w&~z) | (~x&~y&w&~z) | (~x&y&w&~z) | (~x&y&w&z) | (x&~y&~w&~z) | (x&y&w&~z);
endmodule // SoP

//Testes

module test_module;
reg x, y, w, z;
wire s1;

SoP SOP1 (s1, x, y, w, z);

initial begin: start
 x=1'bx; y=1'bx; w=1'bx; z=1'bx; // indefinidos
end

initial begin: main


 $display("Test boolean expression");


 // monitoramento

 $display(" x  y  w  z =  s");
 $monitor("%2b %2b %2b %2b = %2b", x, y, w, z, s1);
 // sinalizacao
 #1 x=0; y=0; w=0; z=0;
 #1 x=0; y=0; w=0; z=1;
 #1 x=0; y=0; w=1; z=0;
 #1 x=0; y=0; w=1; z=1;
 #1 x=0; y=1; w=0; z=0;
 #1 x=0; y=1; w=0; z=1;
 #1 x=0; y=1; w=1; z=0;
 #1 x=0; y=1; w=1; z=1;
 #1 x=1; y=0; w=0; z=0;
 #1 x=1; y=0; w=0; z=1;
 #1 x=1; y=0; w=1; z=0;
 #1 x=1; y=0; w=1; z=1;
 #1 x=1; y=1; w=0; z=0;
 #1 x=1; y=1; w=0; z=1;
 #1 x=1; y=1; w=1; z=0;
 #1 x=1; y=1; w=1; z=1;
end
endmodule