package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        this.conn = new Conexao().conexao();
    }

    public String inserir(Usuario usuario) throws SQLException {
        String sqlUsuario = "INSERT INTO tb_usuario (id_usuario, nome_empresa, email, cnpj) " +
                "VALUES (tb_usuario_id_usuario_seq.NEXTVAL, ?, ?, ?)";
        String sqlLogin = "INSERT INTO tb_login (id_usuario, senha) " +
                "VALUES (tb_usuario_id_usuario_seq.CURRVAL, ?)";

        try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
             PreparedStatement stmtLogin = conn.prepareStatement(sqlLogin)) {

            stmtUsuario.setString(1, usuario.getNomeEmpresa());
            stmtUsuario.setString(2, usuario.getEmail());
            stmtUsuario.setString(3, usuario.getCnpj());
            stmtUsuario.executeUpdate();

            stmtLogin.setString(1, usuario.getLogin().getSenha());
            stmtLogin.executeUpdate();

            return "Usuário e login cadastrados com sucesso!";
        }
    }

    public Usuario buscar(int id) throws SQLException {
        String sql = "SELECT * FROM tb_usuario WHERE id_usuario = ?";
        Usuario usuario = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setNomeEmpresa(rs.getString(2));
                usuario.setEmail(rs.getString(3));
                usuario.setCnpj(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar o usuário pelo id: " + id, e);
        }

        return usuario;
    }
}