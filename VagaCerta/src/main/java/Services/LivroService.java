package Services;

import static spark.Spark.*;

import java.util.List;

import dao.LivroDAO;
import Classes.Livro;
import spark.Request;
import spark.Response;

public class LivroService {
    private LivroDAO dao = new LivroDAO();

    public LivroService() {
        path("/", () -> {
            // CREATE via form HTML (url-encoded)
            post("livros", this::createLivro);

            // READ ALL / READ ONE
            get("livros", this::getAllLivros);
            get("livros/:id", this::getLivroById);

            // UPDATE via form HTML (override _method=PUT)
            put("livros/:id", this::updateLivro);

            // DELETE via form HTML (override _method=DELETE)
            delete("livros/:id", this::deleteLivro);
        });
    }

    // POST /livros
    private Object createLivro(Request req, Response res) {
        // lê campos do form
        String titulo = req.queryParams("titulo");
        String autor = req.queryParams("autor");
        int versao = Integer.parseInt(req.queryParams("versao"));
        String materia = req.queryParams("materia");
        String link = req.queryParams("link");

        // gera objeto e persiste
        Livro l = new Livro(0, titulo, autor, versao, materia, link);
        boolean ok = dao.inserir(l);

        // após criar, redireciona de volta à página
        if (ok) {
            res.redirect("/biblioteca.html");
        } else {
            res.status(500);
            return "Erro ao cadastrar livro";
        }
        return "";
    }

    public Object getAllLivros(Request req, Response res) {
        List<Livro> lista = dao.listar();
        res.type("application/json");
        return lista;
    }
    
    public Object getLivroById(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        Livro l = dao.get(id);
        if (l == null) {
            res.status(404);
            return "Livro não encontrado";
        }
        res.type("application/json");
        return l;
    }

    // PUT /livros/:id
    public Object updateLivro(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        Livro existing = dao.get(id);
        if (existing == null) {
            res.status(404);
            return "Livro não encontrado";
        }

        // lê campos (se vierem vazios, mantém o original)
        String titulo   = req.queryParams("titulo");
        String autor    = req.queryParams("autor");
        String versaoS  = req.queryParams("versao");
        String materia  = req.queryParams("materia");
        String link     = req.queryParams("link");

        if (titulo  != null && !titulo.isEmpty())  existing.setTitulo(titulo);
        if (autor   != null && !autor.isEmpty())   existing.setAutor(autor);
        if (versaoS != null && !versaoS.isEmpty()) existing.setVersao(Integer.parseInt(versaoS));
        if (materia != null && !materia.isEmpty()) existing.setMateria(materia);
        if (link    != null && !link.isEmpty())    existing.setLink(link);

        boolean ok = dao.update(existing);
        if (ok) {
            res.redirect("/biblioteca.html");
        } else {
            res.status(500);
            return "Erro ao atualizar livro";
        }
        return "";
    }

    // DELETE /livros/:id
    public Object deleteLivro(Request req, Response res) {
        int id = Integer.parseInt(req.params("id"));
        boolean ok = dao.delete(id);
        if (ok) {
            res.redirect("/biblioteca.html");
        } else {
            res.status(404);
            return "Erro ao deletar livro";
        }
        return "";
    }
}