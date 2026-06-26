from estado import OBJETIVO


def h1_fora_do_lugar(estado):

    erros = 0

    for i in range(9):

        if estado[i] != 0 and estado[i] != OBJETIVO[i]:
            erros += 1

    return erros


def h2_manhattan(estado):

    distancia = 0

    for i in range(9):

        valor = estado[i]

        if valor == 0:
            continue

        linha_atual = i // 3
        coluna_atual = i % 3

        pos_obj = valor

        linha_obj = pos_obj // 3
        coluna_obj = pos_obj % 3

        distancia += abs(linha_atual - linha_obj)
        distancia += abs(coluna_atual - coluna_obj)

    return distancia


def h3_combinada(estado):

    return (
        h1_fora_do_lugar(estado)
        + h2_manhattan(estado)
    )