package Services;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.security.*;
import dao.ConcursoDAO;
import Classes.Concurso;
import spark.Request;
import spark.Response;

public class ConcursoService {
	public ConcursoDAO concursoDAO = new ConcursoDAO();
	
	public String insert(Request request, Response response) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		String ID = geradorDeId();
		String nome = request.queryParams("nome");
		String escolaridade = request.queryParams("escolaridade");
		String localizacao = request.queryParams("localizacao");
		String categoria = request.queryParams("categoria");
		String banca = request.queryParams("banca");
		String descricao = request.queryParams("descricao");
		String orgao = request.queryParams("orgao");
		String linkEdital = request.queryParams("linkEdital");
		String materiaisDeEstudo = request.queryParams("materiaisDeEstudo");
		String horario = request.queryParams("horario");
		boolean status = Boolean.parseBoolean(request.queryParams("status"));
		String dataIncricaoStr = request.queryParams("inicioIncricoes");
		String dataFinalStr = request.queryParams("terminoIncricoes");
		Date inicioIncricoes = formato.parse(dataIncricaoStr);
		Date terminoIncricoes = formato.parse(dataFinalStr);
		String resp = "";
		
		Concurso concurso = new Concurso(ID, nome, escolaridade, localizacao, categoria, banca, descricao, orgao, linkEdital, materiaisDeEstudo, horario, status, inicioIncricoes, terminoIncricoes);
		
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
		String id = request.params(":id");

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
		String id = request.params(":id");
		Concurso concurso = concursoDAO.getConcurso(id);
		if(concurso != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			concurso.setID(request.queryParams("nome"));
			concurso.setEscolaridade(request.queryParams("escolaridade"));
			concurso.setLocalizacao(request.queryParams("localizacao"));
			concurso.setCategoria(request.queryParams("categoria"));
			concurso.setBanca(request.queryParams("banca"));
			concurso.setDescricao(request.queryParams("descricao"));
			concurso.setOrgao(request.queryParams("orgao"));
			concurso.setLinkEdital(request.queryParams("linkEdital"));
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
			return "Produto não encontrado!";
		}
	}
	
	public Object get(Request request, Response response) {
		String id = request.params(":id");
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
					"\t<linkEdital>" + concurso.getLinkEdital() + "</linkEdital>\n" +
					"\t<linkEdital>" + concurso.getLinkEdital() + "</linkEdital>\n" +
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
	
	// falta o negocio de conferir se existe!!!
	
	public static String geradorDeId() {
		String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder("27"); 
		
		for (int i = 0; i < 7; i++) {
            int index = random.nextInt(ALFABETO.length());
            sb.append(ALFABETO.charAt(index));
        }
        return sb.toString();
	}
}
