package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;
import br.com.skyzero.model.vo.Registro;
import br.com.skyzero.model.vo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RegistroDAO {
    private Connection conn;

    public RegistroDAO() {
        this.conn = new Conexao().conexao();
    }

    public int inserir(Registro registro) throws SQLException {
        String sql = "INSERT INTO tb_registro (id_usuario, id_registro, tipo_aviao, distancia, data_registro) VALUES (?, tb_registro_id_registro_seq.NEXTVAL, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, registro.getUsuario().getId());
            stmt.setString(2, registro.getTipoAviao());
            stmt.setDouble(3, registro.getDistancia());
            stmt.setDate(4, new java.sql.Date(registro.getDataRegistro().getTime()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o registro.");
                }
            }
        }
    }

    public void atualizar(int idRegistro, double emissaoCalculada) throws SQLException {
        String sql = "UPDATE tb_registro SET emissao_calculada = ? WHERE id_registro = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, emissaoCalculada);
            stmt.setInt(2, idRegistro);
            stmt.executeUpdate();
        }
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
                        rs.getDouble("distancia"),
                        rs.getDouble("emissao_calculada"),
                        rs.getDate("data_registro")
                );
                registros.add(registro);
            }
            return registros;
        }
    }
}
