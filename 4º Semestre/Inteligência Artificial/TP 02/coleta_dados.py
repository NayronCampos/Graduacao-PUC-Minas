import time
from bfs import bfs
from dfs import dfs
from uniforme import busca_uniforme
from gulosa import gulosa
from astar import a_star
from heuristicas import *
from utils import *

instancias = [
    ("Trivial", (0, 1, 2, 3, 4, 5, 6, 7, 8)),
    ("Fácil", (1, 0, 2, 3, 4, 5, 6, 7, 8)),
    ("Médio", (1, 2, 3, 4, 0, 5, 6, 7, 8)),
    ("Difícil", (4, 1, 2, 3, 5, 0, 6, 7, 8)),
]

print("=" * 100)
print("COLETA DE DADOS EXPERIMENTAIS - 8-PUZZLE")
print("=" * 100)

for nome, estado in instancias:
    if not solucionavel(estado):
        print(f"\n{nome}: NÃO SOLUCIONÁVEL")
        continue
    
    print(f"\n{nome}: {estado}")
    print("-" * 100)
    
    # BFS
    inicio = time.time()
    sol, vis, ger = bfs(estado)
    tempo = (time.time() - inicio) * 1000
    print(f"BFS        | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # DFS
    inicio = time.time()
    sol, vis, ger = dfs(estado)
    tempo = (time.time() - inicio) * 1000
    print(f"DFS        | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # Uniforme
    inicio = time.time()
    sol, vis, ger = busca_uniforme(estado)
    tempo = (time.time() - inicio) * 1000
    print(f"Uniforme   | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # A* h1
    inicio = time.time()
    sol, vis, ger = a_star(estado, h1_fora_do_lugar)
    tempo = (time.time() - inicio) * 1000
    print(f"A* (h1)    | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # A* h2
    inicio = time.time()
    sol, vis, ger = a_star(estado, h2_manhattan)
    tempo = (time.time() - inicio) * 1000
    print(f"A* (h2)    | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # A* h3
    inicio = time.time()
    sol, vis, ger = a_star(estado, h3_combinada)
    tempo = (time.time() - inicio) * 1000
    print(f"A* (h3)    | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # Gulosa h1
    inicio = time.time()
    sol, vis, ger = gulosa(estado, h1_fora_do_lugar)
    tempo = (time.time() - inicio) * 1000
    print(f"Gulosa(h1) | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")
    
    # Gulosa h2
    inicio = time.time()
    sol, vis, ger = gulosa(estado, h2_manhattan)
    tempo = (time.time() - inicio) * 1000
    print(f"Gulosa(h2) | {tempo:8.2f}ms | Visitados: {vis:6d} | Gerados: {ger:6d} | Profundidade: {sol.profundidade}")

print("\n" + "=" * 100)
print("TESTES CONCLUÍDOS")
print("=" * 100)
