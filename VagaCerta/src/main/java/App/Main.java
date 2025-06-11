package App;

import static spark.Spark.*;

import Services.ConcursoService;
import Services.LivroService;
import dao.DAO;

public class Main {
    public static void main(String[] args) {
        // Porta
        port(8086);

        // Serve HTML/CSS/JS de src/main/resources/public
        staticFiles.externalLocation("src/main/resources/ti-1-ppl-cc-m-20242-g7-concursos-master/" + "ti-1-ppl-cc-m-20242-g7-concursos-master/codigo/public");

        
        DAO dao = new DAO();
        if (!dao.conectar()) {
            System.err.println("Falha ao conectar ao BD");
            System.exit(1);
        }

        // Registra rotas de Concurso
        post("/concursos/:id", (req, res) -> {
            String method = req.queryParams("_method");
            if ("PUT".equalsIgnoreCase(method)) {
                return new ConcursoService().update(req, res);
            } else if ("DELETE".equalsIgnoreCase(method)) {
                return new ConcursoService().delete(req, res);
            } else {
                halt(405, "Método não permitido");
                return null;
            }
        });
        new ConcursoService();
        System.out.println("ConcursoService iniciado!");

        // Registra rotas de Livro
        post("/livros/:id", (req, res) -> {
            String method = req.queryParams("_method");
            if ("PUT".equalsIgnoreCase(method)) {
                return new LivroService().updateLivro(req, res);
            } else if ("DELETE".equalsIgnoreCase(method)) {
                return new LivroService().deleteLivro(req, res);
            } else {
                halt(405, "Método não permitido");
                return null;
            }
        });
        new LivroService();
        System.out.println("LivroService iniciado!");

        // Aqui, a aplicação fica escutando todas as rotas configuradas
        System.out.println("Main rodando na porta 8086 com todas as rotas.");

    }
}