from collections import deque

from estado import No, gerar_sucessores, OBJETIVO


def bfs(inicial):

    fila = deque()

    visitados = set()

    fila.append(
        No(inicial)
    )

    nos_visitados = 0
    nos_gerados = 1

    while fila:

        atual = fila.popleft()

        nos_visitados += 1

        if atual.estado == OBJETIVO:

            return atual, nos_visitados, nos_gerados

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

                fila.append(filho)

                nos_gerados += 1

    return None, nos_visitados, nos_gerados