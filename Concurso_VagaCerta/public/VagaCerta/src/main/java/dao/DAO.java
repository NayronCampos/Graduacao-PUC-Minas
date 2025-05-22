package dao;

import java.sql.*;
import java.util.*;

public class DAO {
	protected static Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
	    String driverName = "org.postgresql.Driver";
	    String serverName = "localhost";
	    String database = "concurso_vagacerta"; 
	    int porta = 5432;
	    String urlPadrao = "jdbc:postgresql://" + serverName + ":" + porta + "/";
	    String username = "postgres";
	    String password = "pucminas25";
	    boolean status = false;

	    try {
	        Class.forName(driverName);

	        try (Connection connInicial = DriverManager.getConnection(urlPadrao + "postgres", username, password)) {
	            PreparedStatement stmt = connInicial.prepareStatement(
	                "SELECT 1 FROM pg_database WHERE datname = ?");
	            stmt.setString(1, database);
	            ResultSet rs = stmt.executeQuery();

	            if (!rs.next()) {
	                System.out.println("Banco '" + database + "' não existe. Criando...");
	                Statement createStmt = connInicial.createStatement();
	                createStmt.executeUpdate("CREATE DATABASE " + database);
	                createStmt.close();
	            } else {
	                System.out.println("Banco '" + database + "' já existe.");
	            }

	            stmt.close();
	        }

	        conexao = DriverManager.getConnection(urlPadrao + database, username, password);
	        status = (conexao != null);

	        Statement stmt = conexao.createStatement();
	        String sql = """
	                CREATE TABLE IF NOT EXISTS edital (
	                id_edital SERIAL PRIMARY KEY,
	                arquivo VARCHAR(100) NOT NULL,
	                data_publi DATE NOT NULL
	            );

	            CREATE TABLE IF NOT EXISTS concurso (
	                id_concurso SERIAL PRIMARY KEY,
	                nome VARCHAR(50) NOT NULL,
	                escolaridade VARCHAR(50) NOT NULL,
	                localizacao VARCHAR(50) NOT NULL,
	                categoria VARCHAR(100) NOT NULL,
	                banca VARCHAR(50) NOT NULL,
	                descricao VARCHAR(600) NOT NULL,
	                orgao VARCHAR(50) NOT NULL,
	                cargo VARCHAR(60) NOT NULL,
	                materiaisDeEstudo VARCHAR(600) NOT NULL,
	                horario VARCHAR(50) NOT NULL,
	                status BOOLEAN NOT NULL,
	                data_inscricao DATE NOT NULL,
	                data_termino DATE NOT NULL,
	                editalId INTEGER,
	                CONSTRAINT fk_concurso_edital FOREIGN KEY (editalId) REFERENCES edital(id_edital)
	            );

	            CREATE TABLE IF NOT EXISTS materia (
	                sigla VARCHAR(8) PRIMARY KEY,
	                nome VARCHAR(30) NOT NULL
	            );

	            CREATE TABLE IF NOT EXISTS livro (
	                id_livro SERIAL PRIMARY KEY,
	                nome VARCHAR(50) NOT NULL,
	                autor VARCHAR(50) NOT NULL,
	                versao INTEGER,
	                materia VARCHAR(50)
	            );

	            CREATE TABLE IF NOT EXISTS cronograma (
	                id_cronograma SERIAL PRIMARY KEY,
	                planejamento VARCHAR(50) NOT NULL
	            );

	            CREATE TABLE IF NOT EXISTS usuario (
	                cpf BIGINT PRIMARY KEY,
	                nome VARCHAR(50) NOT NULL,
	                email VARCHAR(50) DEFAULT 'não informado',
	                senha VARCHAR(10) NOT NULL,
	                escolaridade VARCHAR(20) NOT NULL,
	                cronogramaId INTEGER,
	                CONSTRAINT fk_usuario_cronograma FOREIGN KEY (cronogramaId) REFERENCES cronograma(id_cronograma)
	            );

	            CREATE TABLE IF NOT EXISTS vincular (
	                materia_id VARCHAR(8) NOT NULL,
	                concurso_id INTEGER,
	                CONSTRAINT fk_vincular_materia FOREIGN KEY (materia_id) REFERENCES materia(sigla),
	                CONSTRAINT fk_vincular_concurso FOREIGN KEY (concurso_id) REFERENCES concurso(id_concurso)
	            );

	            CREATE TABLE IF NOT EXISTS inscrever (
	                alternativa CHAR NOT NULL,
	                resposta CHAR NOT NULL,
	                usuario_id BIGINT,
	                concurso_id INTEGER,
	                CONSTRAINT pk_inscrever_usuario_concurso PRIMARY KEY (usuario_id, concurso_id),
	                CONSTRAINT fk_inscrever_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(cpf),
	                CONSTRAINT fk_inscrever_concurso FOREIGN KEY (concurso_id) REFERENCES concurso(id_concurso)
	            );

	            CREATE TABLE IF NOT EXISTS ler (
	                usuario_id BIGINT,
	                livro_id INTEGER,
	                CONSTRAINT fk_ler_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(cpf),
	                CONSTRAINT fk_ler_livro FOREIGN KEY (livro_id) REFERENCES livro(id_livro)
	            );

	            CREATE TABLE IF NOT EXISTS simular (
	                numero_questao INTEGER PRIMARY KEY,
	                usuario_id BIGINT,
	                materia_id VARCHAR(8) NOT NULL,
	                concurso_id INTEGER,
	                CONSTRAINT fk_simular_materia FOREIGN KEY (materia_id) REFERENCES materia(sigla),
	                CONSTRAINT fk_simular_concurso FOREIGN KEY (concurso_id) REFERENCES concurso(id_concurso),
	                CONSTRAINT fk_simular_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(cpf)
	            );

	            CREATE TABLE IF NOT EXISTS questao (
	                identificador INTEGER PRIMARY KEY,
	                enunciado VARCHAR(200) NOT NULL,
	                resposta CHAR NOT NULL,
	                usuario_id BIGINT,
	                materia_id VARCHAR(8) NOT NULL,
	                concurso_id INTEGER,
	                numero_quest INTEGER,
	                CONSTRAINT fk_questao_numero FOREIGN KEY (numero_quest) REFERENCES simular(numero_questao),
	                CONSTRAINT fk_questao_materia FOREIGN KEY (materia_id) REFERENCES materia(sigla),
	                CONSTRAINT fk_questao_concurso FOREIGN KEY (concurso_id) REFERENCES concurso(id_concurso),
	                CONSTRAINT fk_questao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(cpf)
	            );

	            CREATE TABLE IF NOT EXISTS alternativa (
	                frase VARCHAR(100) PRIMARY KEY,
	                letra CHAR NOT NULL,
	                id_questao INTEGER,
	                CONSTRAINT fk_alternativa_identificador FOREIGN KEY (id_questao) REFERENCES questao(identificador)
	            );
	            """;

	        stmt.executeUpdate(sql);
	        stmt.close();

	        System.out.println("Conexão efetuada.");

	    } catch (ClassNotFoundException e) {
	        System.err.println("Driver PostgreSQL não encontrado: " + e.getMessage());
	    } catch (SQLException e) {
	        System.err.println("Erro de SQL: " + e.getMessage());
	    }

	    return status;
	}

	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
}