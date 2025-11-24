# Ex01
# Nayron Campos Soares
# Matrícula: 874422

.text
.globl main
main:

#addi $s0, $zero, 1 x = 1
#addi $s0, $s0, 1 x= x+1






#y = a – b + x;
#b = x – y;

addi $s0, $zero, 2 #a =2;
addi $s1, $zero, 3 #b =3;
addi $s2, $zero, 4 #c =4;
addi $s3, $zero, 5 #d =5;

add $t0, $s0, $s1 
add $t1, $s2, $s3
sub $t0, $t0, $t1 #x = (a+b) - (c+d);
# t0 = x
# t2 = y

sub $t2, $s0, $s1
add $t2, $t2, $t0

sub $s1, $t0, $t2