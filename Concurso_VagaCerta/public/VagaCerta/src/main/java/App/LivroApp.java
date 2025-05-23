package App;

import Services.LivroService;
import static spark.Spark.*;

public class LivroApp {
    public static void main(String[] args) {
        // Porta
        port(3000);

        // Serve HTML/CSS/JS de src/main/resources/public
        arquivos = staticFiles.location("src/main/resources/ti-1-ppl-cc-m-20242-g7-concursos-master/ti-1-ppl-cc-m-20242-g7-concursos-master/codigo/public/biblioteca.html");

        System.out.println();

        // Habilita POST “especiais” para atualizar e deletar via formulário HTML
        // ao invés de usar antes((req,res)->req.requestMethod(...))
        // definimos endpoints POST para aqueles casos:
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

        // Inicia rotas REST “normais”
        new LivroService();

    }
}