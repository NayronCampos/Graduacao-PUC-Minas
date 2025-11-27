#x = 3;
#y = 4 ;
#z = ( 15*x + 67*y)*4

.text
.globl main
main:

addi $s0, $zero, 3 # x = 3
addi $s1, $zero, 4 # y = 4
addi $s2, $zero, 0 # z = 0
addi $t0, $zero, 0

#15 * x
while1: beq $s0,$zero,fim1
addi $s2, $s2, 15
addi $s0, $s0, -1

j while1

fim1:

while2: beq $s1,$zero,fim2
addi $s2, $s2, 67
addi $s1, $s1, -1

j while2

fim2:

#while de fora = *4, mas uso o conceito da multiplicação x2, no add s2, s2, s2
while3: beq $t0,2,fim3

add $s2, $s2, $s2
addi $t0, $t0, 1
j while3

fim3:

