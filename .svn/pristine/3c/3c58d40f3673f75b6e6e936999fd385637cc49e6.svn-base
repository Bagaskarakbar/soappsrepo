package com.averin.SOAP.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.averin.SOAP.R;
import com.averin.SOAP.models.Dokter;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class AntrianDetActivity extends AppCompatActivity {

    TransactionData trans;
    Utility util;

    TextView txNamaPasien, txNoMr, txJenisKelamin, txUmur, txAlamat, txNamaDokter, txNamaPoli, txTanggalDaftar, txNoAntri;
    ImageView imgQR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_det);

        trans = new TransactionData(getApplicationContext());
        util = new Utility();

        txNamaPasien = findViewById(R.id.txNamaPasien);
        txNoMr = findViewById(R.id.txNoMr);
        txJenisKelamin = findViewById(R.id.txJenisKelamin);
        txUmur = findViewById(R.id.txUmur);
        txAlamat = findViewById(R.id.txAlamat);
        txNamaDokter = findViewById(R.id.txNamaDokter);
        txNamaPoli = findViewById(R.id.txNamaPoli);
        txTanggalDaftar = findViewById(R.id.txTanggalDaftar);
        txNoAntri = findViewById(R.id.txNoAntri);
        imgQR = findViewById(R.id.imgQR);

        getDataPasien();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AntrianDetActivity.this,ListLihatAntrianActivity.class);
        startActivity(intent);
    }

    private void getDataPasien() {
        String url = trans.getRSurl() + "get_data_antrian_detail.php";

        JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("id_pl_tc", Integer.parseInt(getIntent().getStringExtra(ListLihatAntrianActivity.EXTRA_IDPLTC)));
            jsonparams.put("hari", util.getDayOfWeek());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest AntrianDetRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0){
                        Toast.makeText(getApplicationContext(), "Data Antrian Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    } else if (Response == 1){
                        txNamaPasien.setText(response.getString("nama_pasien"));
                        String no_mr = response.getString("no_mr");
                        txNoMr.setText(no_mr);
                        String jenKelamin = response.getString("jen_kelamin");
                        if (jenKelamin.equals("L")){
                            txJenisKelamin.setText("Laki - Laki");
                        } else if (jenKelamin.equals("P")){
                            txJenisKelamin.setText("Perempuan");
                        }
                        String uTahun = response.getString("umur_tahun");
                        String uBulan = response.getString("umur_bulan");
                        String uHari = response.getString("umur_hari");
                        txUmur.setText(uTahun + " Tahun " + uBulan + " Bulan " + uHari + " Hari");
                        String Alamat = response.getString("almt_ttp_pasien");
                        if (Alamat.equals("null")){
                            txAlamat.setText("-");
                        } else {
                            txAlamat.setText(Alamat);
                        }
                        txNamaDokter.setText(response.getString("nama_pegawai"));
                        txNamaPoli.setText(response.getString("nama_bagian"));
                        int no_antrian = response.getInt("no_antrian");
                        txNoAntri.setText(String.valueOf(no_antrian));
                        int waktu_periksa = response.getInt("waktu_periksa");
                        Date jamMulai = util.ParseHour(response.getString("jam_mulai"));

                        int i = 1;
                        while (i<no_antrian){
                            jamMulai = util.addMinutesToHourFormat(jamMulai,waktu_periksa);
                            i++;
                        }
                        Date jamSelesai = util.addMinutesToHourFormat(jamMulai,waktu_periksa);
                        txTanggalDaftar.setText(util.FormatHour(jamMulai) + " - " + util.FormatHour(jamSelesai));

                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix QrMatrix = multiFormatWriter.encode(no_mr, BarcodeFormat.QR_CODE,150,150);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap QRcode = barcodeEncoder.createBitmap(QrMatrix);
                        imgQR.setImageBitmap(QRcode);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataPasien();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataPasien();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataPasien();
                                }
                            }).show();
                }
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(AntrianDetRequest);
    }
}
