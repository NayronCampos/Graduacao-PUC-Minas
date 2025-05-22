package dao;

import java.sql.*;
import java.util.*;

import Classes.Concurso;

public class ConcursoDAO {
	public ConcursoDAO(){
		super();
		conectar();
	}
	
	
	
	public boolean delete(String codigo) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM concursos WHERE codigo = " + codigo;
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
