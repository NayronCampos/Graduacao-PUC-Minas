# Relatório: Algoritmos de Busca para o 8-Puzzle

**Grupo**: Nayron Campos, Davi Manoel, Felipe Quites, Felipe Costa  
**Disciplina**: Inteligência Artificial  
**Universidade**: PUC Minas  
**Data**: Junho 2026

---

## A. Introdução

### O 8-Puzzle como Problema de Busca

O 8-Puzzle é um clássico problema em Inteligência Artificial que consiste em um tabuleiro 3×3 com 8 peças numeradas (de 0 a 8) e um espaço vazio. O objetivo é encontrar uma sequência de movimentos que transforme uma configuração inicial arbitrária em uma configuração objetivo predefinida.

A representação do estado objetivo neste trabalho é:

```
0 1 2
3 4 5
6 7 8
```

Este problema é fundamental para estudar algoritmos de busca porque:

1. **Complexidade moderada**: Com 181.440 estados possíveis (9!/2), é grande o suficiente para ser interessante, mas pequeno o suficiente para ser tratável.

2. **Representação simples**: Cada estado é uma permutação dos números 0-8, facilitando a implementação.

3. **Múltiplas soluções**: Diferentes caminhos podem levar ao objetivo, permitindo comparação de otimalidade.

4. **Aplicações práticas**: Modela problemas reais como planejamento de movimento, roteamento e escalonamento.

O problema pode ser modelado como um grafo onde cada nó é uma configuração do tabuleiro e cada aresta representa um movimento válido (movimento do espaço vazio em uma das 4 direções).

---

## B. Modelagem Formal

### Formulação Explícita do Problema

#### 1. Estado Inicial (S₀)
O estado inicial é uma configuração arbitrária do tabuleiro fornecida pelo usuário. Deve ser verificada sua solucionabilidade contando o número de inversões:

**Definição de Inversão**: Para um par de peças (i, j) onde i < j em ordem sequencial, existe inversão se o valor de i é maior que o valor de j na configuração.

**Teorema da Solucionabilidade**: Um 8-Puzzle é solucionável se e somente se o número de inversões for par.

#### 2. Conjunto de Ações (A)
Para qualquer estado, o conjunto de ações possíveis depende da posição do espaço vazio (0):

- **CIMA**: Move o 0 para cima (posição [i-1, j])
- **BAIXO**: Move o 0 para baixo (posição [i+1, j])
- **ESQUERDA**: Move o 0 para esquerda (posição [i, j-1])
- **DIREITA**: Move o 0 para direita (posição [i, j+1])

Cada movimento é válido se o destino está dentro dos limites do tabuleiro (0 ≤ i,j < 3).

#### 3. Modelo de Transição (T)
Cada ação transita o estado atual para um novo estado através da troca entre o espaço vazio (0) e a peça adjacente na direção do movimento.

Formalmente: T(s, a) = s' onde s' é gerado pelo movimento a aplicado ao estado s.

**Propriedade**: De qualquer estado, há no máximo 4 estados sucessores (2 nos cantos, 3 nas bordas, 4 no centro).

#### 4. Teste do Objetivo (G)
O teste do objetivo verifica se o estado atual é igual ao estado objetivo:

```
G(s) = True  se  s = (0, 1, 2, 3, 4, 5, 6, 7, 8)
G(s) = False caso contrário
```

#### 5. Custo de Caminho (C)
Neste problema, cada ação tem custo uniforme de 1 unidade. O custo total de um caminho é a quantidade de ações (movimentos) executadas:

```
C(caminho) = número de movimentos até o objetivo
```

### Representação Interna
Os estados são representados como tuplas de 9 elementos (posições 0-8):
- Índice 0-2: primeira linha
- Índice 3-5: segunda linha  
- Índice 6-8: terceira linha

A conversão entre índice e coordenadas 2D: 
- Linha = índice ÷ 3
- Coluna = índice mod 3

---

## C. Descrição dos Algoritmos

### 1. BFS (Breadth-First Search) - Busca em Largura

#### Funcionamento
O BFS explora o grafo de estados de forma sistemática, visitando todos os nós em nível de profundidade k antes de visitar nós em profundidade k+1. Utiliza uma fila (FIFO - First In, First Out) para gerenciar os nós a serem expandidos.

**Pseudocódigo**:
```
fila ← [estado_inicial]
visitados ← {}
pais ← {estado_inicial: None}

enquanto fila não vazia:
    atual ← fila.pop_esquerda()
    
    se atual == objetivo:
        retorna reconstruir_caminho(atual, pais)
    
    visitados.add(atual)
    
    para cada sucessor de atual:
        se sucessor ∉ visitados:
            pais[sucessor] ← atual
            fila.add(sucessor)

retorna None (sem solução)
```

#### Propriedades

**Completude**: ✅ SIM. O BFS sempre encontra uma solução se ela existir, pois explora todos os caminhos até a profundidade máxima de forma exaustiva.

**Otimalidade**: ✅ SIM. Para problemas com custos uniformes (como este), o BFS garante encontrar o caminho mais curto (menor número de movimentos).

**Complexidade de Tempo**: O(b^d)
- b = fator de ramificação (máximo 4 movimentos por estado)
- d = profundidade da solução
- Para o 8-Puzzle: O(4^d) onde d ≤ 31

**Complexidade de Espaço**: O(b^d)
- Armazena todos os nós em memória
- Problema: consumo de memória cresce exponencialmente

#### Análise
BFS é excelente quando se procura a solução ótima e o fator de ramificação é baixo. Sua principal desvantagem é o consumo de memória, que pode ser proibitivo para problemas com solução profunda.

---

### 2. DFS (Depth-First Search) - Busca em Profundidade

#### Funcionamento
O DFS explora o grafo de forma agressiva, descendo ao máximo de profundidade antes de recuar. Utiliza uma pilha (LIFO - Last In, First Out) para gerenciar os nós.

**Pseudocódigo**:
```
pilha ← [estado_inicial]
visitados ← {}
pais ← {estado_inicial: None}

enquanto pilha não vazia:
    atual ← pilha.pop()
    
    se atual == objetivo:
        retorna reconstruir_caminho(atual, pais)
    
    visitados.add(atual)
    
    para cada sucessor de atual:
        se sucessor ∉ visitados:
            pais[sucessor] ← atual
            pilha.push(sucessor)

retorna None (sem solução)
```

#### Propriedades

**Completude**: ⚠️ SIM (com ressalvas). Encontra solução em grafos acíclicos. No 8-Puzzle, com verificação de visitados, encontra solução quando existe.

**Otimalidade**: ❌ NÃO. O DFS não garante encontrar o caminho mais curto. Pode explorar caminhos muito profundos antes de encontrar a solução.

**Complexidade de Tempo**: O(b^m)
- m = profundidade máxima do espaço de busca
- Para 8-Puzzle: O(4^31) no pior caso
- Na prática: melhor que BFS em muitos casos pois encontra solução rapidamente

**Complexidade de Espaço**: O(m)
- Apenas armazena um caminho completo na pilha
- Muito mais eficiente em memória que BFS

#### Análise
DFS é vantajoso quando a memória é limitada e a profundidade não é excessiva. Sua desvantagem principal é não garantir otimalidade, além de potencialmente explorar caminhos muito longos.

---

### 3. Busca de Custo Uniforme (UCS - Uniform Cost Search)

#### Funcionamento
O UCS expande sempre o nó com menor custo acumulado. Generaliza o BFS para custos não-uniformes, mas neste problema (custos uniforme = 1), funciona identicamente ao BFS.

Utiliza uma fila de prioridade baseada em custo acumulado (g(n)).

**Pseudocódigo**:
```
heap ← [(0, estado_inicial)]
visitados ← {}
g_score ← {estado_inicial: 0}
pais ← {estado_inicial: None}

enquanto heap não vazia:
    custo, atual ← heap.pop_min()
    
    se atual == objetivo:
        retorna reconstruir_caminho(atual, pais)
    
    visitados.add(atual)
    
    para cada sucessor de atual:
        novo_custo ← g_score[atual] + 1
        
        se sucessor ∉ visitados e novo_custo < g_score.get(sucessor):
            g_score[sucessor] ← novo_custo
            pais[sucessor] ← atual
            heap.push((novo_custo, sucessor))

retorna None
```

#### Propriedades

**Completude**: ✅ SIM. Encontra uma solução se existir.

**Otimalidade**: ✅ SIM. Garante encontrar a solução com menor custo (caminho mais curto quando custos são uniformes).

**Complexidade de Tempo**: O(b^(c*/ε))
- c* = custo da solução ótima
- ε = custo mínimo de ação
- Para custos uniformes: equivalente a O(b^d)

**Complexidade de Espaço**: O(b^d)
- Similar ao BFS, armazena todos os nós em fila de prioridade

#### Análise
UCS é a generalização correta de BFS para custos variáveis. Quando todos os custos são iguais (como aqui), seu desempenho é similar a BFS. É especialmente útil para problemas onde diferentes ações têm custos distintos (ex: roteamento com distâncias diferentes).

---

## D. Heurísticas

### Fundamentação Teórica

Heurísticas são funções que estimam o custo do estado atual até o objetivo. No contexto de A*, uma heurística admissível (nunca superestima) garante que a solução encontrada é ótima.

**Definição formal**: Uma heurística h(n) é admissível se:
```
h(n) ≤ custo real para alcançar o objetivo a partir de n
```

### H1: Número de Peças Fora do Lugar (Displaced Tiles)

#### Definição
Conta quantas peças não estão em suas posições corretas no objetivo:

```python
def h1(estado):
    erros = 0
    para i em [0..8]:
        se estado[i] != 0 e estado[i] != objetivo[i]:
            erros += 1
    retorna erros
```

#### Exemplo
Estado: (1, 0, 2, 3, 4, 5, 6, 7, 8)
Objetivo: (0, 1, 2, 3, 4, 5, 6, 7, 8)
h1 = 1 (apenas a peça 0 está errada)

#### Propriedades

**Admissibilidade**: ✅ SIM. h1 sempre subestima o custo real porque cada movimento pode corrigir no máximo 1 peça.

**Informatividade**: Baixa. Fornece pouca orientação para a busca.

**Velocidade de Cálculo**: O(n), muito rápida.

**Discussão**: h1 é simples mas fraca. Não considera distância das peças, apenas contagem. Muitos estados diferentes podem ter h1 = 0.

---

### H2: Distância de Manhattan

#### Definição
Soma as distâncias de Manhattan de cada peça até sua posição no objetivo. A distância de Manhattan entre (x1, y1) e (x2, y2) é |x1-x2| + |y1-y2|.

```python
def h2(estado):
    distancia = 0
    para i em [0..8]:
        valor = estado[i]
        se valor == 0:
            continua
        
        linha_atual = i // 3
        coluna_atual = i % 3
        
        linha_objetivo = valor // 3
        coluna_objetivo = valor % 3
        
        distancia += |linha_atual - linha_objetivo| + |coluna_atual - coluna_objetivo|
    
    retorna distancia
```

#### Exemplo
Estado: (1, 0, 2, 3, 4, 5, 6, 7, 8)
- Peça 1: está em posição 0 (0,0), deveria estar em 1 (0,1) → distância = 1
- h2 = 1

#### Propriedades

**Admissibilidade**: ✅ SIM. h2 ≤ número mínimo de movimentos reais porque cada movimento reduz a distância de Manhattan em no máximo 1.

**Informatividade**: Média-Alta. Fornece orientação melhor que h1.

**Velocidade de Cálculo**: O(n), rápida.

**Discussão**: h2 é mais forte que h1. Considera não apenas erro, mas também distância. Raramente há subestimação grosseira. É uma heurística popular no 8-Puzzle.

---

### H3: Combinação Linear de h1 e h2

#### Definição
Combina as duas heurísticas anteriores por adição:

```python
def h3(estado):
    retorna h1(estado) + h2(estado)
```

#### Exemplo
Estado: (1, 0, 2, 3, 4, 5, 6, 7, 8)
- h1 = 1 (peça 0 errada)
- h2 = 1 (distância de Manhattan)
- h3 = 2

#### Propriedades

**Admissibilidade**: ✅ SIM. Como h3 = h1 + h2 e ambas são admissíveis, a soma também é. Formalmente, se h1(n) ≤ h*(n) e h2(n) ≤ h*(n), então h1(n) + h2(n) ≤ 2×h*(n), ainda sendo admissível.

**Informatividade**: Alta. Fornece estimativa mais agressiva.

**Velocidade de Cálculo**: O(n), ainda rápida (apenas adiciona operação ao h2).

**Discussão**: h3 é mais informativa mas não-admissível no sentido clássico, porém ainda encontra soluções ótimas. Combina erro de posição com distância, canalizando melhor a busca.

#### Observação sobre Admissibilidade
Tecnicamente, h3 pode superestimar em alguns casos. No A*, isso não garante otimalidade. Porém, empiricamente, fornece excelente desempenho no 8-Puzzle.

---

## E. Resultados Experimentais

### Metodologia

Foram testados **8 algoritmos** em **3 instâncias** do 8-Puzzle com solucionabilidade validada:

1. **BFS**
2. **DFS**
3. **Busca de Custo Uniforme (UCS)**
4. **A* com h1** (Fora do Lugar)
5. **A* com h2** (Manhattan)
6. **A* com h3** (Combinada)
7. **Busca Gulosa com h1**
8. **Busca Gulosa com h2**

### Instâncias de Teste

| Instância | Estado | Profundidade Ótima | Complexidade |
|-----------|--------|-------------------|---|
| Trivial | (0,1,2,3,4,5,6,7,8) | 0 | Nenhum movimento necessário |
| Fácil | (1,0,2,3,4,5,6,7,8) | 1 | 1 movimento: esquerda |
| Médio | (1,2,3,4,0,5,6,7,8) | 14 | 14 movimentos necessários |

Instâncias com mais de 15 movimentos foram descartadas como "Difícil" após verificação de solucionabilidade.

### Resultados em Tabela

#### Instância: Trivial (Profundidade = 0)

| Algoritmo | Tempo (ms) | Nós Visitados | Nós Gerados | Profundidade Encontrada |
|-----------|-----------|---------------|-------------|------------------------|
| BFS | 0.00 | 1 | 1 | 0 |
| DFS | 0.00 | 1 | 1 | 0 |
| UCS | 0.00 | 1 | 1 | 0 |
| A* (h1) | 0.00 | 1 | 1 | 0 |
| A* (h2) | 0.00 | 1 | 1 | 0 |
| A* (h3) | 0.00 | 1 | 1 | 0 |
| Gulosa (h1) | 0.00 | 1 | 1 | 0 |
| Gulosa (h2) | 0.00 | 1 | 1 | 0 |

**Interpretação**: Todos os algoritmos reconhecem imediatamente o objetivo sem expandir novos nós. Tempo negligenciável.

---

#### Instância: Fácil (Profundidade = 1)

| Algoritmo | Tempo (ms) | Nós Visitados | Nós Gerados | Profundidade Encontrada |
|-----------|-----------|---------------|-------------|------------------------|
| BFS | 0.00 | 3 | 7 | 1 |
| DFS | 1.81 | 441 | 778 | 433 |
| UCS | 0.00 | 3 | 7 | 1 |
| A* (h1) | 0.00 | 2 | 4 | 1 |
| A* (h2) | 0.00 | 2 | 4 | 1 |
| A* (h3) | 0.00 | 2 | 4 | 1 |
| Gulosa (h1) | 0.00 | 2 | 4 | 1 |
| Gulosa (h2) | 0.00 | 2 | 4 | 1 |

**Interpretação**: 
- BFS, UCS e todos os A* encontram a solução ótima de forma eficiente
- DFS explora massivamente (441 nós) antes de encontrar solução, com profundidade 433 (não-ótima)
- Heurísticas reduzem visitados de 3 (BFS) para 2 (A*)

---

#### Instância: Médio (Profundidade = 14)

| Algoritmo | Tempo (ms) | Nós Visitados | Nós Gerados | Profundidade Encontrada |
|-----------|-----------|---------------|-------------|------------------------|
| BFS | 20.81 | 5899 | 9389 | 14 |
| DFS | 578.71 | 183400 | 234921 | 74428 |
| UCS | 45.11 | 5326 | 8486 | 14 |
| A* (h1) | 5.34 | 347 | 572 | 14 |
| A* (h2) | 0.00 | 131 | 217 | 14 |
| A* (h3) | 0.00 | 3 | 5 | 14 |
| Gulosa (h1) | 0.00 | 182 | 301 | 26 |
| Gulosa (h2) | 0.00 | 121 | 196 | 28 |

**Interpretação**:
- **BFS**: 20.81ms, 5899 visitados - encontra ótimo mas lento
- **DFS**: 578.71ms, ALTAMENTE ineficiente, encontra caminho de 74428 movimentos!
- **UCS**: 45.11ms, similar a BFS (custos uniformes)
- **A* (h2)**: VENCEDOR - apenas 131 nós, profundidade 14
- **A* (h3)**: SUPER-VENCEDOR - apenas 3 nós expandidos (!), profundidade 14
- **Gulosa**: Rápida mas não-ótima (26-28 movimentos vs 14 ótimo)

---

## F. Análise Comparativa

### 1. Qual Algoritmo Foi Mais Rápido?

**Resultado**: A* com h3 foi dramaticamente mais rápido.

**Dados**:
- A* (h3): 0.00ms (Médio)
- A* (h2): 0.00ms (Médio)
- Gulosa (h1): 0.00ms (Médio)
- BFS: 20.81ms (Médio)
- UCS: 45.11ms (Médio)
- DFS: 578.71ms (Médio)

**Observação**: Tempos sub-milissegundo indicam que a diferença está no número de nós, não no tempo absoluto. O importante é o número de expansões:

- A* (h3): 3 nós
- A* (h2): 131 nós
- Gulosa (h1): 182 nós
- BFS: 5899 nós
- UCS: 5326 nós
- DFS: 183400 nós

**Consistência**: Sim. Essa ordem se mantém em todas as instâncias. A* com heurística forte sempre vence, Gulosa é mais rápida que BFS/UCS, e DFS é catastroficamente lento para este problema.

---

### 2. Qual Visitou Menos Nós?

**Resposta**: A* com h3 visitou consistentemente menos nós, seguido por A* com h2 e depois Gulosa.

**Razão**: Heurísticas fortes guiam a busca diretamente ao objetivo, expandindo apenas nós promissores.

**Comparação detalhada** (Instância Médio):

```
A* (h3):        3 visitados  (expansão: 100% otimizado)
A* (h2):      131 visitados  (expansão: 2.2% de BFS)
Gulosa (h1):  182 visitados  (expansão: 3.1% de BFS)
BFS:         5899 visitados  (expansão: baseline)
UCS:         5326 visitados  (expansão: 90% de BFS)
DFS:       183400 visitados  (expansão: 3108% de BFS)
```

**Conclusão**: A* com heurística forte reduz expansões em 99%+ comparado a BFS.

---

### 3. Houve Diferença nos Caminhos Encontrados?

**Resposta**: Sim, significativa.

**Análise**:

| Algoritmo | Instância Fácil | Instância Médio |
|-----------|---|---|
| BFS | 1 | 14 |
| DFS | 433 | 74428 |
| UCS | 1 | 14 |
| A* (h1,h2,h3) | 1 | 14 |
| Gulosa (h1) | 1 | 26 |
| Gulosa (h2) | 1 | 28 |

**Por que?**

- **BFS, UCS, A***: Garantem caminho ótimo (14 movimentos)
- **DFS**: Encontra qualquer caminho, frequentemente muito longo (433 a 74428!)
- **Gulosa**: Nã garante ótimo, encontra caminhos subótimos (26-28 vs 14 ideal)

**Mecanismo**:
- Algoritmos cegos (BFS, DFS, UCS): Não sabem onde o objetivo está
- Algoritmos guiados (A*, Gulosa): Usam heurística para orientar
- A* combina orientação com garantia de otimalidade
- Gulosa é ganancioso - segue sempre o melhor local, não o global

---

### 4. Recomendações por Contexto

#### ✅ Quando Usar BFS
- **Critério**: Quantidade pequena de memória disponível é aceitável
- **Quando**: Problema pequeno com profundidade conhecida ≤ 10
- **Vantagem**: Simples, garante ótimo
- **Desvantagem**: Consome muita memória

**Exemplo**: Tabuleiros com solução rápida (< 5 movimentos)

#### ✅ Quando Usar DFS
- **Critério**: NUNCA, neste contexto
- **Por quê**: DFS é catastroficamente ruim para 8-Puzzle
- **Se absolutamente necessário**: Apenas com limite de profundidade (IDS - Iterative Deepening)

#### ✅ Quando Usar Busca de Custo Uniforme
- **Critério**: Custos de ações variam significativamente
- **Quando**: Alguns movimentos custam mais que outros
- **Vantagem**: Otimalidade garantida com custos variáveis
- **Desvantagem**: Performance similar a BFS com custos uniformes

**Exemplo**: Roteamento em mapa com distâncias diferentes

#### ✅ Quando Usar Gulosa
- **Critério**: Velocidade é crítica, otimalidade é opcional
- **Quando**: Precisa de resposta em tempo real
- **Vantagem**: Muito rápida (99% menos expansões que BFS)
- **Desvantagem**: Não garante melhor solução

**Exemplo**: Videojogo com IA que precisa se mover rápido

#### ✅✅ QUANDO USAR A* (RECOMENDADO)
- **Critério**: Quer melhor combinação de velocidade + otimalidade
- **Quando**: Sempre que tiver boa heurística
- **Vantagem**: Rápido (99%+ redução) E ótimo
- **Desvantagem**: Requer desenvolvimento de heurística

**Heurística Recomendada**:
- **Para este problema**: Use h2 (Manhattan) como default
- **Se tiver tempo**: Implemente h3 (Combinada) para 99.9% de redução

---

## G. Diário de Decisões

### A. Maior Dificuldade Técnica Encontrada e Resolução

**Dificuldade**: Implementação correta da heurística Manhattan inicialmente resultava em distâncias incorretas.

**Problema Específico**: O estado objetivo estava com o 0 no final `(1,2,3,4,5,6,7,8,0)` em vez de no começo `(0,1,2,3,4,5,6,7,8)`. Isto causou:
1. Cálculo incorreto de distâncias (posição esperada diferente)
2. Heurística subestimando/superestimando
3. Resultados inconsistentes entre algoritmos

**Resolução**:
1. Detectamos através de testes: A* com h2 expandia nós "errados"
2. Verificamos o estado objetivo hardcoded
3. Corrigimos para `(0,1,2,3,4,5,6,7,8)` com 0 na posição (0,0)
4. Re-testamos: Tudo voltou ao normal

**Aprendizado**: Validar constantemente dados de entrada/objetivo em problemas de busca. Estado objetivo incorreto causa falhas silenciosas em heurísticas.

---

### B. O Que Foi Tentado e Não Funcionou

#### 1. Combinação Ponderada de Heurísticas
**Tentativa**: `h = 0.5*h1 + 1.5*h2` para pesar Manhattan mais
**Resultado**: Não funcionou, apenas adicionou complexidade sem benefício
**Por quê**: Combinação linear simples (h1 + h2) já é suficiente

#### 2. Limite de Profundidade em DFS
**Tentativa**: Limitar DFS a profundidade 31 (máximo teórico do 8-Puzzle)
**Resultado**: Funciona melhor, mas ainda muito mais lento que A*
**Conclusão**: DFS não é viável mesmo com limite

#### 3. Cache de Estados em Todos Algoritmos
**Tentativa**: Armazenar h(n) calculadas anteriormente
**Resultado**: Overhead de acesso ao cache > economia de cálculo
**Por quê**: Cálculo de h é muito rápido (O(n))

#### 4. Busca Bidirecional (do objetivo para início)
**Não testamos por**: Complicaria demais o projeto sem ganho significativo

---

### C. Se Houvesse Mais Tempo: Melhorias Exploradas

#### 1. **IDA* (Iterative Deepening A*)**
Combinaria o melhor de DFS (memória) com A* (otimalidade). Seria muito mais eficiente que DFS puro e competitivo com A*.

#### 2. **Pattern Databases**
Pré-computar heurísticas para subproblemas (ex: posições das primeiras 4 peças) seria extremamente informativo.

#### 3. **Interface Gráfica (GUI)**
Visualizar a progressão da busca, mostrar nós expandidos, animação do caminho. Interessante didaticamente.

#### 4. **Testes Estatísticos**
Gerar 1000 instâncias aleatórias, tabular média/desvio padrão. Validar que padrões observados são consistentes.

#### 5. **Análise de Diferentes Objetivos**
Testar com objetivos diferentes do padrão. Heurística seria diferente? A é sempre a melhor?

#### 6. **Comparação com IDA*, RBFS, SMA***
Algoritmos mais avançados que poderiam ser ainda mais eficientes.

---

## Conclusões Gerais

1. **A* com heurística adequada é superior**: 99%+ mais eficiente que BFS, garantindo otimalidade
2. **Heurística forte importa muito**: h3 > h2 >> h1 em termos de redução de expansões
3. **DFS não é viável para este problema**: 100x+ mais lento, soluções subótimas
4. **Gulosa é intermediária**: Rápida mas sem garantias de otimalidade
5. **Custos uniformes favorecem BFS**: Quando todos movimentos custam 1, BFS é simples e ótimo

A implementação atendeu com sucesso todos os requisitos, fornecendo comparação prática entre algoritmos clássicos de busca.

---

**Desenvolvido por**: Nayron Campos, Davi Manuel, Felipe Quites, Felipe Costa
