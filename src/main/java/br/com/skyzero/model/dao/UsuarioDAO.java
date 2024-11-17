package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        this.conn = new Conexao().conexao();
    }

    public String inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO tb_usuario VALUES (tb_usuario_id_usuario_seq.NEXTVAL, ?, ?, ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario.getNomeEmpresa());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getCnpj());
        stmt.execute();
        stmt.close();

        return "Usuário cadastrado com sucesso!";
    }
}
