package br.com.vagacerta.backend;

import static spark.Spark.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import br.com.vagacerta.backend.dao.QuestaoDAO;

public class Main {
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(4567);

        get("/", (req, res) -> {
            res.redirect("/formulario");
            return null;
        });

        get("/formulario", (req, res) -> {
            res.redirect("/form.html");
            return null;
        });

        post("/questao/cadastrar", (req, res) -> {
            String enunciado = req.queryParams("enunciado");
            String a = req.queryParams("a");
            String b = req.queryParams("b");
            String c = req.queryParams("c");
            String d = req.queryParams("d");
            String resposta = req.queryParams("resposta");
            int concursoId = Integer.parseInt(req.queryParams("concurso_id"));
            String alternativas = "A) " + a + " B) " + b + " C) " + c + " D) " + d;

            boolean sucesso = QuestaoDAO.cadastrar(enunciado, a, b, c, d, resposta, concursoId);
            return sucesso ? "Questão cadastrada com sucesso!" : "Erro ao cadastrar questão.";
        });

        get("/questoes", (req, res) -> {
            List<String> questoes = QuestaoDAO.listar();
            StringBuilder listaHtml = new StringBuilder();
            for (String q : questoes) {
                String id = q.split(" ")[1];
                listaHtml.append("<li>").append(q).append(" ")
                         .append("<a href='/questao/editar/").append(id).append("'>Editar</a> ")
                         .append("<a href='/questao/confirmar-exclusao/").append(id).append("'>Excluir</a></li>");
            }
            String template = new String(Files.readAllBytes(Paths.get("src/main/resources/public/listar.html")), StandardCharsets.UTF_8);
            return template.replace("{{lista}}", listaHtml.toString());
        });

        get("/questao/editar/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Map<String, String> questao = QuestaoDAO.buscarPorId(id);
            String html = new String(Files.readAllBytes(Paths.get("src/main/resources/public/editar.html")), StandardCharsets.UTF_8);
            html = html.replace("{{id}}", String.valueOf(id));
            html = html.replace("{{concurso_id}}", questao.get("concurso_id"));
            html = html.replace("{{enunciado}}", questao.get("enunciado"));
            html = html.replace("{{alternativas}}", questao.get("alternativas"));
            html = html.replace("{{resposta}}", questao.get("resposta"));
            return html;
        });

        post("/questao/atualizar/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            String enunciado = req.queryParams("enunciado");
            String alternativas = req.queryParams("alternativas");
            String resposta = req.queryParams("resposta");
            int concursoId = Integer.parseInt(req.queryParams("concurso_id"));
            QuestaoDAO.atualizar(id, enunciado, alternativas, resposta, concursoId);
            res.redirect("/questoes");
            return null;
        });

        get("/questao/confirmar-exclusao/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Map<String, String> questao = QuestaoDAO.buscarPorId(id);
            String html = new String(Files.readAllBytes(Paths.get("src/main/resources/public/confirmar-exclusao.html")), StandardCharsets.UTF_8);
            html = html.replace("{{id}}", String.valueOf(id));
            html = html.replace("{{enunciado}}", questao.get("enunciado"));
            html = html.replace("{{alternativas}}", questao.get("alternativas"));
            html = html.replace("{{resposta}}", questao.get("resposta"));
            return html;
        });

        post("/questao/excluir/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            QuestaoDAO.excluir(id);
            res.redirect("/questoes");
            return null;
        });
    }
}

