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
        // Registra rotas de Concurso
        new ConcursoService();
        System.out.println("ConcursoService iniciado!");

        // Registra rotas de Livro
        new LivroService();
        System.out.println("LivroService iniciado!");

        // Aqui, a aplicação fica escutando todas as rotas configuradas
        System.out.println("Main rodando na porta 8086 com todas as rotas.");

    }
}