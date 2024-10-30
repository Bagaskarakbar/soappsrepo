package com.averin.SOAP.utilities.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.averin.SOAP.activities.ListDataAntrianActivity;
import com.averin.SOAP.activities.PasienBaruActivity;
import com.averin.SOAP.utilities.TransactionData;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogPasienLama extends Dialog implements View.OnClickListener {

    int trial = 0;

    public Dialog dialog;
    public Button btnCancel, btnOk, btnClose;
    public EditText edtNoTelp;
    public Activity activity;
    public TextView txTitle, txGagalLogin;

    TransactionData trans;

    public DialogPasienLama(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_pasien_lama);

        trans = new TransactionData(activity);

        edtNoTelp = findViewById(R.id.edtNoTelp);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);
        btnClose = findViewById(R.id.btnClose);
        txGagalLogin = findViewById(R.id.txGagalLogin);
        txTitle = findViewById(R.id.txTitle);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOk:
                cekUser();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnClose:
                dismiss();
                break;
        }
    }

    private void cekUser() {
        String url = trans.getRSurl() + "cek_data_pasien_lama.php";

        String noTelp = edtNoTelp.getText().toString();

        if (TextUtils.isEmpty(noTelp)) {
            edtNoTelp.setError("Nomor Telp Tidak Boleh Kosong");
        } else {
            JSONObject jsonparams = new JSONObject();
            try {
                jsonparams.put("no_hp", noTelp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest cekUserRequest = new JsonObjectRequest(Request.Method.POST, url, jsonparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int Response = 0;
                    try {
                        Response = response.getInt("Response");
                        if (Response == 0) {
                            trial++;
                            Toast.makeText(activity.getApplicationContext(), String.valueOf(trial) + "x gagal login", Toast.LENGTH_SHORT).show();
                            if (trial == 3) {
                                Log.e("error", "Web Server response with 0");
                                txGagalLogin.setVisibility(View.VISIBLE);
                                edtNoTelp.setVisibility(View.INVISIBLE);
                                edtNoTelp.setEnabled(false);
                                txTitle.setText("Gagal Login");
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(activity.getApplicationContext(), PasienBaruActivity.class);
                                        activity.startActivity(intent);
                                    }
                                });
                            }
                        } else if (Response == 1) {
                            int noMr = response.getInt("no_mr");
                            String tglLahir = response.getString("tgl_lhr");
                            String namaPasien = response.getString("nama_pasien");
                            trans.setDataPasien(namaPasien, tglLahir, noMr, 0);
                            Toast.makeText(activity.getApplicationContext(), "user ditemukan\n A/N " + namaPasien, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity.getApplicationContext(), ListDataAntrianActivity.class);
                            activity.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        new AlertDialog.Builder(activity.getApplicationContext())
                                .setTitle("No Internet Connection")
                                .setMessage("Please connect to the Internet")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cekUser();
                                    }
                                }).show();

                    } else if (error instanceof TimeoutError) {
                        new AlertDialog.Builder(activity.getApplicationContext())
                                .setTitle("No Internet Connection")
                                .setMessage("It looks like your're having a problem with your internet connection and try again")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cekUser();
                                    }
                                }).show();
                    } else if (error instanceof NoConnectionError) {
                        new AlertDialog.Builder(activity.getApplicationContext())
                                .setTitle("Failed to connect to Server")
                                .setMessage("Check if you have Internet Access")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        cekUser();
                                    }
                                }).show();
                    }
                    Log.e("SRD", "Error: " + error.getMessage());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
            queue.add(cekUserRequest);
        }

    }
}
