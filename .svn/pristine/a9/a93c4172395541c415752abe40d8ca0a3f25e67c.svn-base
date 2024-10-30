package com.averin.SOAP.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.averin.SOAP.adapters.ListAntrianAdapter;
import com.averin.SOAP.models.Antrian;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class ListDataAntrianActivity extends AppCompatActivity {

    int countAntrian = 0;

    TextView txNamaDokter, txTanggal, txJamPraktek, txSisaAntri;
    RecyclerView rvListAntri;
    ImageView imgDr;
    ArrayList<Antrian> antrians;
    ListAntrianAdapter listAntrianAdapter;
    ProgressBar LoadingUI;

    TransactionData trans;
    Utility util;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_antrian);
        setTitle("Buat Antrian");

        trans = new TransactionData(getApplicationContext());
        util = new Utility();
        antrians = new ArrayList<>();

        txNamaDokter = findViewById(R.id.txNamaDokter);
        txTanggal = findViewById(R.id.txTanggal);
        txJamPraktek = findViewById(R.id.txJamPraktek);
        txSisaAntri = findViewById(R.id.txSisaAntri);
        imgDr = findViewById(R.id.imgDr);
        LoadingUI = findViewById(R.id.LoadingUI);
        LoadingUI.setVisibility(View.INVISIBLE);
        rvListAntri = findViewById(R.id.rvListAntri);
        rvListAntri.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listAntrianAdapter = new ListAntrianAdapter(getApplicationContext(), antrians);

        Glide.with(getApplicationContext()).load(trans.getRSBaseUrl() + trans.getFotoDokter()).circleCrop().into(imgDr);
        txTanggal.setText(util.getDate());
        txNamaDokter.setText(trans.getNamaDokter());

        getdataantri();
    }

    private void getdataantri() {
        String url = trans.getRSurl() + "get_data_antrian.php";

        rvListAntri.setVisibility(View.INVISIBLE);
        LoadingUI.setVisibility(View.VISIBLE);

        String kodeDokter = trans.getDokterId();
        String poliId = trans.getPoliId();

        JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("hari", util.getDayOfWeek());
            jsonparams.put("kode_bagian", poliId);
            jsonparams.put("kode_dokter", kodeDokter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("params", jsonparams.toString());

        JsonObjectRequest AntriRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        Log.e("error", "Web Server response with 0");
                        Toast.makeText(getBaseContext(), "data tidak ditemukan", Toast.LENGTH_LONG).show();
                    } else if (Response == 1) {
                        Log.e("resp", "ja : " + response.getString("jam_akhir") + "\n" + "jm : " + response.getString("jam_mulai") + "\n" + String.valueOf(response.getInt("waktu_periksa")));

                        Date jamAkhir = util.ParseHour(response.getString("jam_akhir"));
                        Date jamMulai = util.ParseHour(response.getString("jam_mulai"));
                        int waktuPeriksa = response.getInt("waktu_periksa");

                        txJamPraktek.setText(util.FormatHour(util.ParseHour(response.getString("jam_mulai"))) + " - " + util.FormatHour(util.ParseHour(response.getString("jam_akhir"))));

                        int index = 1;

                        cekDataAntri(index, jamMulai, jamAkhir, waktuPeriksa);
                    }
                    rvListAntri.setAdapter(listAntrianAdapter);
                    rvListAntri.addOnItemTouchListener(new ListAntrianAdapter.RecyclerTouchListener(getApplicationContext(), rvListAntri, new ListAntrianAdapter.ClickListener() {
                        @Override
                        public void onClick(View view, final int position) {
                            boolean statentri = antrians.get(position).getStatusEntri();
                            boolean statpass = antrians.get(position).getPassed();
                            if (!statentri && !statpass) {
                                new AlertDialog.Builder(ListDataAntrianActivity.this)
                                        .setTitle("Konfirmasi Pembuatan Antrian")
                                        .setMessage("Apakah anda yakin dengan pilihan anda?")
                                        .setCancelable(true)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                int tipePasien = trans.getPasienBaru();
                                                if (tipePasien == 1) {
                                                    sendDataRegistrasiBaru(position);
                                                } else if (tipePasien == 0) {
                                                    sendDataRegistrasiLama(position);
                                                }
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onLongCLick(View view, final int position) {
                            boolean statentri = antrians.get(position).getStatusEntri();
                            boolean statpass = antrians.get(position).getPassed();
                            if (!statentri || !statpass) {
                                new AlertDialog.Builder(ListDataAntrianActivity.this)
                                        .setTitle("Konfirmasi Pembuatan Antrian")
                                        .setMessage("Apakah anda yakin dengan pilihan anda?")
                                        .setCancelable(true)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                int tipePasien = trans.getPasienBaru();
                                                if (tipePasien == 1) {
                                                    sendDataRegistrasiBaru(position);
                                                } else if (tipePasien == 0) {
                                                    sendDataRegistrasiLama(position);
                                                }
                                            }
                                        }).show();
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
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();
                }
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(AntriRequest);
    }

    private void cekDataAntri(final int index, final Date jamMulai, final Date jamAkhir, final int waktuPeriksa) {
        String url = trans.getRSurl() + "cek_data_antri.php";

        Boolean passed = false;
        final Date jamMulaiIndex = jamMulai;
        if (util.IsTimeAlreadyPassed(jamMulaiIndex)) {
            passed = true;
        } else {
            passed = false;
        }
        final Date jamN = util.addMinutesToHourFormat(jamMulaiIndex, waktuPeriksa);

        final JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("no_antrian", index);
            jsonparams.put("kode_bagian", trans.getPoliId());
            jsonparams.put("kode_dokter", trans.getDokterId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Boolean finalPassed = passed;
        JsonObjectRequest CekDataAntriRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean statusEntri = true;
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        Log.e("error", "Web Server response with 0");
                        Antrian antrian = new Antrian(jamMulaiIndex, jamN, false, finalPassed);
                        antrians.add(antrian);
                        statusEntri = false;
                    } else if (Response == 1) {
                        Antrian antrian = new Antrian(jamMulaiIndex, jamN, true, finalPassed);
                        antrians.add(antrian);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!statusEntri && !finalPassed){
                    countAntrian++;
                    txSisaAntri.setText(String.valueOf(countAntrian));
                }
                listAntrianAdapter.notifyDataSetChanged();
                if (util.IsHourBefore(jamN, jamAkhir)) {
                    cekDataAntri(index + 1, jamN, jamAkhir, waktuPeriksa);
                } else {
                    LoadingUI.setVisibility(View.INVISIBLE);
                    rvListAntri.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();

                } else if (error instanceof TimeoutError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getdataantri();
                                }
                            }).show();
                }
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(CekDataAntriRequest);
    }

    private void sendDataRegistrasiBaru(final int no_antrian) {
        String url = trans.getRSurl() + "post_data_antrian_baru.php";

        JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("nama_pasien", trans.getNamaPasien());
            jsonparams.put("no_hp", trans.getNoHp());
            jsonparams.put("tgl_lahir", trans.getTanggalLahir());
            jsonparams.put("jenis_kelamin", trans.getJenisKelamin());
            jsonparams.put("gol_darah", trans.getGolonganDarah());
            jsonparams.put("kode_dokter", trans.getDokterId());
            jsonparams.put("kode_bagian", trans.getPoliId());
            jsonparams.put("no_antrian", no_antrian + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest registrasiRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        Log.e("error", "Web Server response with 0");
                        new AlertDialog.Builder(ListDataAntrianActivity.this)
                                .setTitle("Pembuatan Antrian Gagal")
                                .setMessage("Coba Lagi?")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendDataRegistrasiBaru(no_antrian);
                                    }
                                }).show();
                    } else if (Response == 1) {
                        new AlertDialog.Builder(ListDataAntrianActivity.this)
                                .setMessage("Berhasil buat antrian! \nAtas nama " +trans.getNamaPasien()+"\nCek data antrian di menu 'Lihat Antrian'")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ListDataAntrianActivity.this, PilihTipeRegistrasiActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiBaru(no_antrian);
                                }
                            }).show();
                } else if (error instanceof TimeoutError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiBaru(no_antrian);
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiBaru(no_antrian);
                                }
                            }).show();
                }
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(registrasiRequest);
    }

    private void sendDataRegistrasiLama(final int no_antrian) {
        String url = trans.getRSurl() + "post_data_antrian_lama.php";

        JSONObject jsonparams = new JSONObject();
        try {
            jsonparams.put("nama_pasien", trans.getNamaPasien());
            jsonparams.put("no_mr", trans.getNoMr());
            jsonparams.put("tgl_lahir", trans.getTanggalLahir());
            jsonparams.put("kode_dokter", trans.getDokterId());
            jsonparams.put("kode_bagian", trans.getPoliId());
            jsonparams.put("no_antrian", no_antrian + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest registrasiRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int Response = 0;
                try {
                    Response = response.getInt("Response");
                    if (Response == 0) {
                        Log.e("error", "Web Server response with 0");
                        new AlertDialog.Builder(ListDataAntrianActivity.this)
                                .setTitle("Pembuatan Antrian Gagal")
                                .setMessage("Coba Lagi?")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendDataRegistrasiBaru(no_antrian + 1);
                                    }
                                }).show();
                    } else if (Response == 1) {
                        new AlertDialog.Builder(ListDataAntrianActivity.this)
                                        .setMessage("Berhasil buat antrian! \nAtas nama " +trans.getNamaPasien()+"\nCek data antrian di menu 'Lihat Antrian'")
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ListDataAntrianActivity.this, PilihTipeRegistrasiActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("Please connect to the Internet")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiLama(no_antrian);
                                }
                            }).show();
                } else if (error instanceof TimeoutError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your're having a problem with your internet connection and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiLama(no_antrian);
                                }
                            }).show();
                } else if (error instanceof NoConnectionError) {
                    new AlertDialog.Builder(ListDataAntrianActivity.this)
                            .setTitle("Failed to connect to Server")
                            .setMessage("Check if you have Internet Access")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDataRegistrasiLama(no_antrian);
                                }
                            }).show();
                }
                Log.e("SRD", "Error: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(registrasiRequest);
    }

    public interface VolleyCallback {
        void onSuccessResponse();
    }
}
