package br.com.skyzero.model.vo;

import java.util.Date;

public class Registro {
    private int id;
    private String tipoAviao;
    private double distancia;
    private double emissaoCalculada;
    private Date dataRegistro;

    public Registro(int id, String tipoAviao, double distancia, double emissaoCalculada, Date dataRegistro) {
        this.id = id;
        this.tipoAviao = tipoAviao;
        this.distancia = distancia;
        this.emissaoCalculada = emissaoCalculada;
        this.dataRegistro = dataRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoAviao() {
        return tipoAviao;
    }

    public void setTipoAviao(String tipoAviao) {
        this.tipoAviao = tipoAviao;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getEmissaoCalculada() {
        return emissaoCalculada;
    }

    public void setEmissaoCalculada(double emissaoCalculada) {
        this.emissaoCalculada = emissaoCalculada;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", tipoAviao='" + tipoAviao + '\'' +
                ", distancia=" + distancia +
                ", emissaoCalculada=" + emissaoCalculada +
                ", dataRegistro=" + dataRegistro +
                '}';
    }
}
