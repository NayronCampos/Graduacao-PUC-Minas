CREATE SCHEMA pet_lares;
USE pet_lares;

CREATE TABLE capturador(
    matricula INT AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    data_nascimento DATE,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    PRIMARY KEY (matricula)
);

CREATE TABLE animal(
    id_animal INT AUTO_INCREMENT,
    fk_capturador INT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    raca VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    tipo VARCHAR(10) CHECK (tipo IN ('cachorro', 'gato')),
    local_captura VARCHAR(120) NOT NULL,
    data_captura DATE NOT NULL,
    PRIMARY KEY (id_animal),

    FOREIGN KEY (fk_capturador)
        REFERENCES capturador(matricula)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE veterinario(
    cpf CHAR(11) NOT NULL,
    nome VARCHAR(120) NOT NULL,
    area_atuacao VARCHAR(80) NOT NULL,
    PRIMARY KEY (cpf)
);	

CREATE TABLE instituicao(
    identificador INT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    cidade VARCHAR(120) NOT NULL,
    PRIMARY KEY (identificador)
);

CREATE TABLE voluntario(
    matricula INT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    telefone CHAR(15) NOT NULL,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    data_nascimento DATE NOT NULL,
    PRIMARY KEY (matricula)
);

CREATE TABLE receptor(
    cpf CHAR(11) NOT NULL,
    nome VARCHAR(120) NOT NULL,
    endereco VARCHAR(230) NOT NULL,
    data_nascimento DATE,
    PRIMARY KEY (cpf)
);

CREATE TABLE adotar(
    fk_animal INT NOT NULL,
    fk_receptor CHAR(11) NOT NULL,
    fk_veterinario_responsavel CHAR(11) NOT NULL,
    data_adocao DATE NOT NULL,
    PRIMARY KEY (fk_animal, fk_receptor, fk_veterinario_responsavel),

    FOREIGN KEY (fk_animal)
        REFERENCES animal(id_animal)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (fk_receptor)
        REFERENCES receptor(cpf)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (fk_veterinario_responsavel)
        REFERENCES veterinario(cpf)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE atendido(
    fk_animal INT NOT NULL,
    fk_veterinario CHAR(11) NOT NULL,
    data_atendimento DATE NOT NULL,
    PRIMARY KEY (fk_animal, fk_veterinario),

    FOREIGN KEY (fk_animal)
        REFERENCES animal(id_animal)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (fk_veterinario)
        REFERENCES veterinario(cpf)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE ajuda(
    fk_instituicao INT NOT NULL,
    fk_voluntario INT NOT NULL,
    horas INT NOT NULL,
    PRIMARY KEY (fk_instituicao, fk_voluntario),

    FOREIGN KEY (fk_instituicao)
        REFERENCES instituicao(identificador)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (fk_voluntario)
        REFERENCES voluntario(matricula)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

ALTER TABLE instituicao
ADD UNIQUE (nome, cidade);

ALTER TABLE animal
ADD UNIQUE (nome, fk_capturador);

-- Comandos para recriar as tabelas caso preciso:

    #drop table if exists adotar;
	#drop table if exists atendido;
	#drop table if exists ajuda;
    #drop table if exists receptor;
    #drop table if exists instituicao;
	#drop table if exists voluntario;
    #drop table if exists animal;
    #drop table if exists veterinario;
    #drop table if exists capturador;
       
-- Comandos para popular o banco, mandei separado tb no outro arquivo, mas já pus aqui para facilitar caso queira:
/*
USE pet_lares;


-- Popular capturador
INSERT INTO capturador (nome, data_nascimento, sexo) VALUES
('Carlos Henrique Duarte', '1984-02-18', 'M'),
('Juliana Pereira da Costa', '1991-09-05', 'F'),
('Rogério Martins Alves', '1987-11-23', 'M');

-- Popular animal
INSERT INTO animal (fk_capturador, nome, raca, idade, tipo, local_captura, data_captura) VALUES
(1, 'Bolt', 'Vira-Lata', 14, 'cachorro', 'Centro', '2024-04-12'),
(1, 'Nina', 'Siamês', 10, 'gato', 'Savassi', '2024-05-27'),
(2, 'Thor', 'Pastor Alemão', 26, 'cachorro', 'Barreiro', '2024-06-03'),
(3, 'Mia', 'Persa', 8, 'gato', 'Venda Nova', '2024-07-14'),
(2, 'Lola', 'Vira-Lata', 12, 'cachorro', 'Pampulha', '2024-08-02');

-- Popular veterinário
INSERT INTO veterinario (cpf, nome, area_atuacao) VALUES
('01749235681', 'Dr. Pedro Augusto Silva', 'Clínica Geral'),
('42891567320', 'Dra. Ana Carolina Moura', 'Felinos'),
('89234715609', 'Dr. Mateus Figueiredo Rocha', 'Cirurgia Veterinária');

-- Popular instituição
INSERT INTO instituicao (nome, cidade) VALUES
('Pet Lares Centro', 'Belo Horizonte'),
('Pet Lares Barreiro', 'Belo Horizonte'),
('Pet Lares Contagem', 'Contagem');

-- Popular voluntário
INSERT INTO voluntario (matricula, nome, telefone, sexo, data_nascimento) VALUES
(101, 'Mariana Lopes Prado', '31998546612', 'F', '1999-03-21'),
(102, 'Lucas Carvalho Ribeiro', '31987442219', 'M', '2000-10-13'),
(103, 'Fernanda Oliveira Lima', '31997735544', 'F', '1998-07-29');

-- Popular receptor
INSERT INTO receptor (cpf, nome, endereco, data_nascimento) VALUES
('31785592040', 'Paulo Roberto Santos', 'Rua das Palmeiras, 120 - BH', '1994-05-11'),
('50231986472', 'Renata Aparecida Martins', 'Rua das Magnólias, 455 - Contagem', '1986-09-28'),
('71245839012', 'João Pedro Rocha', 'Rua dos Limoeiros, 782 - BH', '2000-01-19');

-- Popular adoção
INSERT INTO adotar (fk_animal, fk_receptor, fk_veterinario_responsavel, data_adocao) VALUES
(1, '31785592040', '01749235681', '2024-06-02'),
(4, '50231986472', '42891567320', '2024-09-05'),
(5, '71245839012', '89234715609', '2024-10-15');

-- Popular atendimento
INSERT INTO atendido (fk_animal, fk_veterinario, data_atendimento) VALUES
(1, '01749235681', '2024-04-15'),
(2, '42891567320', '2024-05-30'),
(3, '89234715609', '2024-06-10'),
(4, '42891567320', '2024-07-20'),
(5, '01749235681', '2024-08-07');

-- Popular ajuda
INSERT INTO ajuda (fk_instituicao, fk_voluntario, horas) VALUES
(1, 101, 42),
(1, 102, 18),
(2, 101, 16),
(2, 103, 29),
(3, 102, 12);
*/

-- Comando select para verificar as tabelas e relações:
/*
SELECT 
    a.id_animal,
    a.nome AS nome_animal,
    a.tipo,
    a.raca,
    a.data_captura,

    c.nome AS capturador,
    
    r.nome AS receptor,
    ad.data_adocao,

    v.nome AS veterinario_responsavel,

    atd.data_atendimento,

    inst.nome AS instituicao,
    vol.nome AS voluntario,
    aj.horas

FROM animal a

-- capturador do animal
JOIN capturador c 
    ON a.fk_capturador = c.matricula

-- adoções: LEFT JOIN porque o animal pode ainda não ter sido adotado
LEFT JOIN adotar ad 
    ON ad.fk_animal = a.id_animal

LEFT JOIN receptor r 
    ON r.cpf = ad.fk_receptor

LEFT JOIN veterinario v
    ON v.cpf = ad.fk_veterinario_responsavel

-- atendimentos veterinários também podem não existir
LEFT JOIN atendido atd
    ON atd.fk_animal = a.id_animal

-- ajuda de voluntários NÃO tem ligação com o animal
-- então colocamos só para mostrar dados, sem afetar o número de animais
LEFT JOIN ajuda aj 
    ON TRUE    -- não reduz o resultado

LEFT JOIN instituicao inst
    ON inst.identificador = aj.fk_instituicao

LEFT JOIN voluntario vol
    ON vol.matricula = aj.fk_voluntario

ORDER BY a.id_animal;
*/

