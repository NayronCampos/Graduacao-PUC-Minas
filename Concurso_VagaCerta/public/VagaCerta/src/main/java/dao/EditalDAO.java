package dao;

import java.sql.*;
import java.util.*;

import Classes.Edital;

public class EditalDAO extends DAO {
	public EditalDAO(){
		super();
		conectar();
	}
	
	public boolean insert(Edital edital) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO edital(id_ediatl, arquivo, data_publi) " + "VALUES(" 
		            + edital.getID() + ", '" 
		            + edital.getArquivo() + "', '" 
		            + edital.getData_publicacao() + "');";
			System.out.println(sql);;
			st.executeUpdate(sql);
			st.close();
			status = true;
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public Edital getEdital(int id) {
	    Edital edital = null;
	    String sql = "SELECT * FROM edital WHERE id_edital = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                edital= new Edital(
	                    rs.getInt("id_edital"),
	                    rs.getString("arquivo"),
	                    rs.getDate("data_publi")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return edital;
	}
	
	public List<Edital> getAllEditais() {
	    List<Edital> editais = new ArrayList<>();
	    String sql = "SELECT * FROM edital";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql);
	    	ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Edital edital = new Edital(
	                rs.getInt("id_edital"),
	                rs.getString("arquivo"),
	                rs.getDate("data_publi")
	            );
	            editais.add(edital);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return editais;
	}
	
	public boolean update(Edital edital) {
	    boolean status = false;
	    try {
	        Statement st = conexao.createStatement();
	        String sql = "UPDATE editais SET "
	            + "arquivo = '" + edital.getArquivo() + "', "
	            + "data_publi = '" + edital.getData_publicacao()+ "', "
	            + " WHERE id_edital = " + edital.getID() + ";";

	        System.out.println(sql);
	        st.executeUpdate(sql);
	        st.close();
	        status = true;
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM edital WHERE codigo = " + codigo;
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
