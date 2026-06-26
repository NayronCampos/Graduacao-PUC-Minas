class No:
    def __init__(self, estado, pai=None, movimento=None,
                 custo=0, profundidade=0):

        self.estado = estado
        self.pai = pai
        self.movimento = movimento
        self.custo = custo
        self.profundidade = profundidade

    def __lt__(self, other):
        return self.custo < other.custo


MOVIMENTOS = {
    "CIMA": (-1, 0),
    "BAIXO": (1, 0),
    "ESQUERDA": (0, -1),
    "DIREITA": (0, 1)
}

OBJETIVO = (
    0, 1, 2,
    3, 4, 5,
    6, 7, 8
)


def gerar_sucessores(estado):

    sucessores = []

    pos = estado.index(0)

    linha = pos // 3
    coluna = pos % 3

    for nome, (dl, dc) in MOVIMENTOS.items():

        nl = linha + dl
        nc = coluna + dc

        if 0 <= nl < 3 and 0 <= nc < 3:

            nova_pos = nl * 3 + nc

            novo_estado = list(estado)

            novo_estado[pos], novo_estado[nova_pos] = \
                novo_estado[nova_pos], novo_estado[pos]

            sucessores.append(
                (tuple(novo_estado), nome)
            )

    return sucessores