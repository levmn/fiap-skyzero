package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Usuario> listar() throws SQLException {
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tb_usuario");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id_usuario"));
            usuario.setNomeEmpresa(rs.getString("nome_empresa"));
            usuario.setEmail(rs.getString("email"));
            usuario.setCnpj(rs.getString("cnpj"));
            listaUsuario.add(usuario);
        }
        return listaUsuario;
    }

}
