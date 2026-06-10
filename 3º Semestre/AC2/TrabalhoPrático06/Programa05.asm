.text
.globl main
main:
    # x = 100000
    lui $s0, 1            # carrega 1 em bits altos -> 1 << 16 = 65536
    ori $s0, $s0, 34464   # soma a parte baixa (34464 + 65536 = 100000)
    # agora $s0 = 100000

    # y = 200000
    lui $s1, 3            # 3 << 16 = 196608
    ori $s1, $s1, 3392    # 196608 + 3392 = 200000
    # agora $s1 = 200000

    # z = x + y
    add $s2, $s0, $s1     # z = 100000 + 200000 = 300000

    # fim
    jr $ra
