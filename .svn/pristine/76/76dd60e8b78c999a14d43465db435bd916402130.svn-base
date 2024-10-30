package com.averin.SOAP.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.averin.SOAP.adapters.ListPoliAdapter;
import com.averin.SOAP.adapters.ListRsAdapter;
import com.averin.SOAP.models.Poliklinik;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilihPoliActivity extends AppCompatActivity {

    RecyclerView rvPoliklinik;
    ProgressBar LoadingUI;
    ArrayList<Poliklinik> polikliniks;

    Utility utils = new Utility();
    TransactionData trans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_poli);
        trans = new TransactionData(getApplicationContext());

        LoadingUI = findViewById(R.id.LoadingUI);
        rvPoliklinik = findViewById(R.id.rvPoliGrid);
        rvPoliklinik.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        getdatapoli();
    }

    private void getdatapoli() {
        String url = trans.getRSurl() + "get_data_bagian.php";
        LoadingUI.setVisibility(View.VISIBLE);

        JsonObjectRequest poliRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
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
                        polikliniks = new ArrayList<>();
                        JSONArray jsonArrayPoli = response.getJSONArray("bagian");
                        for (int i = 0; i < jsonArrayPoli.length(); i++) {
                            JSONObject PoliObject = jsonArrayPoli.getJSONObject(i);
                            String idPoli = PoliObject.getString("kode_bagian");
                            String namaPoli = PoliObject.getString("nama_bagian");
                            String fotoPoli = PoliObject.getString("icon");
                            Poliklinik poliklinik = new Poliklinik(idPoli, namaPoli, fotoPoli);
                            polikliniks.add(poliklinik);
                        }
                        LoadingUI.setVisibility(View.GONE);
                    }
                    ListPoliAdapter poliAdapter = new ListPoliAdapter(getApplicationContext(), polikliniks);
                    rvPoliklinik.setAdapter(poliAdapter);

                    rvPoliklinik.addOnItemTouchListener(new ListRsAdapter.RecyclerTouchListener(getApplicationContext(), rvPoliklinik, new ListRsAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            trans.setPoliId(String.valueOf(polikliniks.get(position).getIdPoli()));
                            Intent intent = new Intent(PilihPoliActivity.this, ListDokterActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongCLick(View view, int position) {
                            trans.setPoliId(polikliniks.get(position).getIdPoli());
                            Intent intent = new Intent(PilihPoliActivity.this, ListDokterActivity.class);
                            startActivity(intent);
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
                    new AlertDialog.Builder(PilihPoliActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdatapoli();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    LoadingUI.setVisibility(View.GONE);
                    new AlertDialog.Builder(PilihPoliActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdatapoli();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    LoadingUI.setVisibility(View.GONE);
                    new AlertDialog.Builder(PilihPoliActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdatapoli();
                                }
                            }).show();
                }
                LoadingUI.setVisibility(View.GONE);
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(poliRequest);
    }
}
