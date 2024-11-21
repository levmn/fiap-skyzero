package br.com.skyzero.model.bo;

import br.com.skyzero.model.dao.UsuarioDAO;
import br.com.skyzero.model.vo.Login;
import br.com.skyzero.model.vo.Usuario;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public String cadastrarUsuario(Usuario usuario) throws SQLException {
        validarUsuario(usuario);

        Login login = usuario.getLogin();
        if (!usuario.getCnpj().equals(login.getCnpj())) {
            throw new IllegalArgumentException("O CNPJ do login não corresponde ao CNPJ da empresa.");
        }

        return usuarioDAO.inserir(usuario);
    }

    public Usuario buscarUsuario(int id) throws SQLException {
        return usuarioDAO.buscar(id);
    }

    private void validarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        validarCNPJ(usuario.getCnpj());
        validarNomeEmpresa(usuario.getNomeEmpresa());
    }

    private void validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }

        String emailRegex = "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
    }

    private void validarCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            throw new IllegalArgumentException("O CNPJ não pode ser vazio.");
        }

        String cnpjNumerico = cnpj.replaceAll("[^0-9]", "");

        if (cnpjNumerico.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve ter 14 dígitos.");
        }

        if (!isCNPJValido(cnpjNumerico)) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }
    }

    private boolean isCNPJValido(String cnpj) {
        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += (cnpj.charAt(i) - '0') * peso1[i];
            }

            int digito1 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
            if (digito1 != (cnpj.charAt(12) - '0')) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += (cnpj.charAt(i) - '0') * peso2[i];
            }

            int digito2 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
            return digito2 == (cnpj.charAt(13) - '0');
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void validarNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa == null || nomeEmpresa.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da empresa não pode ser vazio.");
        }

        if (nomeEmpresa.length() > 255) {
            throw new IllegalArgumentException("O nome da empresa não pode exceder 255 caracteres.");
        }
    }
}
