package Services;

import java.sql.*;
import java.util.*;
import java.sql.Date
import java.util.Date;
import java.text.*;
import java.security.*;
import dao.*;
import Classes.Concurso;
import Classes.Edital;
import spark.Request;
import spark.Response;

public class EditalServices extends DAO{
	public EditalDAO editalDAO = new EditalDAO();
	
	public String insert(Request request, Response response) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		int ID = geradorDeId();
		String arquivo = request.queryParams("arquivo");
		String data_publiStr = request.queryParams("data_publi");
		Date data_publi = formato.parse(data_publi);
		String resp = "";
		
		Edital edital = new Edital(ID, arquivo, data_publi);
		
		if(editalDAO.insert(edital)) {
            resp = "Edital (" + arquivo + ") inserido!";
            response.status(201); 
		} else {
			resp = "Edital (" + arquivo + ") não inserido!";
			response.status(404);
		}
		return resp;
	}
	
	public String update(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));
		Edital edital = editalDAO.getEdital(id);
		if(edital != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			//edital.setID(Integer.parseInt(request.queryParams("id")));
			edital.setArquivo(request.queryParams("arquivo"));
			String data_publiStr = request.queryParams("data_publi");
			edital.setData_publicacao(formato.parse(data_publiStr));
			editalDAO.update(edital);
			return id;
		}else {
			response.status(404);
			return "Edital não encontrado!";
		}
	}
	
	public Object get(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));
		Edital edital = editalDAO.getEdital(id);
		
		if(edital != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");
			
			return "<edital>\n" +
					"\t<id>" + edital.getID() + "</id>\n" +
					"\t<arquivo>" + edital.getArquivo() + "</arquivo>\n" +
					"\t<Data_publicacao>" + edital.getData_publicacao() + "</Data_publicacao>\n" +
					"</edital>\n";
		}else {
			response.status(404);
			return "Edital " + id + " não encontrado";
		}
	}
	
	public Object getAll(Request request, Response response) {
	    List<Edital> editais = editalDAO.getAllEditais();

	    if (editais != null && !editais.isEmpty()) {
	        response.header("Content-Type", "application/xml");
	        response.header("Content-Encoding", "UTF-8");

	        StringBuilder xml = new StringBuilder();
	        xml.append("<concursos>\n");

	        for (Edital edital : editais) {
	            xml.append("\t<edital>\n")
	                .append("\t\t<id>").append(edital.getID()).append("</id>\n")
	                .append("\t\t<arquivo>").append(edital.getArquivo()).append("</arquivo>\n")
	                .append("\t\t<data_publi>").append(edital.getData_publicacao()).append("</data_publi>\n")
	                .append("\t</edital>\n");
	        }

	        xml.append("</edital>");

	        return xml.toString();
	    } else {
	        response.status(404);
	        return "Nenhum edital encontrado";
	    }
	}
	
	public Object delete(Request request, Response response) {
		int id =  Integer.parseInt(request.params(":id"));

		Edital edital = editalDAO.getEdital(id);
		if(edital != null) {
			editalDAO.delete(id);
			response.status(200);
			return "Edital com ID " + id + " foi deletado com sucesso!";
		}else {
			response.status(404);
			return "Edital não encontrado!";
		}
	}
	
	public boolean idExiste(long id) {
        boolean existe = false;
        String sql = "SELECT 1 FROM edital WHERE id_edital = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
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

    public int geradorDeId() {
        String ALFABETO = "0123456789";
        SecureRandom random = new SecureRandom();
        int id;

        do {
            StringBuilder sb = new StringBuilder("13");
            for (int i = 0; i < 9; i++) {
                int index = random.nextInt(ALFABETO.length());
                sb.append(ALFABETO.charAt(index));
            }
            id = (int) Long.parseLong(sb.toString());
        } while (idExiste(id));

        return id;
    }

}
