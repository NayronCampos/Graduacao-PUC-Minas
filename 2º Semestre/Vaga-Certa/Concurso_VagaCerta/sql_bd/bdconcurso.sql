CREATE DATABASE concurso; 

USE concurso;

CREATE TABLE IF NOT EXISTS edital(
	id_edital int unsigned auto_increment primary key,
    arquivo varchar(100) not null,
    data_publi date not null 
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS concurso (
	id_concurso int unsigned auto_increment primary key,
    nome varchar(50) not null,
    orgao varchar(50) not null,
    cargo varchar(60) not null,
    requisito varchar(50) not null, 
    data_inscricao date not null,
    data_prova date not null,
    status varchar(30) not null,
	editalId int unsigned default null,
    CONSTRAINT fk_concurso_edital FOREIGN KEY (editalId) REFERENCES edital(id_edital)
    )ENGINE=InnoDB;
    
CREATE TABLE IF NOT EXISTS materia (

	sigla varchar(8) primary key,
    nome varchar(30) not null
    
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS livro (

	id_livro int unsigned auto_increment primary key,
    nome varchar(50) not null,
    autor varchar(50) not null,
    versao int unsigned default null,
    materia varchar(50),
    link varchar(200)
    
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cronograma(

	id_cronograma int unsigned auto_increment primary key,
	planejamento varchar(50) not null
    
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS usuario (

	cpf int unsigned primary key,
    nome varchar(50) not null,
    email varchar(50) default 'não informado',
    senha varchar(10) not null,
    escolaridade varchar(20) not null,
    cronogramaId int unsigned default null,
    CONSTRAINT fk_usuario_cronograma FOREIGN KEY (cronogramaId) REFERENCES cronograma (id_cronograma)
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS vincular (
	
    materia_id varchar(8) not null,
    concurso_id int unsigned default null,
    CONSTRAINT fk_vincular_materia FOREIGN KEY (materia_id) REFERENCES materia (sigla),
    CONSTRAINT fk_vincular_concurso FOREIGN KEY (concurso_id) REFERENCES concurso (id_concurso)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS inscrever (

	alternativa char not null,
    resposta char not null,
    usuario_id int unsigned default null,
    concurso_id int unsigned default null,
    CONSTRAINT pk_inscrever_usuario_concurso PRIMARY KEY (usuario_id,concurso_id),
	CONSTRAINT fk_inscrever_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (cpf),
    CONSTRAINT fk_inscrever_concurso FOREIGN KEY (concurso_id) REFERENCES concurso (id_concurso)
    
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ler (

	usuario_id int unsigned default null,
    livro_id int unsigned default null,
	CONSTRAINT fk_ler_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (cpf),
	CONSTRAINT fk_ler_livro FOREIGN KEY (livro_id) REFERENCES livro (id_livro)

)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS simular (

	numero_questao int unsigned not null primary key,
	usuario_id int unsigned default null,
	materia_id varchar(8) not null,
    concurso_id int unsigned default null,
    CONSTRAINT fk_simular_materia FOREIGN KEY (materia_id) REFERENCES materia (sigla),
    CONSTRAINT fk_simular_concurso FOREIGN KEY (concurso_id) REFERENCES concurso (id_concurso),
	CONSTRAINT fk_simular_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (cpf)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS questao(

	identificador int unsigned not null primary key,
    enunciado varchar(200) not null,
    resposta char not null,
	usuario_id int unsigned default null,
	materia_id varchar(8) not null,
    concurso_id int unsigned default null,
    numero_quest int unsigned default null,
    CONSTRAINT fk_questao_numero FOREIGN KEY (numero_quest) REFERENCES simular (numero_questao),
    CONSTRAINT fk_questao_materia FOREIGN KEY (materia_id) REFERENCES materia (sigla),
    CONSTRAINT fk_questao_concurso FOREIGN KEY (concurso_id) REFERENCES concurso (id_concurso),
	CONSTRAINT fk_questao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (cpf)

)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alternativa(

	frase varchar(100) not null primary key,
    letra char not null,
	id_questao int unsigned,
    CONSTRAINT fk_alternativa_identificador FOREIGN KEY (id_questao) REFERENCES questao (identificador)
)ENGINE=InnoDB;

