def contar_inversoes(estado):

    numeros = [x for x in estado if x != 0]

    inv = 0

    for i in range(len(numeros)):
        for j in range(i + 1, len(numeros)):
            if numeros[i] > numeros[j]:
                inv += 1

    return inv


def solucionavel(estado):

    return contar_inversoes(estado) % 2 == 0


def reconstruir_caminho(no):

    caminho = []

    while no:

        caminho.append(no)

        no = no.pai

    caminho.reverse()

    return caminho


def imprimir_tabuleiro(estado):

    for i in range(0, 9, 3):

        print(estado[i:i+3])

    print()