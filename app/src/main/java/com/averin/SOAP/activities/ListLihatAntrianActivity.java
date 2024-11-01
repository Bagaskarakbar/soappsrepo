package com.averin.SOAP.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.averin.SOAP.adapters.ListAntriLihatAdapter;
import com.averin.SOAP.models.Antrian;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ListLihatAntrianActivity extends AppCompatActivity {

    public static String EXTRA_IDPLTC = "id_pl_tc_poli";

    public RecyclerView rvListAntri;
    public TextView edtSearchBar;
    public ProgressBar LoadingUI;
    public Button btnCari;
    ListAntriLihatAdapter antriLihatAdapter;

    ArrayList<Antrian> antrians;

    Utility util = new Utility();
    TransactionData trans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_antrian);
        trans = new TransactionData(getApplicationContext());

        edtSearchBar = findViewById(R.id.edtSearchBar);
        rvListAntri = findViewById(R.id.rvListAntri);
        rvListAntri.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LoadingUI = findViewById(R.id.LoadingUI);
        LoadingUI.setVisibility(View.INVISIBLE);
        btnCari = findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariDataAntri();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListLihatAntrianActivity.this,PilihTipeRegistrasiActivity.class);
        startActivity(intent);
    }

    private void cariDataAntri() {
        String url = trans.getRSurl() + "get_data_antrian_lihat.php";
        String noTelp = edtSearchBar.getText().toString();

        if (TextUtils.isEmpty(noTelp)) {
            edtSearchBar.setError("Nomor Telp Tidak Boleh Kosong");
        } else {
            JSONObject jsonparams = new JSONObject();
            try {
                jsonparams.put("no_hp", noTelp);
                jsonparams.put("hari",util.getDayOfWeek());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest getAntriRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int Response = 0;
                    try {
                        Response = response.getInt("Response");
                        if (Response == 0) {
                            Toast.makeText(getApplicationContext(), "No. telp tidak ditemukan", Toast.LENGTH_SHORT).show();
                        } else if (Response == 1) {
                            String NamaPasien = response.getString("nama_pasien");
                            Toast.makeText(getApplicationContext(), "Terdaftar A/N\n" + NamaPasien, Toast.LENGTH_SHORT).show();
                            antrians = new ArrayList<>();
                            JSONArray antrianArray = response.getJSONArray("data_antrian");
                            for (int i = 0; i < antrianArray.length(); i++) {
                                JSONObject antrianJson = antrianArray.getJSONObject(i);
                                int id_pl_tc_poli = antrianJson.getInt("id_pl_tc_poli");
                                String NamaDr = antrianJson.getString("nama_pegawai");
                                String NamaPoli = antrianJson.getString("nama_bagian");
                                int NoAntri = antrianJson.getInt("no_antrian");
                                String JamDaftar = antrianJson.getString("tgl_jam_poli");
                                int waktu_periksa = antrianJson.getInt("waktu_periksa");
                                Date jamMulai = util.ParseHour(antrianJson.getString("jam_mulai"));

                                int j = 1;
                                while (j < NoAntri) {
                                    jamMulai = util.addMinutesToHourFormat(jamMulai, waktu_periksa);
                                    j++;
                                }
                                Date jamSelesai = util.addMinutesToHourFormat(jamMulai, waktu_periksa);

                                Antrian antrian = new Antrian(id_pl_tc_poli, util.ParseDate(JamDaftar), NamaDr, NamaPoli, NoAntri, jamMulai, jamSelesai);
                                antrians.add(antrian);
                            }
                            antriLihatAdapter = new ListAntriLihatAdapter(getApplicationContext(), antrians);
                            rvListAntri.setAdapter(antriLihatAdapter);
                            rvListAntri.addOnItemTouchListener(new ListAntriLihatAdapter.RecyclerTouchListener(getApplicationContext(), rvListAntri, new ListAntriLihatAdapter.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Intent intent = new Intent(ListLihatAntrianActivity.this, AntrianDetActivity.class);
                                    intent.putExtra(EXTRA_IDPLTC, String.valueOf(antrians.get(position).getIdPlTc()));
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongCLick(View view, int position) {
                                    Intent intent = new Intent(ListLihatAntrianActivity.this, AntrianDetActivity.class);
                                    startActivity(intent);
                                }
                            }));
                        }
                    } catch (JSONException e) {
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
                                        cariDataAntri();
                                    }
                                }).show();

                    } else if (error instanceof TimeoutError) {
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("No Internet Connection")
                                .setMessage("It looks like your're having a problem with your internet connection and try again")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cariDataAntri();
                                    }
                                }).show();
                    } else if (error instanceof NoConnectionError) {
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Failed to connect to Server")
                                .setMessage("Check if you have Internet Access")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cariDataAntri();
                                    }
                                }).show();
                    }
                    Log.e("SRD", "Error: " + error.getMessage());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(getAntriRequest);
        }
    }
}
