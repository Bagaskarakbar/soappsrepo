package com.averin.SOAP.models;

import java.io.Serializable;

public class RumahSakit implements Serializable {
    int idRs, bpjs;
    String namaRs, fotoRs, alamatRs, spesialisasiRs, urlRs;

    public RumahSakit(int idRs, int bpjs, String namaRs, String fotoRs, String alamatRs, String spesialisasiRs, String urlRs) {
        this.idRs = idRs;
        this.bpjs = bpjs;
        this.namaRs = namaRs;
        this.fotoRs = fotoRs;
        this.alamatRs = alamatRs;
        this.spesialisasiRs = spesialisasiRs;
        this.urlRs = urlRs;
    }

    public int getIdRs() {
        return idRs;
    }

    public void setIdRs(int idRs) {
        this.idRs = idRs;
    }

    public String getNamaRs() {
        return namaRs;
    }

    public void setNamaRs(String namaRs) {
        this.namaRs = namaRs;
    }

    public String getFotoRs() {
        return fotoRs;
    }

    public void setFotoRs(String fotoRs) {
        this.fotoRs = fotoRs;
    }

    public String getAlamatRs() {
        return alamatRs;
    }

    public void setAlamatRs(String alamatRs) {
        this.alamatRs = alamatRs;
    }

    public int getBpjs() {
        return bpjs;
    }

    public void setBpjs(int bpjs) {
        this.bpjs = bpjs;
    }

    public String getSpesialisasiRs() {
        return spesialisasiRs;
    }

    public void setSpesialisasiRs(String spesialisasiRs) {
        this.spesialisasiRs = spesialisasiRs;
    }

    public String getUrlRs() {
        return urlRs;
    }

    public void setUrlRs(String urlRs) {
        this.urlRs = urlRs;
    }
}
