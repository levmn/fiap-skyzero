package br.com.skyzero.model.dao;

import br.com.skyzero.model.vo.Registro;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {

    private Connection connection;

    public RegistroDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Registro registro) {
        String sql = "INSERT INTO tb_registro (id_usuario, tipo_aviao, distancia, emissao_calculada, data_registro) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, registro.getUsuario().getId());
            stmt.setString(2, registro.getTipoAviao());
            stmt.setDouble(3, registro.getDistancia());
            stmt.setDouble(4, registro.getEmissaoCalculada());
            stmt.setDate(5, new java.sql.Date(registro.getDataRegistro().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Registro buscarPorId(int id) {
        String sql = "SELECT r.id_registro, r.id_usuario, r.tipo_aviao, r.distancia, r.emissao_calculada, r.data_registro, " +
                "u.nome_empresa, u.email, u.cnpj " +
                "FROM tb_registro r " +
                "JOIN tb_usuario u ON r.id_usuario = u.id_usuario " +
                "WHERE r.id_registro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nome_empresa"),
                            rs.getString("email"),
                            rs.getString("cnpj")
                    );
                    return new Registro(
                            rs.getInt("id_registro"),
                            usuario,
                            rs.getString("tipo_aviao"),
                            rs.getDouble("distancia"),
                            rs.getDouble("emissao_calculada"),
                            rs.getDate("data_registro")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Registro> listar() {
        String sql = "SELECT r.id_registro, r.id_usuario, r.tipo_aviao, r.distancia, r.emissao_calculada, r.data_registro, " +
                "u.nome_empresa, u.email, u.cnpj " +
                "FROM tb_registro r " +
                "JOIN tb_usuario u ON r.id_usuario = u.id_usuario";
        List<Registro> registros = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome_empresa"),
                        rs.getString("email"),
                        rs.getString("cnpj")
                );
                Registro registro = new Registro(
                        rs.getInt("id_registro"),
                        usuario,
                        rs.getString("tipo_aviao"),
                        rs.getDouble("distancia"),
                        rs.getDouble("emissao_calculada"),
                        rs.getDate("data_registro")
                );
                registros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public boolean atualizar(Registro registro) {
        String sql = "UPDATE tb_registro SET tipo_aviao = ?, distancia = ?, data_registro = ? " +
                "WHERE id_registro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, registro.getTipoAviao());
            stmt.setDouble(2, registro.getDistancia());
            stmt.setDate(3, new java.sql.Date(registro.getDataRegistro().getTime()));
            stmt.setInt(4, registro.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM tb_registro WHERE id_registro = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
