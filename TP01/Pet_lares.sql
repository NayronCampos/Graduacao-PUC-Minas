CREATE SCHEMA pet_lares;
USE pet_lares;
    
    CREATE TABLE animal(
	id_animal INT auto_increment,
	nome VARCHAR(120) not null,
	raca VARCHAR(100) not null,
	idade TINYINT,
	tipo VARCHAR(10) CHECK (tipo IN ('cachorro', 'gato')),
	PRIMARY KEY (id_animal)
	);
    
    CREATE TABLE veterinario(
    cpf CHAR(11) not null,
    nome VARCHAR(120) not null,
    area_atuacao VARCHAR(80) not null,
    PRIMARY KEY (cpf)
    );	
    
    CREATE TABLE instituicao(
    identificador INT auto_increment not null,
    nome VARCHAR(120) not null,
    cidade VARCHAR(120) not null,
    PRIMARY KEY (identificador)
    );
    
    CREATE TABLE voluntario(
    matricula INT not null,
    fk_instituicao int not null,
    nome VARCHAR(120) not null,
    telefone CHAR(15) not null,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    PRIMARY KEY (matricula),
    FOREIGN KEY (fk_instituicao) REFERENCES instituicao (identificador)
    );
    
    #drop table if exists voluntario;
    CREATE TABLE capturador(
    matricula INT auto_increment,
    nome VARCHAR(120) not null,
    data_nascimento DATE,
	sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
	PRIMARY KEY (matricula)
    );
    
    CREATE TABLE receptor(
    Cpf CHAR(11) not null,
    nome VARCHAR(120) not null,
    endereco VARCHAR(230) not null,
    data_nascimento DATE,
    PRIMARY KEY (cpf)
    );
    
    CREATE TABLE capturar(
    fk_animal INT not null,
    fk_capturador INT not null,
    data_captura DATE,
    local_captura VARCHAR(230) not null,
    PRIMARY KEY (fk_animal, fk_capturador),
    FOREIGN KEY (fk_animal) REFERENCES animal (id_animal),
    FOREIGN KEY (fk_capturador) REFERENCES capturador (matricula)
    );
    
    CREATE TABLE adotar(
    fk_animal INT not null,
    fk_receptor CHAR(11) not null,
    fk_veterinario_responsavel CHAR(11) not null,
    data_adocao DATE not null,
    PRIMARY KEY (fk_animal, fk_receptor, fk_veterinario_responsavel),
    FOREIGN KEY (fk_animal) REFERENCES animal (id_animal), 
    FOREIGN KEY (fk_receptor) REFERENCES receptor (cpf),
    FOREIGN KEY (fk_veterinario_responsavel) REFERENCES veterinario (cpf)
    );
    
    #drop table if exists adotar;
    
    CREATE TABLE atendido(
    fk_animal INT not null,
    fk_veterinario CHAR(11) not null,
    data_atendimento DATE not null,
    PRIMARY KEY (fk_animal, fk_veterinario),
    FOREIGN KEY (fk_animal) REFERENCES animal (id_animal),
    FOREIGN KEY (fk_veterinario) REFERENCES veterinario (cpf)
    );
    
USE pet_lares;

-- 📋 Tabela: instituicao
INSERT INTO instituicao (nome, cidade) VALUES
('Lar dos Animais', 'Belo Horizonte'),
('Amigos de Quatro Patas', 'São Paulo'),
('Cuidar e Amar', 'Curitiba');

-- 📋 Tabela: veterinario
INSERT INTO veterinario (cpf, nome, area_atuacao) VALUES
('11111111111', 'Dra. Ana Paula', 'Clínica Geral'),
('22222222222', 'Dr. Carlos Souza', 'Cirurgia'),
('33333333333', 'Dra. Fernanda Lima', 'Dermatologia');

-- 📋 Tabela: voluntario
INSERT INTO voluntario (matricula, fk_instituicao, nome, telefone, sexo) VALUES
(101, 1, 'Mariana Silva', '31999999999', 'F'),
(102, 2, 'João Pereira', '11988888888', 'M'),
(103, 3, 'Beatriz Costa', '41977777777', 'F');

-- 📋 Tabela: capturador
INSERT INTO capturador (nome, data_nascimento, sexo) VALUES
('Lucas Oliveira', '1995-04-10', 'M'),
('Sofia Almeida', '1998-07-22', 'F'),
('Ricardo Santos', '1990-03-15', 'M');

-- 📋 Tabela: receptor
INSERT INTO receptor (cpf, nome, endereco, data_nascimento) VALUES
('44444444444', 'Paula Andrade', 'Rua das Flores, 120 - BH', '1992-09-15'),
('55555555555', 'Gabriel Lima', 'Av. Paulista, 900 - SP', '1988-11-30'),
('66666666666', 'Carla Menezes', 'Rua XV de Novembro, 250 - Curitiba', '1995-06-05');

-- 📋 Tabela: animal
INSERT INTO animal (nome, raca, idade, tipo) VALUES
('Rex', 'Labrador', 4, 'cachorro'),
('Mia', 'Siamês', 2, 'gato'),
('Thor', 'Vira-lata', 3, 'cachorro'),
('Luna', 'Persa', 1, 'gato'),
('Bob', 'Golden Retriever', 5, 'cachorro');

-- 📋 Tabela: capturar
-- vincula animais a capturadores
INSERT INTO capturar (fk_animal, fk_capturador, data_captura, local_captura) VALUES
(1, 1, '2025-01-10', 'Praça da Liberdade - BH'),
(2, 2, '2025-01-15', 'Centro - SP'),
(3, 3, '2025-02-01', 'Parque Barigui - Curitiba'),
(4, 2, '2025-03-05', 'Zona Sul - SP'),
(5, 1, '2025-04-10', 'Savassi - BH');

-- 📋 Tabela: atendido
-- indica atendimento veterinário aos animais
INSERT INTO atendido (fk_animal, fk_veterinario, data_atendimento) VALUES
(1, '11111111111', '2025-01-12'),
(2, '22222222222', '2025-01-16'),
(3, '11111111111', '2025-02-03'),
(4, '33333333333', '2025-03-06'),
(5, '22222222222', '2025-04-12');

-- 📋 Tabela: adotar
-- registra as adoções (animal + receptor + veterinário)
INSERT INTO adotar (fk_animal, fk_receptor, fk_veterinario_responsavel, data_adocao) VALUES
(1, '44444444444', '11111111111', '2025-02-01'),
(2, '55555555555', '22222222222', '2025-02-10'),
(4, '66666666666', '33333333333', '2025-03-15');

SHOW TABLES;
SELECT * FROM adotar;
SELECT 
    a.nome AS Nome_Animal,
    a.tipo AS Tipo,
    r.nome AS Nome_Receptor,
    v.nome AS Veterinario_Responsavel,
    ad.data_adocao AS Data_Adocao
FROM adotar ad
JOIN animal a ON a.id_animal = ad.fk_animal
JOIN receptor r ON r.cpf = ad.fk_receptor
JOIN veterinario v ON v.cpf = ad.fk_veterinario_responsavel
ORDER BY ad.data_adocao;
