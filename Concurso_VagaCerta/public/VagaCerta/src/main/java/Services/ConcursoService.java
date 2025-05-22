package Services;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.security.*;
import dao.*;
import Classes.Concurso;
import spark.Request;
import spark.Response;

public class ConcursoService extends DAO{
	public ConcursoDAO concursoDAO = new ConcursoDAO();
	
	public String insert(Request request, Response response) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		int ID = geradorDeId();
		String nome = request.queryParams("nome");
		String escolaridade = request.queryParams("escolaridade");
		String localizacao = request.queryParams("localizacao");
		String categoria = request.queryParams("categoria");
		String banca = request.queryParams("banca");
		String descricao = request.queryParams("descricao");
		String orgao = request.queryParams("orgao");
		String cargo = request.queryParams("cargo");
		int editalID = Integer.parseInt(request.queryParams("editalID"));
		String materiaisDeEstudo = request.queryParams("materiaisDeEstudo");
		String horario = request.queryParams("horario");
		boolean status = Boolean.parseBoolean(request.queryParams("status"));
		String dataIncricaoStr = request.queryParams("inicioIncricoes");
		String dataFinalStr = request.queryParams("terminoIncricoes");
		Date inicioIncricoes = formato.parse(dataIncricaoStr);
		Date terminoIncricoes = formato.parse(dataFinalStr);
		String resp = "";
		
		Concurso concurso = new Concurso(ID, nome, escolaridade, localizacao, categoria, banca, descricao, orgao, cargo, materiaisDeEstudo, horario, status, inicioIncricoes, terminoIncricoes,  editalID);
		
		if(concursoDAO.insert(concurso)) {
            resp = "Concurso (" + nome + ") inserido!";
            response.status(201); 
		} else {
			resp = "Concurso (" + nome + ") não inserido!";
			response.status(404);
		}
		return resp;
	}
	
	public Object delete(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));

		Concurso concurso = concursoDAO.getConcurso(id);
		if(concurso != null) {
			concursoDAO.delete(id);
			response.status(200);
			return "Concurso com ID " + id + " foi deletado com sucesso!";
		}else {
			response.status(404);
			return "Concurso não encontrado!";
		}
	}
	
	public String update(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));
		Concurso concurso = concursoDAO.getConcurso(id);
		if(concurso != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			//concurso.setID(Integer.parseInt(request.queryParams("nome")));
			concurso.setEscolaridade(request.queryParams("escolaridade"));
			concurso.setLocalizacao(request.queryParams("localizacao"));
			concurso.setCategoria(request.queryParams("categoria"));
			concurso.setBanca(request.queryParams("banca"));
			concurso.setDescricao(request.queryParams("descricao"));
			concurso.setOrgao(request.queryParams("orgao"));
			concurso.setCargo(request.queryParams("cargo"));
			concurso.setEditalID(Integer.parseInt(request.queryParams("editalID")));
			concurso.setMateriaisDeEstudo(request.queryParams("materiaisDeEstudo"));
			concurso.setHorario(request.queryParams("horario"));
			concurso.setStatus(Boolean.parseBoolean(request.queryParams("localizacao")));
			String dataIncricaoStr = request.queryParams("inicioIncricoes");
			String dataFinalStr = request.queryParams("terminoIncricoes");
			concurso.setInicioIncricoes(formato.parse(dataIncricaoStr));
			concurso.setTerminoIncricoes(formato.parse(dataFinalStr));
			concursoDAO.update(concurso);
			return id;
		}else {
			response.status(404);
			return "Concurso não encontrado!";
		}
	}
	
	public Object get(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));
		Concurso concurso = concursoDAO.getConcurso(id);
		
		if(concurso != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");
			
			return "<concurso>\n" +
					"\t<id>" + concurso.getID() + "</id>\n" +
					"\t<nome>" + concurso.getNome() + "</nome>\n" +
					"\t<escolaridade>" + concurso.getEscolaridade() + "</escolaridade>\n" +
					"\t<localizacao>" + concurso.getLocalizacao() + "</localizacao>\n" +
					"\t<categoria>" + concurso.getCategoria() + "</categoria>\n" +
					"\t<banca>" + concurso.getBanca() + "</banca>\n" +
					"\t<descricao>" + concurso.getDescricao() + "</banca>\n" +
					"\t<orgao>" + concurso.getOrgao() + "</orgao>\n" +
					"\t<cargo>" + concurso.getCargo() + "</cargo>\n" +
					"\t<linkEdital>" + concurso.getEditalID() + "</linkEdital>\n" +
					"\t<materiaisDeEstudo>" + concurso.getMateriaisDeEstudo() + "</materiaisDeEstudo>\n" +
					"\t<horario>" + concurso.getHorario() + "</horario>\n" +
					"\t<status>" + (concurso.getStatus() ? "Ativo" : "Inativo") + "</status>\n" +
					"\t<inicioIncricoes>" + concurso.getInicioIncricoes() + "</inicioIncricoes>\n" +
					"\t<terminoIncricoes>" +  concurso.getTerminoIncricoes() + "</terminoIncricoes>\n" +
					"</concurso>\n";
		}else {
			response.status(404);
			return "Concurso " + id + " não encontrado";
		}
	}
	
	public Object getAll(Request request, Response response) {
	    List<Concurso> concursos = concursoDAO.getAllConcursos();

	    if (concursos != null && !concursos.isEmpty()) {
	        response.header("Content-Type", "application/xml");
	        response.header("Content-Encoding", "UTF-8");

	        StringBuilder xml = new StringBuilder();
	        xml.append("<concursos>\n");

	        for (Concurso concurso : concursos) {
	            xml.append("\t<concurso>\n")
	                .append("\t\t<id>").append(concurso.getID()).append("</id>\n")
	                .append("\t\t<nome>").append(concurso.getNome()).append("</nome>\n")
	                .append("\t\t<escolaridade>").append(concurso.getEscolaridade()).append("</escolaridade>\n")
	                .append("\t\t<localizacao>").append(concurso.getLocalizacao()).append("</localizacao>\n")
	                .append("\t\t<categoria>").append(concurso.getCategoria()).append("</categoria>\n")
	                .append("\t\t<banca>").append(concurso.getBanca()).append("</banca>\n")
	                .append("\t\t<descricao>").append(concurso.getDescricao()).append("</descricao>\n")
	                .append("\t\t<orgao>").append(concurso.getOrgao()).append("</orgao>\n")
	                .append("\t\t<cargo>").append(concurso.getCargo()).append("</cargo>\n")
	                .append("\t\t<linkEdital>").append(concurso.getEditalID()).append("</linkEdital>\n")
	                .append("\t\t<materiaisDeEstudo>").append(concurso.getMateriaisDeEstudo()).append("</materiaisDeEstudo>\n")
	                .append("\t\t<horario>").append(concurso.getHorario()).append("</horario>\n")
	                .append("\t\t<status>").append(concurso.getStatus() ? "Ativo" : "Inativo").append("</status>\n")
	                .append("\t\t<inicioIncricoes>").append(concurso.getInicioIncricoes()).append("</inicioIncricoes>\n")
	                .append("\t\t<terminoIncricoes>").append(concurso.getTerminoIncricoes()).append("</terminoIncricoes>\n")
	                .append("\t</concurso>\n");
	        }

	        xml.append("</concursos>");

	        return xml.toString();
	    } else {
	        response.status(404);
	        return "Nenhum concurso encontrado";
	    }
	}

	
	public static boolean idExiste(int id) {
	    boolean existe = false;
	    String sql = "SELECT 1 FROM concurso WHERE id_concurso = ?";

	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                existe = true; 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return existe;
	}

	
	public static int geradorDeId() {
		String ALFABETO = "0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder("27"); 
		do{
			for (int i = 0; i < 14; i++) {
	            int index = random.nextInt(ALFABETO.length());
	            sb.append(ALFABETO.charAt(index));
	        }
		}while(idExiste(Integer.parseInt(sb.toString())));
        return Integer.parseInt(sb.toString());
	}
}
