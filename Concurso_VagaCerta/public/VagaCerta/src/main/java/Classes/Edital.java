package Classes;

import java.util.*;
import java.sql.Date

public class Edital {
	private int ID;
	private String arquivo;
	private Date data_publicacao;
	
	public Edital(){
		this.ID = -1;
		this.arquivo = "";
		this.data_publicacao = null;
	}
	
	public Edital(int ID, String arquivo, Date data_publicacao) {
		this.ID = ID;
		this.arquivo = arquivo;
		this.data_publicacao = data_publicacao;
	}

	public int getID() {
	    return ID;
	}

	public void setID(int ID) {
	    this.ID = ID;
	}

	public String getArquivo() {
	    return arquivo;
	}

	public void setArquivo(String arquivo) {
	    this.arquivo = arquivo;
	}

	public Date getData_publicacao() {
	    return data_publicacao;
	}

	public void setData_publicacao(Date data_publicacao) {
	    this.data_publicacao = data_publicacao;
	}

	@Override
	public String toString() {
	    return "Edital {" +
	           "ID=" + ID +
	           ", arquivo='" + arquivo + '\'' +
	           ", data_publicacao=" + data_publicacao +
	           '}';
	}
}