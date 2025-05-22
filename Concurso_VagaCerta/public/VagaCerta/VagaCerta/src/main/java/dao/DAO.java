package dao;

import java.sql.*;
import java.util.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        String serverName = "localhost";
        String database = "concursos";
        int porta = 5432;
        String urlPadrao = "jdbc:postgresql://" + serverName + ":" + porta + "/";
        String username = "postgres";
        String password = "marcos";
        boolean status = false;

        try {
            Class.forName(driverName);
            try (Connection connInicial = DriverManager.getConnection(urlPadrao + "postgres", username, password)) {
                PreparedStatement stmt = connInicial.prepareStatement(
                        "SELECT 1 FROM pg_database WHERE datname = ?");
                stmt.setString(1, database);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("Banco 'concursos' não existe. Criando...");
                    Statement createStmt = connInicial.createStatement();
                    createStmt.executeUpdate("CREATE DATABASE " + database);
                    createStmt.close();
                } else {
                    System.out.println("Banco 'concursos' já existe.");
                }
                stmt.close();
            }

            conexao = DriverManager.getConnection(urlPadrao + database, username, password);
            status = (conexao != null);

            Statement stmt = conexao.createStatement();
            String sql = """
                CREATE TABLE IF NOT EXISTS Usuario (
                    id SERIAL PRIMARY KEY,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    nome VARCHAR(255) NOT NULL,
                    senha VARCHAR(255) NOT NULL,
                    nivel_de_escolaridade VARCHAR(100)
                );

                CREATE TABLE IF NOT EXISTS Concurso (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    orgao VARCHAR(255),
                    cargo VARCHAR(255),
                    requisitos TEXT,
                    data_de_inscricao DATE,
                    data_da_prova DATE,
                    status BOOLEAN
                );

                CREATE TABLE IF NOT EXISTS Edital (
                    id SERIAL PRIMARY KEY,
                    concurso_id INT NOT NULL,
                    arquivo VARCHAR(255) NOT NULL,
                    data_de_publicacao DATE,
                    CONSTRAINT fk_edital_concurso FOREIGN KEY (concurso_id)
                        REFERENCES Concurso(id) ON DELETE CASCADE
                );

                CREATE TABLE IF NOT EXISTS Questao (
                    id SERIAL PRIMARY KEY,
                    concurso_id INT NOT NULL,
                    enunciado TEXT NOT NULL,
                    alternativas TEXT NOT NULL,
                    resposta_certa TEXT NOT NULL,
                    CONSTRAINT fk_questao_concurso FOREIGN KEY (concurso_id)
                        REFERENCES Concurso(id) ON DELETE CASCADE
                );

                CREATE TABLE IF NOT EXISTS Livro (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    autor VARCHAR(255),
                    versao VARCHAR(50),
                    materia VARCHAR(100)
                );

                CREATE TABLE IF NOT EXISTS Simulado (
                    id SERIAL PRIMARY KEY,
                    materia VARCHAR(100) NOT NULL,
                    numero_de_questoes INT NOT NULL
                );

                CREATE TABLE IF NOT EXISTS UsuarioSimulado (
                    usuario_id INT,
                    simulado_id INT,
                    PRIMARY KEY (usuario_id, simulado_id),
                    CONSTRAINT fk_usuariosimulado_usuario FOREIGN KEY (usuario_id)
                        REFERENCES Usuario(id) ON DELETE CASCADE,
                    CONSTRAINT fk_usuariosimulado_simulado FOREIGN KEY (simulado_id)
                        REFERENCES Simulado(id) ON DELETE CASCADE
                );

                CREATE TABLE IF NOT EXISTS SimuladoQuestao (
                    simulado_id INT,
                    questao_id INT,
                    PRIMARY KEY (simulado_id, questao_id),
                    CONSTRAINT fk_simuladoquestao_simulado FOREIGN KEY (simulado_id)
                        REFERENCES Simulado(id) ON DELETE CASCADE,
                    CONSTRAINT fk_simuladoquestao_questao FOREIGN KEY (questao_id)
                        REFERENCES Questao(id) ON DELETE CASCADE
                );

                CREATE TABLE IF NOT EXISTS Cronograma (
                    id SERIAL PRIMARY KEY,
                    usuario_id INT NOT NULL,
                    planejamento_de_estudo TEXT,
                    livro_id INT,
                    questao_id INT,
                    CONSTRAINT fk_cronograma_usuario FOREIGN KEY (usuario_id)
                        REFERENCES Usuario(id) ON DELETE CASCADE,
                    CONSTRAINT fk_cronograma_livro FOREIGN KEY (livro_id)
                        REFERENCES Livro(id) ON DELETE SET NULL,
                    CONSTRAINT fk_cronograma_questao FOREIGN KEY (questao_id)
                        REFERENCES Questao(id) ON DELETE SET NULL
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
