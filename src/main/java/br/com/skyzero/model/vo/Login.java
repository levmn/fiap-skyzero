package br.com.skyzero.model.vo;

public class Login {
    private Usuario usuario;
    private String login;
    private String senha;

    public Login(Usuario usuario, String login, String senha) {
        this.usuario = usuario;
        this.login = login;
        this.senha = senha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Login{" +
                "usuario=" + usuario +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
