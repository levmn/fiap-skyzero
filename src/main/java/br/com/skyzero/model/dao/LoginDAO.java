package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Login;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private Connection conn;

    public LoginDAO() {
        this.conn = new Conexao().conexao();
    }

    public String inserir(Usuario usuario, Login login) throws SQLException {
        String sql = "INSERT INTO tb_login (id_usuario, login, senha) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, login.getLogin());
            stmt.setString(3, login.getSenha());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao inserir login: " + e.getMessage());
        }

        return "Login realizado com sucesso.";
    }

    public Login autenticar(String cnpj, String senha) throws SQLException {
        String sql = "SELECT l.senha, u.id_usuario, u.nome_empresa, u.email, u.cnpj " +
                "FROM tb_login l " +
                "JOIN tb_usuario u ON l.id_usuario = u.id_usuario " +
                "WHERE u.cnpj = ? AND l.senha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNomeEmpresa(rs.getString("nome_empresa"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCnpj(rs.getString("cnpj"));

                    return new Login(usuario, cnpj, senha);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao autenticar usuário: " + e.getMessage());
        }

        return null;
    }
}
