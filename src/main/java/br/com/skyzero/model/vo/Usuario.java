package br.com.skyzero.model.vo;

public class Usuario {
    private int id;
    private String nomeEmpresa;
    private String email;
    private String cnpj;

    public Usuario() {
    }

    public Usuario(int id, String nomeEmpresa, String email, String cnpj) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.email = email;
        this.cnpj = cnpj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}