import heapq

from estado import No, gerar_sucessores, OBJETIVO


def busca_uniforme(inicial):

    fila = []

    heapq.heappush(
        fila,
        (0, No(inicial))
    )

    visitados = set()

    nos_visitados = 0
    nos_gerados = 1

    while fila:

        _, atual = heapq.heappop(fila)

        nos_visitados += 1

        if atual.estado == OBJETIVO:
            return atual, nos_visitados, nos_gerados

        if atual.estado in visitados:
            continue

        visitados.add(atual.estado)

        for estado, mov in gerar_sucessores(atual.estado):

            if estado not in visitados:

                filho = No(
                    estado,
                    atual,
                    mov,
                    atual.custo + 1,
                    atual.profundidade + 1
                )

                heapq.heappush(
                    fila,
                    (filho.custo, filho)
                )

                nos_gerados += 1

    return None, nos_visitados, nos_gerados