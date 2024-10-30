package com.averin.SOAP.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class TransactionData {

    public SharedPreferences sharedprefs;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TransDataSOAP";
    //data global
    private static final String KEY_TIPE_REG = "TipeReg";

    //data pasien
    private static final String IS_NEW_PATIENT = "IsNewPatient";
    private static final String KEY_NO_MR = "NoMr";
    private static final String KEY_NAMA_PASIEN = "NamaPasien";
    private static final String KEY_NO_HP = "NoHp";
    private static final String KEY_TANGGAL_LAHIR = "TanggalLahir";
    private static final String KEY_JENIS_KELAMIN = "JenisKelamin";
    private static final String KEY_GOLONGAN_DARAH = "GolonganDarah";
    private static final String KEY_PASIEN_BARU = "PasienBaru";

    //data rs
    private static final String KEY_RS_ID = "rs_id";
    private static final String KEY_URL_BASE_RS = "rs_base_url";
    private static final String KEY_URL_RS = "rs_url";
    private static final String KEY_URL_IMG_RS = "rs_img_url";
    private static final String KEY_POLI_ID = "poli_id";

    //data dr
    private static final String KEY_DOKTER_ID = "dokter_id";
    private static final String KEY_FOTO_DOKTER = "dokter_foto";
    private static final String KEY_NAMA_DOKTER = "dokter_nama";

    public TransactionData(Context context) {
        this.context = context;
        sharedprefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedprefs.edit();
    }

    //simple setters and getters

    public boolean IsNewPatient() {
        return sharedprefs.getBoolean(IS_NEW_PATIENT, false);
    }

    public void SetNewPatient(boolean setNewPatient) {
        editor.putBoolean(IS_NEW_PATIENT, setNewPatient);
    }

    public int getRSId() {
        return sharedprefs.getInt(KEY_RS_ID, 0);
    }

    public void setRSId(int rd_id) {
        editor.putInt(KEY_RS_ID, rd_id);
        editor.commit();
    }

    public String getRSurl() {
        return sharedprefs.getString(KEY_URL_RS, null);
    }

    public void setRSurl(String url_rs) {
        editor.putString(KEY_URL_RS, url_rs);
        editor.commit();
    }

    public String getRSimgUrl() {
        return sharedprefs.getString(KEY_URL_IMG_RS, null);
    }

    public void setRSimgUrl(String url_rs) {
        editor.putString(KEY_URL_IMG_RS, url_rs);
        editor.commit();
    }

    public String getRSBaseUrl() {
        return sharedprefs.getString(KEY_URL_BASE_RS, null);
    }

    public void setRSBaseUrl(String url_rs) {
        editor.putString(KEY_URL_BASE_RS, url_rs);
        editor.commit();
    }

    public String getPoliId() {
        return sharedprefs.getString(KEY_POLI_ID, null);
    }

    public void setPoliId(String poli_id) {
        editor.putString(KEY_POLI_ID, poli_id);
        editor.commit();
    }

    public String getDokterId() {
        return sharedprefs.getString(KEY_DOKTER_ID, null);
    }

    public void setDokterId(String dokterId) {
        editor.putString(KEY_DOKTER_ID, dokterId);
        editor.commit();
    }

    public String getFotoDokter() {
        return sharedprefs.getString(KEY_FOTO_DOKTER, null);
    }

    public void setFotoDokter(String fotoDokter) {
        editor.putString(KEY_FOTO_DOKTER, fotoDokter);
        editor.commit();
    }

    public String getNamaPasien() {
        return sharedprefs.getString(KEY_NAMA_PASIEN, null);
    }

    public void setNamaPasien(String namaPasien) {
        editor.putString(KEY_NAMA_PASIEN, namaPasien);
        editor.commit();
    }

    public String getNoHp() {
        return sharedprefs.getString(KEY_NO_HP, null);
    }

    public void setNoHp(String noHp) {
        editor.putString(KEY_NO_HP, noHp);
        editor.commit();
    }

    public String getTanggalLahir() {
        return sharedprefs.getString(KEY_TANGGAL_LAHIR, null);
    }

    public void setTanggalLahir(String tanggalLahir) {
        editor.putString(KEY_TANGGAL_LAHIR, tanggalLahir);
        editor.commit();
    }

    public String getJenisKelamin() {
        return sharedprefs.getString(KEY_JENIS_KELAMIN, null);
    }

    public void setJenisKelamin(String jenisKelamin) {
        editor.putString(KEY_JENIS_KELAMIN, jenisKelamin);
        editor.commit();
    }

    public String getGolonganDarah() {
        return sharedprefs.getString(KEY_GOLONGAN_DARAH, null);
    }

    public void setGolonganDarah(String golonganDarah) {
        editor.putString(KEY_GOLONGAN_DARAH, golonganDarah);
        editor.commit();
    }

    public int getNoMr() {
        return sharedprefs.getInt(KEY_NO_MR, 0);
    }

    public void setNoMr(int no_mr) {
        editor.putInt(KEY_NO_MR, no_mr);
        editor.commit();
    }

    public int getPasienBaru() {
        return sharedprefs.getInt(KEY_PASIEN_BARU, 0);
    }

    public void setPasienBaru(int pasienBaru) {
        editor.putInt(KEY_PASIEN_BARU, pasienBaru);
        editor.commit();
    }

    public String getNamaDokter() {
        return sharedprefs.getString(KEY_NAMA_DOKTER, null);
    }

    public void setNamaDokter(String namaDokter) {
        editor.putString(KEY_NAMA_DOKTER, namaDokter);
        editor.commit();
    }

    public int getTipeReg() {
        return sharedprefs.getInt(KEY_TIPE_REG, 0);
    }

    public void setTipeReg(int tipeReg) {
        editor.putInt(KEY_TIPE_REG, tipeReg);
        editor.commit();
    }

    //complex setter getters

    public void setDataDokter(String dokterId, String fotoDokter, String namaDokter) {
        setDokterId(dokterId);
        setFotoDokter(fotoDokter);
        setNamaDokter(namaDokter);
    }

    public void setDataRS(int RsID, String RsBaseUrl, String RsWsUrl, String RsImgUrl) {
        setRSId(RsID);
        setRSBaseUrl(RsBaseUrl);
        setRSurl(RsWsUrl);
        setRSimgUrl(RsImgUrl);
    }

    public void setDataPasien(String namaPasien, String noHP, String tanggalLahir, String jenisKelamin, String golonganDarah, int PasienBaru) {
        setNamaPasien(namaPasien);
        setNoHp(noHP);
        setTanggalLahir(tanggalLahir);
        setJenisKelamin(jenisKelamin);
        setGolonganDarah(golonganDarah);
        setPasienBaru(PasienBaru);
    }

    public void setDataPasien(String namaPasien, String tanggalLahir, int NoMr, int PasienBaru) {
        setNamaPasien(namaPasien);
        setNoMr(NoMr);
        setTanggalLahir(tanggalLahir);
        setPasienBaru(PasienBaru);
    }

    public void clearTransaction() {
        editor.clear();
        editor.commit();
        editor.apply();
    }


}
