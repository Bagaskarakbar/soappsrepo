package com.averin.SOAP.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.averin.SOAP.adapters.ListDokterAdapter;
import com.averin.SOAP.adapters.ListRsAdapter;
import com.averin.SOAP.models.Dokter;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;
import com.averin.SOAP.utilities.dialogs.DialogTipePasien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ListDokterActivity extends AppCompatActivity {

    ArrayList<Dokter> dokters;
    ImageView ico_poli;
    TextView txNamaPoli;
    RecyclerView rvDokterList;
    ProgressBar LoadingUI;

    Utility util = new Utility();
    TransactionData trans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dokter);
        trans = new TransactionData(getApplicationContext());
        txNamaPoli = findViewById(R.id.txNamaPoli);
        ico_poli = findViewById(R.id.ico_poli);
        rvDokterList = findViewById(R.id.rvDokterList);
        rvDokterList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        LoadingUI = findViewById(R.id.LoadingUI);

        getDataDokter();
    }

    private void getDataDokter() {
        String url = trans.getRSurl() + "get_data_dokter.php";
        LoadingUI.setVisibility(View.VISIBLE);

        JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("act","3");
            jsonparams.put("kode_bagian", trans.getPoliId());
            jsonparams.put("hari",util.getDayOfWeek());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest dataDrRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        LoadingUI.setVisibility(View.GONE);
                        Log.e("error", "Web Server response with 0");
                        Toast.makeText(getBaseContext(), "data tidak ditemukan", Toast.LENGTH_LONG).show();
                    } else if (Response == 1) {
                        dokters = new ArrayList<>();
                        JSONArray DokterArray = response.getJSONArray("jadwal_dokter");
                        for (int i = 0; i < DokterArray.length(); i++) {
                            JSONObject DokterObject = DokterArray.getJSONObject(i);
                            int kode_dokter = DokterObject.getInt("kode_dokter");
                            String nama_dokter = DokterObject.getString("nama_pegawai");
                            String foto_dokter = DokterObject.optString("url_foto_karyawan");
                            foto_dokter = foto_dokter.replace("../","");
                            Date jam_mulai = util.ParseDate(DokterObject.getString("jam_mulai"));
                            Date jam_selesai = util.ParseDate(DokterObject.getString("jam_akhir"));
                            boolean passed = util.IsTimeAlreadyPassed(jam_selesai);
                            if (!passed){
                                Dokter dokter = new Dokter(kode_dokter, nama_dokter, foto_dokter, jam_mulai, jam_selesai);
                                dokters.add(dokter);
                            }
                        }
                        LoadingUI.setVisibility(View.GONE);
                    }
                    ListDokterAdapter listDokterAdapter = new ListDokterAdapter(getApplicationContext(), dokters);
                    rvDokterList.setAdapter(listDokterAdapter);

                    rvDokterList.addOnItemTouchListener(new ListRsAdapter.RecyclerTouchListener(getApplicationContext(), rvDokterList, new ListRsAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            //Toast.makeText(getApplicationContext(), "dokter " + dokters.get(position).getNamaDokter() + "di klik", Toast.LENGTH_SHORT).show();
                            trans.setDataDokter(String.valueOf(dokters.get(position).getIdDokter()),dokters.get(position).getFotoDokter(),dokters.get(position).getNamaDokter());
                            DialogTipePasien dtp = new DialogTipePasien(ListDokterActivity.this);
                            dtp.show();
                        }

                        @Override
                        public void onLongCLick(View view, int position) {
                            //Toast.makeText(getApplicationContext(), "dokter " + dokters.get(position).getNamaDokter() + "di klik", Toast.LENGTH_SHORT).show();
                            trans.setDataDokter(String.valueOf(dokters.get(position).getIdDokter()),dokters.get(position).getFotoDokter(),dokters.get(position).getNamaDokter());
                            DialogTipePasien dtp = new DialogTipePasien(ListDokterActivity.this);
                            dtp.show();
                        }
                    }));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    LoadingUI.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListDokterActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataDokter();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    LoadingUI.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListDokterActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataDokter();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    LoadingUI.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListDokterActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataDokter();
                                }
                            }).show();
                }
                LoadingUI.setVisibility(View.GONE);
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(dataDrRequest);
    }
}
