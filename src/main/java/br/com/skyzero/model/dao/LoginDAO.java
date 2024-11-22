package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginDAO {
    private Connection conn;

    public LoginDAO() {
        this.conn = new Conexao().conexao();
    }

    public Map<String, Object> autenticar(String cnpj, String senha) throws SQLException {
        String sql = "SELECT u.id_usuario, u.cnpj, l.senha " +
                "FROM tb_usuario u " +
                "JOIN tb_login l ON u.id_usuario = l.id_usuario " +
                "WHERE u.cnpj = ? AND l.senha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id_usuario", rs.getInt("id_usuario"));
                    result.put("cnpj", rs.getString("cnpj"));
                    return result;
                }
            }
        }
        return null;
    }

    public boolean atualizarSenha(String cnpj, String novaSenha) throws SQLException {
        String sql = "UPDATE tb_login " +
                "SET senha = ? " +
                "WHERE id_usuario = (SELECT id_usuario FROM tb_usuario WHERE cnpj = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, cnpj);
            return stmt.executeUpdate() > 0;
        }
    }
}
