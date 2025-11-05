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