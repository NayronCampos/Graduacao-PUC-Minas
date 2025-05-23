package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Concurso;

public class ConcursoDAO extends DAO {
    public ConcursoDAO() {
        super();
        conectar();
    }

    public boolean insert(Concurso concurso) {
        boolean status = false;
        String sql = "INSERT INTO concurso (" +
                     "id_concurso, nome, escolaridade, localizacao, categoria, banca, descricao, orgao, cargo, materiaisDeEstudo, horario, status, data_inscricao, data_termino) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, concurso.getID());
            st.setString(2, concurso.getNome());
            st.setString(3, concurso.getEscolaridade());
            st.setString(4, concurso.getLocalizacao());
            st.setString(5, concurso.getCategoria());
            st.setString(6, concurso.getBanca());
            st.setString(7, concurso.getDescricao());
            st.setString(8, concurso.getOrgao());
            st.setString(9, concurso.getCargo());
            st.setString(10, concurso.getMateriaisDeEstudo());
            st.setString(11, concurso.getHorario());
            st.setString(12, concurso.getStatus());
            // converte java.util.Date para java.sql.Date (formato dd/MM/yyyy já parseado na Service)
            st.setDate(13, new java.sql.Date(concurso.getInicioIncricoes().getTime()));
            st.setDate(14, new java.sql.Date(concurso.getTerminoIncricoes().getTime()));

            st.executeUpdate();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean update(Concurso concurso) {
        boolean status = false;
        String sql = "UPDATE concurso SET " +
                     "nome = ?, escolaridade = ?, localizacao = ?, categoria = ?, banca = ?, descricao = ?, orgao = ?, cargo = ?, materiaisDeEstudo = ?, horario = ?, status = ?, data_inscricao = ?, data_termino = ? " +
                     "WHERE id_concurso = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setString(1, concurso.getNome());
            st.setString(2, concurso.getEscolaridade());
            st.setString(3, concurso.getLocalizacao());
            st.setString(4, concurso.getCategoria());
            st.setString(5, concurso.getBanca());
            st.setString(6, concurso.getDescricao());
            st.setString(7, concurso.getOrgao());
            st.setString(8, concurso.getCargo());
            st.setString(9, concurso.getMateriaisDeEstudo());
            st.setString(10, concurso.getHorario());
            st.setString(11, concurso.getStatus());
            st.setDate(12, new java.sql.Date(concurso.getInicioIncricoes().getTime()));
            st.setDate(13, new java.sql.Date(concurso.getTerminoIncricoes().getTime()));
            st.setInt(14, concurso.getID());

            st.executeUpdate();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public Concurso getConcurso(int id) {
        Concurso concurso = null;
        String sql = "SELECT * FROM concurso WHERE id_concurso = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    concurso = new Concurso(
                        rs.getInt("id_concurso"),
                        rs.getString("nome"),
                        rs.getString("escolaridade"),
                        rs.getString("localizacao"),
                        rs.getString("categoria"),
                        rs.getString("banca"),
                        rs.getString("descricao"),
                        rs.getString("orgao"),
                        rs.getString("cargo"),
                        rs.getString("materiaisDeEstudo"),
                        rs.getString("horario"),
                        rs.getString("status"),
                        rs.getDate("data_inscricao"),
                        rs.getDate("data_termino")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return concurso;
    }

    public List<Concurso> getAllConcursos() {
        List<Concurso> concursos = new ArrayList<>();
        String sql = "SELECT * FROM concurso";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Concurso concurso = new Concurso(
                    rs.getInt("id_concurso"),
                    rs.getString("nome"),
                    rs.getString("escolaridade"),
                    rs.getString("localizacao"),
                    rs.getString("categoria"),
                    rs.getString("banca"),
                    rs.getString("descricao"),
                    rs.getString("orgao"),
                    rs.getString("cargo"),
                    rs.getString("materiaisDeEstudo"),
                    rs.getString("horario"),
                    rs.getString("status"),
                    rs.getDate("data_inscricao"),
                    rs.getDate("data_termino")
                );
                concursos.add(concurso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return concursos;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM concurso WHERE id_concurso = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int geradorDeId() {
        List<Concurso> concursos = getAllConcursos();
        int maxId = 0;
        if (concursos != null) {
            for (Concurso c : concursos) {
                if (c.getID() > maxId) {
                    maxId = c.getID();
                }
            }
        }
        return maxId + 1;
    }
}
