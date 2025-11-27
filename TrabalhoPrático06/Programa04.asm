.text
.globl main
main:

    addi $s0, $zero, 3     # x = 3
    addi $s1, $zero, 4     # y = 4


    # 15 = 8 + 4 + 2 + 1
    sll $t0, $s0, 3         # t0 = x * 8
    sll $t1, $s0, 2         # t1 = x * 4
    sll $t2, $s0, 1         # t2 = x * 2

    add $t3, $t0, $t1       # t3 = 8x + 4x = 12x
    add $t3, $t3, $t2       # t3 = 12x + 2x = 14x
    add $t3, $t3, $s0       # t3 = 14x + x = 15x

    # 67 = 64 + 2 + 1
    sll $t4, $s1, 6         # t4 = y * 64
    sll $t5, $s1, 1         # t5 = y * 2
    add $t6, $t4, $t5       # t6 = 64y + 2y = 66y
    add $t6, $t6, $s1       # t6 = 66y + y = 67y

    add $s2, $t3, $t6       # s2 = (15x + 67y)

    sll $s2, $s2, 2         # s2 = resultado * 4

 
