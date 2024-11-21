package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Registro;
import br.com.skyzero.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegistroDAO {
    private Connection conn;

    public RegistroDAO() {
        this.conn = new Conexao().conexao();
    }

    public Registro inserir(Registro registro) throws SQLException {
        String sqlRegistro = "INSERT INTO tb_registro (id_registro, id_usuario, tipo_aviao, distancia, data_registro) " +
                "VALUES (tb_registro_id_registro_seq.NEXTVAL, ?, ?, ?, ?)";

        System.out.println("SQL Executado: " + sqlRegistro);
        System.out.println("Parâmetros: id_usuario=" + registro.getUsuario().getId() +
                ", tipo_aviao=" + registro.getTipoAviao() +
                ", distancia=" + registro.getDistancia() +
                ", data_registro=" + registro.getDataRegistro());

        try (PreparedStatement stmtRegistro = conn.prepareStatement(sqlRegistro)) {
            stmtRegistro.setInt(1, registro.getUsuario().getId());
            stmtRegistro.setString(2, registro.getTipoAviao());
            stmtRegistro.setInt(3, registro.getDistancia());
            stmtRegistro.setDate(4, new java.sql.Date(registro.getDataRegistro().getTime()));

            stmtRegistro.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao tentar registrar emissão: " + e);
        }
        return registro;
    }

    public void atualizar(int idRegistro, double emissaoCalculada) throws SQLException {
        String sql = "UPDATE tb_registro SET emissao_calculada = ? WHERE id_registro = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, emissaoCalculada);
            stmt.setInt(2, idRegistro);
            stmt.executeUpdate();
        }
    }

    public Registro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tb_registro WHERE id_registro = ?";
        Registro registro = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                registro = new Registro();
                registro.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao encontrar registro pelo id: " + id, e);
        }

        return registro;
    }

    public List<Registro> listar() throws SQLException {
        String sql = "SELECT * FROM tb_registro";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Registro> registros = new ArrayList<>();
            while (rs.next()) {
                Registro registro = new Registro(
                        rs.getInt("id_registro"),
                        new Usuario(rs.getInt("id_usuario")),
                        rs.getString("tipo_aviao"),
                        rs.getInt("distancia"),
                        rs.getDouble("emissao_calculada"),
                        rs.getDate("data_registro")
                );
                registros.add(registro);
            }
            return registros;
        }
    }

    public List<Registro> listarPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM tb_registro WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Registro> registros = new ArrayList<>();
                while (rs.next()) {
                    Registro registro = new Registro(
                            rs.getInt("id_registro"),
                            new Usuario(rs.getInt("id_usuario")),
                            rs.getString("tipo_aviao"),
                            rs.getInt("distancia"),
                            rs.getDouble("emissao_calculada"),
                            rs.getDate("data_registro")
                    );
                    registros.add(registro);
                }
                return registros;
            }
        }
    }

    public String deletar(int id) throws SQLException {
        String sql = "DELETE FROM tb_registro WHERE id_registro = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();

        return "Registro removido com sucesso!";
    }
}
