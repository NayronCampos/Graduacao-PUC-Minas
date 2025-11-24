#x = 1;
#y = 5*x + 15;

.text
.globl main
main:

addi $s0, $zero, 1 # x= 1

while: beq $s0,$zero,fim
addi $s1, $s1, 5
subi $s0, $s0, 1

j while

fim:

addi $s1, $s1, 15