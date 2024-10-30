package com.averin.SOAP.models;

public class Poliklinik {
    private String idPoli,namaPoli, fotoPoli;

    public Poliklinik(String idPoli, String namaPoli, String fotoPoli) {
        this.idPoli = idPoli;
        this.namaPoli = namaPoli;
        this.fotoPoli = fotoPoli;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    public String getNamaPoli() {
        return namaPoli;
    }

    public void setNamaPoli(String namaPoli) {
        this.namaPoli = namaPoli;
    }

    public String getFotoPoli() {
        return fotoPoli;
    }

    public void setFotoPoli(String fotoPoli) {
        this.fotoPoli = fotoPoli;
    }
}
