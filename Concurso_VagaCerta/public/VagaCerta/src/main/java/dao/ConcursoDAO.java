package dao;

import java.sql.*;
import java.util.*;

import Classes.Concurso;

public class ConcursoDAO extends DAO{
	public ConcursoDAO(){
		super();
		conectar();
	}

	public boolean insert(Concurso concurso) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO concurso(id_concurso, nome, escolaridade, localizacao, categoria, banca, descricao, orgao, cargo, materiaisDeEstudo, horario, status, data_inscricao, data_termino, editalId) " + "VALUES(" 
		            + concurso.getID() + ", '" 
		            + concurso.getNome() + "', '" 
		            + concurso.getEscolaridade() + "', '" 
		            + concurso.getLocalizacao() + "', '" 
		            + concurso.getCategoria() + "', '" 
		            + concurso.getBanca() + "', '" 
		            + concurso.getDescricao() + "', '" 
		            + concurso.getOrgao() + "', '" 
		            + concurso.getEditalID() + "', '" 
		            + concurso.getMateriaisDeEstudo() + "', '" 
		            + concurso.getHorario() + "', '" 
		            + concurso.getStatus() + "', '" 
		            + concurso.getInicioIncricoes() + "', '" 
		            + concurso.getTerminoIncricoes() + "');";
			System.out.println(sql);;
			st.executeUpdate(sql);
			st.close();
			status = true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean update(Concurso concurso) {
	    boolean status = false;
	    try {
	        Statement st = conexao.createStatement();
	        String sql = "UPDATE concurso SET "
	            + "nome = '" + concurso.getNome() + "', "
	            + "escolaridade = '" + concurso.getEscolaridade() + "', "
	            + "localizacao = '" + concurso.getLocalizacao() + "', "
	            + "categoria = '" + concurso.getCategoria() + "', "
	            + "banca = '" + concurso.getBanca() + "', "
	            + "descricao = '" + concurso.getDescricao() + "', "
	            + "orgao = '" + concurso.getOrgao() + "', "
	            + "cargo = '" + concurso.getCargo() + "', "
	            + "materiaisDeEstudo = '" + concurso.getMateriaisDeEstudo() + "', "
	            + "horario = '" + concurso.getHorario() + "', "
	            + "status = " + concurso.getStatus() + ", "
	            + "data_inscricao = '" + concurso.getInicioIncricoes() + "', "
	            + "data_termino = '" + concurso.getTerminoIncricoes() + "', "
	            + "editalId = " + concurso.getEditalID()
	            + " WHERE id_concurso = " + concurso.getID() + ";";

	        System.out.println(sql);
	        st.executeUpdate(sql);
	        st.close();
	        status = true;
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    return status;
	}
	
	public Concurso getConcurso(int id) {
	    Concurso concurso = null;
	    String sql = "SELECT * FROM concurso WHERE id_concurso = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                concurso = new Concurso(
	                    rs.getInt("id_concurso"),
	                    rs.getString("nome"),
	                    rs.getString("escolaridade"),
	                    rs.getString("localizacao"),
	                    rs.getString("categoria"),
	                    rs.getString("banca"),
	                    rs.getString("descricao"),
	                    rs.getString("orgao"),
	                    rs.getString("cargo"),
	                    rs.getString("materiaisDeEstudo"),
	                    rs.getString("horario"),
	                    rs.getBoolean("status"),
	                    rs.getDate("data_inscricao"),
	                    rs.getDate("data_termino"),
	                    rs.getInt("editalId")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return concurso;
	}
	
	public List<Concurso> getAllConcursos() {
	    List<Concurso> concursos = new ArrayList<>();
	    String sql = "SELECT * FROM concurso";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql);
	    	ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Concurso concurso = new Concurso(
	                rs.getInt("id_concurso"),
	                rs.getString("nome"),
	                rs.getString("escolaridade"),
	                rs.getString("localizacao"),
	                rs.getString("categoria"),
	                rs.getString("banca"),
	                rs.getString("descricao"),
	                rs.getString("orgao"),
	                rs.getString("cargo"),
	                rs.getString("materiaisDeEstudo"),
	                rs.getString("horario"),
	                rs.getBoolean("status"),
	                rs.getDate("data_inscricao"),
	                rs.getDate("data_prova"),
	                rs.getInt("editalId")
	            );
	            concursos.add(concurso);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return concursos;
	}

	public boolean delete(int codigo) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM concurso WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		}catch (Exception e) { 
			System.err.println(e.getMessage());
		}
		return status;
	}
}
