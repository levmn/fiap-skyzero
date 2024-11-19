package br.com.skyzero.model.vo;

public class Login {
    private String cnpj;
    private String senha;

    public Login() {
    }

    public Login(String cnpj, String senha) {
        this.cnpj = cnpj;
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
                "cnpj='" + cnpj + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
