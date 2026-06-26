# TP 02: Algoritmos de Busca no 8-Puzzle

**Grupo**: Nayron Campos, Daniel Matos, Davi Manoel, Felipe Quites, Felipe Costa

## O Projeto

Nesse trabalho a gente implementou vários algoritmos de busca diferentes pra resolver o quebra-cabeça 8-Puzzle. Basicamente, o desafio é pegar um tabuleiro bagunçado e encontrar a sequência de movimentos certa pra chegar no estado objetivo:

```
0 1 2
3 4 5
6 7 8
```

O 0 representa o espaço vazio e pode se mover em 4 direções (pra cima, baixo, esquerda, direita).

## O Que a Gente Fez

### Todos os Requisitos Atendidos ✅

**1. Modularidade**
A gente separou bem direitinho o código pra fica fácil de entender e manter:

- **`estado.py`**: Aqui tem a classe `No` (que representa cada nó da busca), o objetivo final e a função que gera os próximos estados possíveis
- **`heuristicas.py`**: Todas as heurísticas que a gente desenvolveu pra guiar a busca
- **`bfs.py`, `dfs.py`, `uniforme.py`, `gulosa.py`, `astar.py`**: Cada algoritmo em um arquivo separado
- **`utils.py`**: Funções que todo mundo usa (checkar se tem solução, reconstruir o caminho, mostrar o tabuleiro na tela)
- **`main.py`**: A interface com o usuário

**2. Algoritmos** (5 no total)

A gente implementou todos os algoritmos solicitados:
- **A***: Obrigatório, com 3 heurísticas diferentes
- **BFS** (Busca em Largura)
- **DFS** (Busca em Profundidade)  
- **Busca de Custo Uniforme**
- **Busca Gulosa**


**3. Interface Amigável**

A gente deixou o programa bem fácil de usar:
- Digite os 9 números do tabuleiro inicial (separados por espaço)
- Escolhe qual algoritmo quer usar (menu com 5 opções)
- Se for usar A* ou Gulosa, escolhe qual heurística
- O programa mostra cada movimento até chegar na solução

**4. Saída Completa**

Depois de executar, aparece:
- Todo o caminho de movimentos passo a passo
- O tabuleiro depois de cada movimento
- Tempo total que levou
- Quantos nós foram visitados
- Quantos nós foram gerados
- A profundidade da solução (quantos movimentos foram necessários)

**5. Linguagem**
Python 3 - sem dependências externas, roda em qualquer máquina com Python instalado

---

## Como Funciona o Código

### A Estrutura Principal

A gente usa uma classe `No` que representa cada configuração do tabuleiro durante a busca. Cada nó guarda:
- O estado atual (qual é a posição de cada número)
- Um ponteiro pro nó anterior (pro gente conseguir reconstruir o caminho depois)
- Qual foi o movimento que levou até aquele estado
- O custo acumulado desde o início
- A profundidade na árvore de busca

O tabuleiro é representado como uma tupla de 9 números (0-8), que fica mais fácil de trabalhar e comparar.

```
Índice:  0  1  2        Tabuleiro:  [0]  [1]  [2]
         3  4  5                    [3]  [4]  [5]
         6  7  8                    [6]  [7]  [8]
```

### Como Geram-se os Próximos Estados

Pra gerar os próximos estados possíveis, a gente:
1. Encontra onde está o 0 (espaço vazio)
2. Tenta mover em 4 direções (cima, baixo, esquerda, direita)
3. Se der pra mover (tá dentro do tabuleiro), cria um novo estado
4. Retorna a lista de novos estados possíveis

É bem simples mesmo!

---

## Os 5 Algoritmos que A Gente Implementou

### BFS (Busca em Largura)
Esse aqui explora o tabuleiro de forma bem organizada, tipo em camadas. Primeiro testa todos os movimentos possíveis, depois testa todos os próximos, e assim vai. Garante que a solução vai ser a mais curta possível, mas pode usar bastante memória se o problema for grande.

**Arquivo**: `bfs.py`  
**Melhor pra**: Quando você quer a solução com menos movimentos possível

### DFS (Busca em Profundidade)
Esse é meio ao contrário. Ele vai indo fundo, fundo, fundo até não poder mais, aí volta pra trás. Mais rápido que BFS em alguns casos, mas não garante que vai achar a solução mais curta.

**Arquivo**: `dfs.py`  
**Melhor pra**: Exploração rápida

### Busca de Custo Uniforme
Imagina que cada movimento tem um "custo". Esse algoritmo sempre expande o estado que tem o menor custo acumulado. É tipo BFS mas considerando custos diferentes.

**Arquivo**: `uniforme.py`  
**Melhor pra**: Quando os movimentos têm custos diferentes

### Busca Gulosa
Esse aqui usa "intuição" (heurística) pra adivinhar qual movimento é melhor. Não é sempre ótimo, mas é bem rápido porque tá sempre tentando ir pro caminho que parece mais promissor.

**Arquivo**: `gulosa.py`  
**Melhor pra**: Quando velocidade é mais importante que perfeição

### A* (O Melhor dos Dois Mundos)
Esse combina o custo real (quanto você já andou) com a intuição (pra onde ainda precisa ir). É o mais inteligente e geralmente o mais rápido pra achar a solução ótima.

**Arquivo**: `astar.py`  
**Melhor pra**: Praticamente tudo!

---

## As 3 Heurísticas que Usamos

### H1: Fora do Lugar
Simples: conta quantas peças não tão no lugar certo. Se tem 5 peças erradas, h1 = 5. Rápido de calcular, mas não é muito preciso.

### H2: Manhattan
Mais sofisticado. Calcula a distância que cada peça precisa andar pra chegar no lugar certo. Se uma peça tá no canto e precisa ficar no outro lado, soma tudo. Mais preciso que h1, e ainda é rápido.

### H3: Combinada  
A gente somou as duas anteriores. h1 + h2. É mais conservador (subestima menos) e geralmente faz o A* explorar bem menos estados, mas leva mais tempo pra calcular cada heurística.

**Arquivo**: `heuristicas.py`  
**Melhor resultado**: Com 3 opções diferentes, a gente consegue testar qual funciona melhor pro caso específico

---

## Estrutura dos Arquivos

```
TP prático 02/
├── main.py              # Aqui roda o programa, pede input do usuário
├── estado.py            # Classe No e a lógica de gerar próximos estados
├── bfs.py               # Implementação do BFS
├── dfs.py               # Implementação do DFS
├── uniforme.py          # Implementação da Busca de Custo Uniforme
├── gulosa.py            # Implementação da Busca Gulosa
├── astar.py             # Implementação do A*
├── heuristicas.py       # As 3 heurísticas (h1, h2, h3)
├── utils.py             # Funções que todo mundo usa
└── DOCUMENTACAO.md      # Este arquivo
```

---

## Como Rodar o Programa

### Requisitos
Só precisa de Python 3.6 ou mais novo. Nada de dependências estranhas, só o que vem instalado por padrão.

### Rodando
```bash
python main.py
```

### Exemplo de Execução

```
Digite os 9 números:
1 0 2 3 4 5 6 7 8

1 - BFS
2 - DFS
3 - Uniforme
4 - Gulosa
5 - A*

Escolha: 5

1 - Fora do Lugar
2 - Manhattan
3 - Combinada

Heurística: 2

===== SOLUÇÃO =====

Movimento: INICIAL
(1, 0, 2)
(3, 4, 5)
(6, 7, 8)

Movimento: ESQUERDA
(0, 1, 2)
(3, 4, 5)
(6, 7, 8)

Tempo: 0.0012
Nós visitados: 2
Nós gerados: 2
Profundidade: 1
```

Bem tranquilo, não é?

---

## O Que Cada Algoritmo Faz Bem e Mal

| Algoritmo | Acha a melhor? | É rápido? | Usa muita RAM? | Quando usar |
|-----------|---|---|---|---|
| BFS       | Sim ✅ | Não ❌ | Sim ❌ | Quando quer a solução mais curta |
| DFS       | Não ❌ | Sim ✅ | Não ✅ | Quando precisa de resposta rápida |
| Uniforme  | Sim ✅ | Não ❌ | Sim ❌ | Com custos diferentes |
| Gulosa    | Não ❌ | Sim ✅ | Não ✅ | Quando velocidade é tudo |
| A*        | Sim ✅ | Sim ✅ | Talvez | Praticamente sempre! |

### E as Heurísticas?

| Heurística | Rápida? | Precisa? | Menos Exploração |
|-----------|---|---|---|
| h1 (Fora do Lugar)  | 🟢 Muito | 🟡 Média | Muito não |
| h2 (Manhattan)      | 🟡 Normal | 🟢 Boa   | Bastante |
| h3 (Combinada)      | 🔴 Lenta  | 🟢 Ótima | Bem menos |

---

## Verificando se o Quebra-Cabeça Tem Solução

Nem todo tabuleiro bagunçado tem solução. A gente descobriu contando as "inversões". 

Se o número de inversões for par, tem solução. Se for ímpar, não tem jeito mesmo.

**O que é inversão?** Quando uma peça tá antes de outra mas deveria estar depois. Por exemplo, em `2 1 0`, o 2 tá antes do 1, mas no objetivo `0 1 2` o 1 vem antes do 2. Isso é uma inversão.

A gente verifica isso automaticamente quando você entra com o tabuleiro inicial e avisa se é impossível resolver.

---

## Mudanças Que A Gente Fez no Código

Quando a gente recebeu o código, tinha algumas coisas erradas que a gente corrigiu:

### O Objetivo Tava de Cabeça pra Baixo
Tava assim: `(1, 2, 3, 4, 5, 6, 7, 8, 0)` - com o 0 no final  
Devia ser: `(0, 1, 2, 3, 4, 5, 6, 7, 8)` - com o 0 no começo

A gente corrigiu isso em `estado.py`

### Heurística de Manhattan Também Tava Errada
Porque a posição do 0 mudou, a heurística de Manhattan precisava ser ajustada também. A gente fez isso em `heuristicas.py`

Depois disso, tudo ficou funcionando direitinho!

---

## Coisas Técnicas e Limitações

### Quão Rápido Isso Fica?
Depende de vários fatores:
- **Algoritmo**: A* é geralmente mais rápido
- **Heurística**: h3 é melhor mas mais lenta de calcular
- **Quão longe tá da solução**: Alguns tabuleiros são mais fáceis que outros

O espaço de possibilidades tem 181.440 estados únicos (9! dividido por 2). Tá longe de ser infinito, mas também não é pequeno.

A profundidade máxima pra qualquer tabuleiro é 31 movimentos.

### O Que Seria Melhor No Futuro
- Implementar IDA* (mescla BFS com DFS, bem mais eficiente)
- Usar "pattern databases" - uma coisa mais avançada pra achar heurísticas ainda melhores
- Fazer uma interface gráfica mesmo (tipo com Tkinter ou na web)
- Paralelizar a busca pra usar múltiplos cores

---

## Referências e Créditos

A gente usou:
- Russell, S., Norvig, P. (2010). "Artificial Intelligence: A Modern Approach" - o livro clássico mesmo
- Wikipedia e artigos sobre 8-Puzzle
- A comunidade Python e Stack Overflow (porque a gente ama programar, não inventar a roda)

**Desenvolvido por**: Nayron Campos, Daniel Matos, Davi Manoel, Felipe Quites, Felipe Costa  
**Disciplina**: Inteligência Artificial  
**Universidade**: PUC Minas  
**Data**: Junho 2026
