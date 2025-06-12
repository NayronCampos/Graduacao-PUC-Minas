package Services;

import static spark.Spark.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import dao.UsuarioDAO;
import Classes.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	public UsuarioService() {
        path("/", () -> {
            // CREATE via form HTML (url-encoded)
            post("usuario", this::insert);

            // READ ALL / READ ONE
            get("usuario/:cpf", this::getByCpf);

            // UPDATE via form HTML (override _method=PUT)
            put("usuario/:cpf", this::update);

            // DELETE via form HTML (override _method=DELETE)
            delete("usuario/:cpf", this::delete);
        });
    }
	
	private Object insert(Request req, Response res) {
        // lê campos do form
		String cpf = req.queryParams("cpf");
        String nome = req.queryParams("nome");
        String escolaridade = req.queryParams("escolaridade");
        String senha = req.queryParams("senha");
        String email = req.queryParams("email");

        // gera objeto e persiste
        Usuario u = new Usuario(cpf, nome, escolaridade, senha, email);
        boolean ok = usuarioDAO.inserirUsuario(u);

        // após criar, redireciona de volta à página
        if (!ok) {
        	res.status(500);
            return "Erro ao cadastrar usuario.";
        }
        return "";
    }
	
    public Object getByCpf(Request req, Response res) {
        try {
            String cpf = req.params("cpf");
            Usuario u = usuarioDAO.getUsuarioByCpf(cpf);
            if (u == null) {
                res.status(404);
                return "Usuario não encontrado.";
            }
            res.type("application/json");
            return new com.google.gson.Gson().toJson(u);
        } catch (NumberFormatException ex) {
            res.status(400);
            return "CPF inválido.";
        }
    }
    
    public Object update(Request req, Response res) {
        String cpf = req.params("cpf");
		Usuario u = usuarioDAO.getUsuarioByCpf(cpf);
		if (u == null) {
		    res.status(404);
		    return "Usuario não encontrado.";
		}
		
		u.setNome(req.queryParams("nome"));
		u.setEscolaridade(req.queryParams("escolaridade"));
		u.setSenha(req.queryParams("senha"));
		u.setEmail(req.queryParams("email"));

		boolean ok = usuarioDAO.atualizarUsuario(u);
		if (ok) {
		    res.status(200);
		    return "Usuario atualizado.";
		} else {
		    res.status(500);
		    return "Erro ao atualizar usuario.";
		}
    }
    
    public Object delete(Request req, Response res) {
        try {
            String cpf = req.params("cpf");
            boolean ok = usuarioDAO.excluirUsuario(cpf);
            if (ok) {
                res.status(200);
                return "Usuario deletado.";
            } else {
                res.status(404);
                return "Erro ao deletar usuario.";
            }
        } catch (NumberFormatException ne) {
            res.status(400);
            return "CPF inválido.";
        }
    }
}
