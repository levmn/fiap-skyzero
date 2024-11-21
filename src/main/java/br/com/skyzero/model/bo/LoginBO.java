package br.com.skyzero.model.bo;

import br.com.skyzero.model.dao.LoginDAO;

import java.sql.SQLException;
import java.util.Map;

public class LoginBO {
    private LoginDAO loginDAO;

    public LoginBO() {
        this.loginDAO = new LoginDAO();
    }

    public Map<String, Object> autenticar(String cnpj, String senha) throws SQLException {
        validarCredenciais(cnpj, senha);

        Map<String, Object> loginAutenticado = loginDAO.autenticar(cnpj, senha);
        if (loginAutenticado == null) {
            throw new IllegalArgumentException("CNPJ ou senha inválidos.");
        }
        return loginAutenticado;
    }

    private void validarCredenciais(String cnpj, String senha) {
        if (!isValidCNPJ(cnpj)) {
            throw new IllegalArgumentException("O CNPJ fornecido é inválido. Deve conter exatamente 14 dígitos.");
        }

        if (!isValidSenha(senha)) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
    }

    private boolean isValidCNPJ(String cnpj) {
        String cnpjRegex = "\\d{14}";
        return cnpj != null && cnpj.matches(cnpjRegex);
    }

    private boolean isValidSenha(String senha) {
        return senha != null && senha.length() >= 8;
    }
}
