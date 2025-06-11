package dao;

import Classes.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends DAO {
    public boolean inserir(Livro l) {
        String sql = "INSERT INTO livro (titulo, autor, versao, materia, link) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setInt(3, l.getVersao());
            ps.setString(4, l.getMateria());
            ps.setString(5, l.getLink());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) l.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Livro> listar() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Statement st = conexao.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"),
                                    rs.getInt("versao"), rs.getString("materia"), rs.getString("link")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Livro get(int id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Livro(id, rs.getString("titulo"), rs.getString("autor"),
                                     rs.getInt("versao"), rs.getString("materia"), rs.getString("link"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Livro l) {
        String sql = "UPDATE livro SET titulo=?, autor=?, versao=?, materia=?, link=? WHERE id=?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setInt(3, l.getVersao());
            ps.setString(4, l.getMateria());
            ps.setString(5, l.getLink());
            ps.setInt(6, l.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}