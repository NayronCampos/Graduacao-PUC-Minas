package Services;

import static spark.Spark.*;
import spark.Spark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.ConcursoDAO;
import Classes.Concurso;
import spark.Request;
import spark.Response;

public class ConcursoService {
    private ConcursoDAO concursoDAO = new ConcursoDAO();
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public ConcursoService() {
        path("/", () -> {
            // CREATE via form HTML (url-encoded)
            post("concursos", this::createConcurso);

            // READ ALL / READ ONE
            get("concursos", this::getAllConcursos);
            get("concursos/:id", this::getConcursoById);

            // UPDATE via form HTML (override _method=PUT)
            put("concursos/:id", this::updateConcurso);

            // DELETE via form HTML (override _method=DELETE)
            Spark.delete("concursos/:id", this::deleteConcurso);
        });
    }

    // POST /concursos
    private Object createConcurso(Request req, Response res) {
        try {
            int id = concursoDAO.geradorDeId();
            if (id <= 0) {
                res.status(500);
                return "Erro ao gerar ID do concurso";
            }
            String nome = req.queryParams("nome_cadastro");
            String escolaridade = req.queryParams("nivel-ensino_cadastro");
            String localizacao = req.queryParams("localizacao_cadastro");
            String categoria = req.queryParams("categoria_cadastro");
            String banca = req.queryParams("banca_cadastro");
            String descricao = req.queryParams("descricao_cadastro");
            String orgao = req.queryParams("orgao_cadastro");
            String cargo = req.queryParams("cargo_cadastro");
            //int editalID = Integer.parseInt(req.queryParams("editalID"));
            String materiais = req.queryParams("materiais_cadastro");
            String horario = req.queryParams("horario_cadastro");
            String status = req.queryParams("status_cadastro");
            Date inicio = formato.parse(req.queryParams("data-inscricao_cadastro"));
            Date termino = formato.parse(req.queryParams("data-prova_cadastro"));

            Concurso concurso = new Concurso(id, nome, escolaridade, localizacao,
                    categoria, banca, descricao, orgao, cargo,
                    materiais, horario, status, inicio, termino);

            if (concursoDAO.insert(concurso)) {
                res.status(201);
                res.redirect("/cadastrodeconcurso.html");
                return "";
            } else {
                res.status(500);
                return "Erro ao inserir concurso";
            }
        } catch (ParseException | NumberFormatException e) {
            res.status(400);
            return "Erro de formato: " + e.getMessage();
        }
    }

    // GET /concursos
    private Object getAllConcursos(Request req, Response res) {
        List<Concurso> list = concursoDAO.getAllConcursos();
        if (list == null || list.isEmpty()) {
            res.status(404);
            return "Nenhum concurso encontrado";
        }
        res.type("application/xml");
        StringBuilder xml = new StringBuilder();
        xml.append("<concursos>\n");
        for (Concurso c : list) {
            xml.append("  <concurso>\n")
               .append("    <id>").append(c.getID()).append("</id>\n")
               .append("    <nome>").append(c.getNome()).append("</nome>\n")
               .append("    <escolaridade>").append(c.getEscolaridade()).append("</escolaridade>\n")
               .append("    <localizacao>").append(c.getLocalizacao()).append("</localizacao>\n")
               .append("    <categoria>").append(c.getCategoria()).append("</categoria>\n")
               .append("    <banca>").append(c.getBanca()).append("</banca>\n")
               .append("    <descricao>").append(c.getDescricao()).append("</descricao>\n")
               .append("    <orgao>").append(c.getOrgao()).append("</orgao>\n")
               .append("    <cargo>").append(c.getCargo()).append("</cargo>\n")
               //.append("    <linkEdital>").append(c.getEditalID()).append("</linkEdital>\n")
               .append("    <materiaisDeEstudo>").append(c.getMateriaisDeEstudo()).append("</materiaisDeEstudo>\n")
               .append("    <horario>").append(c.getHorario()).append("</horario>\n")
               .append("    <status>").append(c.getStatus().length() > 0 ? "Ativo" : "Inativo").append("</status>\n")
               .append("    <inicioIncricoes>").append(c.getInicioIncricoes()).append("</inicioIncricoes>\n")
               .append("    <terminoIncricoes>").append(c.getTerminoIncricoes()).append("</terminoIncricoes>\n")
               .append("  </concurso>\n");
        }
        xml.append("</concursos>");
        return xml.toString();
    }

    // GET /concursos/:id
    private Object getConcursoById(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            Concurso c = concursoDAO.getConcurso(id);
            if (c == null) {
                res.status(404);
                return "Concurso não encontrado";
            }
            res.type("application/xml");
            return "<concurso>" +
                   "<id>" + c.getID() + "</id>" +
                   "<nome>" + c.getNome() + "</nome>" +
                   "<escolaridade>" + c.getEscolaridade() + "</escolaridade>" +
                   "<localizacao>" + c.getLocalizacao() + "</localizacao>" +
                   "<categoria>" + c.getCategoria() + "</categoria>" +
                   "<banca>" + c.getBanca() + "</banca>" +
                   "<descricao>" + c.getDescricao() + "</descricao>" +
                   "<orgao>" + c.getOrgao() + "</orgao>" +
                   "<cargo>" + c.getCargo() + "</cargo>" +
                   //"<linkEdital>" + c.getEditalID() + "</linkEdital>" +
                   "<materiaisDeEstudo>" + c.getMateriaisDeEstudo() + "</materiaisDeEstudo>" +
                   "<horario>" + c.getHorario() + "</horario>" +
                   "<status>" + (c.getStatus().length() > 0 ? "Ativo" : "Inativo") + "</status>" +
                   "<inicioIncricoes>" + c.getInicioIncricoes() + "</inicioIncricoes>" +
                   "<terminoIncricoes>" + c.getTerminoIncricoes() + "</terminoIncricoes>" +
                   "</concurso>";
        } catch (NumberFormatException e) {
            res.status(400);
            return "ID inválido";
        }
    }

    // PUT /concursos/:id
    private Object updateConcurso(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            Concurso existing = concursoDAO.getConcurso(id);
            if (existing == null) {
                res.status(404);
                return "Concurso não encontrado";
            }
            // campos podem vir vazios
            String nome = req.queryParams("nome");
            String escolaridade = req.queryParams("escolaridade");
            String localizacao = req.queryParams("localizacao");
            String categoria = req.queryParams("categoria");
            String banca = req.queryParams("banca");
            String descricao = req.queryParams("descricao");
            String orgao = req.queryParams("orgao");
            String cargo = req.queryParams("cargo");
            String editalIDS = req.queryParams("editalID");
            String materiais = req.queryParams("materiaisDeEstudo");
            String horario = req.queryParams("horario");
            String statusS = req.queryParams("status");
            String inicioS = req.queryParams("inicioIncricoes");
            String terminoS = req.queryParams("terminoIncricoes");

            if (nome != null && !nome.isEmpty()) existing.setNome(nome);
            if (escolaridade != null && !escolaridade.isEmpty()) existing.setEscolaridade(escolaridade);
            if (localizacao != null && !localizacao.isEmpty()) existing.setLocalizacao(localizacao);
            if (categoria != null && !categoria.isEmpty()) existing.setCategoria(categoria);
            if (banca != null && !banca.isEmpty()) existing.setBanca(banca);
            if (descricao != null && !descricao.isEmpty()) existing.setDescricao(descricao);
            if (orgao != null && !orgao.isEmpty()) existing.setOrgao(orgao);
            if (cargo != null && !cargo.isEmpty()) existing.setCargo(cargo);
            if (editalIDS != null && !editalIDS.isEmpty()) existing.setEditalID(Integer.parseInt(editalIDS));
            if (materiais != null && !materiais.isEmpty()) existing.setMateriaisDeEstudo(materiais);
            if (horario != null && !horario.isEmpty()) existing.setHorario(horario);
            if (statusS != null && !statusS.isEmpty()) existing.setStatus((statusS));
            if (inicioS != null && !inicioS.isEmpty()) existing.setInicioIncricoes(formato.parse(inicioS));
            if (terminoS != null && !terminoS.isEmpty()) existing.setTerminoIncricoes(formato.parse(terminoS));

            if (concursoDAO.update(existing)) {
                res.redirect("/cadastrodeconcurso.html");
                return "";
            } else {
                res.status(500);
                return "Erro ao atualizar concurso";
            }
        } catch (NumberFormatException | ParseException e) {
            res.status(400);
            return "Erro de formato: " + e.getMessage();
        }
    }

    // DELETE /concursos/:id
    private Object deleteConcurso(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params("id"));
            if (concursoDAO.delete(id)) {
                res.redirect("/cadastrodeconcurso.html");
                return "";
            } else {
                res.status(404);
                return "Erro ao deletar concurso";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "ID inválido";
        }
    }

    // Adicione estes métodos públicos para acesso externo
    public Object update(Request req, Response res) {
        return updateConcurso(req, res);
    }

    public Object delete(Request req, Response res) {
        return deleteConcurso(req, res);
    }
}
