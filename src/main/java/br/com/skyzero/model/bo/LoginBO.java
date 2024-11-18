package br.com.skyzero.model.bo;

import br.com.skyzero.model.dao.LoginDAO;
import br.com.skyzero.model.vo.Login;
import br.com.skyzero.model.vo.Usuario;

import java.sql.SQLException;

public class LoginBO {
    private LoginDAO loginDAO;

    public LoginBO() {
        this.loginDAO = new LoginDAO();
    }

    public Login autenticar(String cnpj, String senha) throws SQLException, IllegalArgumentException {
        validarCredenciais(cnpj, senha);

        Login usuarioAutenticado = loginDAO.autenticar(cnpj, senha);
        if (usuarioAutenticado == null) {
            throw new IllegalArgumentException("CNPJ ou senha inválidos.");
        }
        return usuarioAutenticado;
    }

    public String inserirLogin(Usuario usuario, String senha) throws SQLException {
        validarCredenciais(usuario.getCnpj(), senha);

        Login novoLogin = new Login(usuario, usuario.getCnpj(), senha);

        return loginDAO.inserir(usuario, novoLogin);
    }

    private void validarCredenciais(String login, String senha) {
        if (!isValidCNPJ(login)) {
            System.out.println("Validação de CNPJ falhou.");
            throw new IllegalArgumentException("O campo de login deve ser um CNPJ válido com 14 dígitos.");
        }

        if (!isValidSenha(senha)) {
            System.out.println("Validação de senha falhou.");
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
