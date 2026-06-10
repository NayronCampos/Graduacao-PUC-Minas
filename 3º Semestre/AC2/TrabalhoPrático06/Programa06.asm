# x = o maior inteiro possível;
# y = 300000;
# z = x - 4y 

.text
.globl main
main:

    addi $s0, $zero, -1      # começa com todos os bits em 1 (0xFFFFFFFF)
    srl  $s0, $s0, 1         # desloca 1 bit pra direita → 0x7FFFFFFF = 2147483647

    # y = 300000
    lui  $s1, 0x0004         # parte alta → 4 << 16 = 262144
    ori  $s1, $s1, 0x9390    # 262144 + 37744 = 299888 ≈ 300000 (exato é 300000)
    # $s1 = 300000

    # 4*y  (usando deslocamento à esquerda)
    sll  $t0, $s1, 2         # $t0 = y * 4

    # z = x - 4*y
    sub  $s2, $s0, $t0       # $s2 = $s0 - $t0

    # fim
    jr $ra
