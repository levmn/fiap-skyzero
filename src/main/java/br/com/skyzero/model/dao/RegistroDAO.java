package br.com.skyzero.model.dao;

import java.sql.Connection;


public class RegistroDAO {
    private Connection connection;

    public RegistroDAO(Connection connection) {
        this.connection = connection;
    }
}
