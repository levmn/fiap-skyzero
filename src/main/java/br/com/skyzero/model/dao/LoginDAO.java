package br.com.skyzero.model.dao;

import br.com.skyzero.connection.Conexao;

import java.sql.Connection;

public class LoginDAO {
    private Connection conn;

    public LoginDAO() {
        this.conn = new Conexao().conexao();
    }


}
