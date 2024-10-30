package com.averin.SOAP.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.averin.SOAP.R;
import com.averin.SOAP.adapters.ListRsAdapter;
import com.averin.SOAP.models.Poliklinik;
import com.averin.SOAP.models.RumahSakit;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.ArrayList;

public class ListRSActivity extends AppCompatActivity {

    EditText edtSearchBar;
    RecyclerView rvListRs;
    ArrayList<RumahSakit> rumahSakits;
    ProgressBar progressBar;

    Utility utils = new Utility();
    TransactionData trans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rs);
        trans = new TransactionData(getApplicationContext());

        edtSearchBar = findViewById(R.id.edtSearchBar);
        rvListRs = findViewById(R.id.rvListRs);
        rvListRs.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        progressBar = findViewById(R.id.LoadingUI);

        getDataRs();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListRSActivity.this,PilihTipeRegistrasiActivity.class);
        startActivity(intent);
    }

    private void getDataRs() {
        String url = Utility.baseUrl + "/get_data_klinik.php";
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest getlistrs = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("error", "Web Server response with 0");
                        Toast.makeText(getBaseContext(), "data tidak ditemukan", Toast.LENGTH_LONG).show();
                    } else if (Response == 1) {
                        rumahSakits = new ArrayList<>();
                        JSONArray JSONArrayRS = response.getJSONArray("datars");
                        for (int i = 0; i < JSONArrayRS.length(); i++) {
                            JSONObject RSObject = JSONArrayRS.getJSONObject(i);
                            int idRS = RSObject.getInt("id_mt_rs");
                            int bpjs = RSObject.getInt("is_bpjs");
                            String NamaRS = RSObject.optString("nama_rs");
                            String AlamatRS = RSObject.optString("alamat_rs");
                            String fotoRs = RSObject.optString("foto_rs");
                            String spesialisasi_rs = RSObject.optString("spesialisasi_rs");
                            String url_rs = RSObject.optString("url_rs");
                            RumahSakit rumahSakit = new RumahSakit(idRS, bpjs, NamaRS, fotoRs, AlamatRS, spesialisasi_rs, url_rs);
                            rumahSakits.add(rumahSakit);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                    ListRsAdapter rsAdapter = new ListRsAdapter(getBaseContext(), rumahSakits);
                    rvListRs.setAdapter(rsAdapter);

                    rvListRs.addOnItemTouchListener(new ListRsAdapter.RecyclerTouchListener(getApplicationContext(), rvListRs, new ListRsAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            String BaseUrlRs = rumahSakits.get(position).getUrlRs();
                            trans.setDataRS(rumahSakits.get(position).getIdRs(), BaseUrlRs.replace("_ws/", ""), rumahSakits.get(position).getUrlRs(), BaseUrlRs.replace("_ws/", "_images/"));
                            if (trans.getTipeReg() == 1){
                                Intent intent = new Intent(getApplicationContext(), PilihPoliActivity.class);
                                startActivity(intent);
                            } else if (trans.getTipeReg() == 2){
                                Intent intent = new Intent(getApplicationContext(), ListLihatAntrianActivity.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onLongCLick(View view, int position) {
                            String BaseUrlRs = rumahSakits.get(position).getUrlRs();
                            trans.setDataRS(rumahSakits.get(position).getIdRs(), BaseUrlRs.replace("_ws/", ""), rumahSakits.get(position).getUrlRs(), BaseUrlRs.replace("_ws/", "_images/"));
                            if (trans.getTipeReg() == 1){
                                Intent intent = new Intent(getApplicationContext(), PilihPoliActivity.class);
                                startActivity(intent);
                            } else if (trans.getTipeReg() == 2){
                                Intent intent = new Intent(getApplicationContext(), ListLihatAntrianActivity.class);
                                startActivity(intent);
                            }
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
                    progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListRSActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataRs();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListRSActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataRs();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    progressBar.setVisibility(View.GONE);
                    new AlertDialog.Builder(ListRSActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getDataRs();
                                }
                            }).show();
                }
                progressBar.setVisibility(View.GONE);
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getlistrs);
    }
}
