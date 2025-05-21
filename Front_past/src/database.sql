-- Tabela de Usuários
CREATE TABLE Usuario (
    id INT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nivel_de_escolaridade VARCHAR(100)
);

-- Tabela de Concursos
CREATE TABLE Concurso (
    id INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    orgao VARCHAR(255),
    cargo VARCHAR(255),
    requisitos TEXT,
    data_de_inscricao DATE,
    data_da_prova DATE,
    status BOOLEAN
);

-- Tabela de Editais (relacionada a Concurso)
CREATE TABLE Edital (
    id INT PRIMARY KEY,
    concurso_id INT NOT NULL,
    arquivo VARCHAR(255) NOT NULL,
    data_de_publicacao DATE,
    CONSTRAINT fk_edital_concurso FOREIGN KEY (concurso_id)
        REFERENCES Concurso(id)
);

-- Tabela de Questões (relacionada a Concurso)
CREATE TABLE Questao (
    id INT PRIMARY KEY,
    concurso_id INT NOT NULL,
    enunciado TEXT NOT NULL,
    alternativas TEXT NOT NULL,
    resposta_certa TEXT NOT NULL,
    CONSTRAINT fk_questao_concurso FOREIGN KEY (concurso_id)
        REFERENCES Concurso(id)
);

-- Tabela de Livros
CREATE TABLE Livro (
    id INT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    versao VARCHAR(50),
    materia VARCHAR(100)
);

-- Tabela de Simulados
CREATE TABLE Simulado (
    id INT PRIMARY KEY,
    materia VARCHAR(100) NOT NULL,
    numero_de_questoes INT NOT NULL
);

-- Relação entre Usuário e Simulado (Usuário escolhe Simulado)
CREATE TABLE UsuarioSimulado (
    usuario_id INT,
    simulado_id INT,
    PRIMARY KEY (usuario_id, simulado_id),
    CONSTRAINT fk_usuariosimulado_usuario FOREIGN KEY (usuario_id)
        REFERENCES Usuario(id),
    CONSTRAINT fk_usuariosimulado_simulado FOREIGN KEY (simulado_id)
        REFERENCES Simulado(id)
);

-- Relação entre Simulado e Questão (Simulado possui Questões)
CREATE TABLE SimuladoQuestao (
    simulado_id INT,
    questao_id INT,
    PRIMARY KEY (simulado_id, questao_id),
    CONSTRAINT fk_simuladoquestao_simulado FOREIGN KEY (simulado_id)
        REFERENCES Simulado(id),
    CONSTRAINT fk_simuladoquestao_questao FOREIGN KEY (questao_id)
        REFERENCES Questao(id)
);

-- Tabela de Cronogramas (Planejamento de estudo do usuário)
CREATE TABLE Cronograma (
    id INT PRIMARY KEY,
    usuario_id INT NOT NULL,
    planejamento_de_estudo TEXT,
    livro_id INT,
    questao_id INT,
    CONSTRAINT fk_cronograma_usuario FOREIGN KEY (usuario_id)
        REFERENCES Usuario(id),
    CONSTRAINT fk_cronograma_livro FOREIGN KEY (livro_id)
        REFERENCES Livro(id),
    CONSTRAINT fk_cronograma_questao FOREIGN KEY (questao_id)
        REFERENCES Questao(id)
);
