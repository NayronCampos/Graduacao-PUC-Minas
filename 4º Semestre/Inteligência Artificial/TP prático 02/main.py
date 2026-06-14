import time

from bfs import bfs
from dfs import dfs
from uniforme import busca_uniforme
from gulosa import gulosa
from astar import a_star

from heuristicas import *
from utils import *

print("Digite os 9 números:")

entrada = list(
    map(int, input().split())
)

estado_inicial = tuple(entrada)

if not solucionavel(estado_inicial):

    print("Estado sem solução.")
    exit()

print("\n1 - BFS")
print("2 - DFS")
print("3 - Uniforme")
print("4 - Gulosa")
print("5 - A*")

alg = int(input("\nEscolha: "))

heuristica = None

if alg in [4, 5]:

    print("\n1 - Fora do Lugar")
    print("2 - Manhattan")
    print("3 - Combinada")

    h = int(input("Heurística: "))

    if h == 1:
        heuristica = h1_fora_do_lugar

    elif h == 2:
        heuristica = h2_manhattan

    else:
        heuristica = h3_combinada

inicio = time.time()

if alg == 1:
    resultado = bfs(estado_inicial)

elif alg == 2:
    resultado = dfs(estado_inicial)

elif alg == 3:
    resultado = busca_uniforme(estado_inicial)

elif alg == 4:
    resultado = gulosa(
        estado_inicial,
        heuristica
    )

else:
    resultado = a_star(
        estado_inicial,
        heuristica
    )

fim = time.time()

solucao, visitados, gerados = resultado

caminho = reconstruir_caminho(solucao)

print("\n===== SOLUÇÃO =====\n")

for no in caminho:

    print("Movimento:", no.movimento)

    imprimir_tabuleiro(no.estado)

print("Tempo:", fim - inicio)
print("Nós visitados:", visitados)
print("Nós gerados:", gerados)
print("Profundidade:", solucao.profundidade)