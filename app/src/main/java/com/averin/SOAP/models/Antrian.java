package com.averin.SOAP.models;

import java.util.Date;

public class Antrian {

    private Date JamMulai, JamSelesai, TglDaftar;
    private Boolean StatusEntri, Passed;
    private String NamaDr, NamaPoli;
    private int NoAntri, IdPlTc;

    public Antrian(Date jamMulai, Date jamSelesai, Boolean statusEntri, Boolean passed) {
        JamMulai = jamMulai;
        JamSelesai = jamSelesai;
        StatusEntri = statusEntri;
        Passed = passed;
    }

    public Antrian(int idPlTc, Date tglDaftar, String namaDr, String namaPoli, int noAntri, Date jamMulai, Date jamSelesai) {
        TglDaftar = tglDaftar;
        NamaDr = namaDr;
        NamaPoli = namaPoli;
        NoAntri = noAntri;
        IdPlTc = idPlTc;
        JamMulai = jamMulai;
        JamSelesai = jamSelesai;
    }

    public int getIdPlTc() {
        return IdPlTc;
    }

    public void setIdPlTc(int idPlTc) {
        this.IdPlTc = idPlTc;
    }

    public Boolean getPassed() {
        return Passed;
    }

    public void setPassed(Boolean passed) {
        Passed = passed;
    }

    public Date getJamMulai() {
        return JamMulai;
    }

    public void setJamMulai(Date jamMulai) {
        JamMulai = jamMulai;
    }

    public Date getJamSelesai() {
        return JamSelesai;
    }

    public void setJamSelesai(Date jamSelesai) {
        JamSelesai = jamSelesai;
    }

    public Boolean getStatusEntri() {
        return StatusEntri;
    }

    public void setStatusEntri(Boolean statusEntri) {
        StatusEntri = statusEntri;
    }

    public String getNamaDr() {
        return NamaDr;
    }

    public void setNamaDr(String namaDr) {
        NamaDr = namaDr;
    }

    public String getNamaPoli() {
        return NamaPoli;
    }

    public void setNamaPoli(String namaPoli) {
        NamaPoli = namaPoli;
    }

    public int getNoAntri() {
        return NoAntri;
    }

    public void setNoAntri(int noAntri) {
        NoAntri = noAntri;
    }


    public Date getTglDaftar() {
        return TglDaftar;
    }

    public void setTglDaftar(Date tglDaftar) {
        TglDaftar = tglDaftar;
    }
}
