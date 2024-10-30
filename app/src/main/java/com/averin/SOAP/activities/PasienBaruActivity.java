package com.averin.SOAP.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.averin.SOAP.R;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;

import java.util.Calendar;

public class PasienBaruActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txNamaPasien, txNoHp, txTglLahir;
    Spinner spnSex, spnGolDarah;
    Button btnSubmit;

    TransactionData trans;

    String Sex, GolDar;

    Utility utils = new Utility();
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_baru);
        trans = new TransactionData(getApplicationContext());

        txNamaPasien = findViewById(R.id.txNamaPasien);
        txNoHp = findViewById(R.id.txNoHp);
        txTglLahir = findViewById(R.id.txTglLahir);
        TextWatcher DateWatcher = new TextWatcher() {
            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private boolean deletingBackward;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting)
                    return;

                final int selStart = Selection.getSelectionStart(s);
                final int selEnd = Selection.getSelectionEnd(s);
                if (s.length() > 1
                        && count == 1
                        && after == 0
                        && s.charAt(start) == '/'
                        && selStart == selEnd) {
                    deletingHyphen = true;
                    hyphenStart = start;
                    if (selStart == start + 1) {
                        deletingBackward = true;
                    } else {
                        deletingBackward = false;
                    }
                } else {
                    deletingHyphen = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (isFormatting)
                    return;

                isFormatting = true;

                if (deletingHyphen && hyphenStart > 0) {
                    if (deletingBackward) {
                        if (hyphenStart - 1 < text.length()) {
                            text.delete(hyphenStart - 1, hyphenStart);
                        }
                    } else if (hyphenStart < text.length()) {
                        text.delete(hyphenStart, hyphenStart + 1);
                    }
                }
                if (text.length() == 2 || text.length() == 5) {
                    text.append('/');
                }

                isFormatting = false;
            }
        };
        txTglLahir.addTextChangedListener(DateWatcher);

        spnSex = findViewById(R.id.spnSex);
        ArrayAdapter<CharSequence> spnSexAdapter = ArrayAdapter.createFromResource(PasienBaruActivity.this, R.array.jenis_kelamin, R.layout.support_simple_spinner_dropdown_item);
        spnSexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnSex.setAdapter(spnSexAdapter);
        spnSex.setOnItemSelectedListener(this);

        spnGolDarah = findViewById(R.id.spnGolDarah);
        ArrayAdapter<CharSequence> spnGolDarAdapter = ArrayAdapter.createFromResource(PasienBaruActivity.this, R.array.gol_darah, R.layout.support_simple_spinner_dropdown_item);
        spnSexAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnGolDarah.setAdapter(spnGolDarAdapter);
        spnGolDarah.setOnItemSelectedListener(this);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.hideKeyboard(PasienBaruActivity.this);
                boolean error = false;
                String NamaPasien = txNamaPasien.getText().toString();
                String NoHP = txNoHp.getText().toString();
                String tglLahirs = txTglLahir.getText().toString();

                if (TextUtils.isEmpty(NamaPasien)) {
                    txNamaPasien.setError("Nama Tidak Boleh Kosong!");
                    error = true;
                }
                if (TextUtils.isEmpty(NoHP)) {
                    txNoHp.setError("Nomor Hp Tidak Boleh Kosong");
                    error = true;
                }
                if (TextUtils.isEmpty(tglLahirs)) {
                    txTglLahir.setError("Tanggal Lahir Tidak Boleh Kosong");
                    error = true;
                }
                if (Integer.parseInt(tglLahirs.substring(0, 2)) <= 0 || Integer.parseInt(tglLahirs.substring(0, 2)) > 31 || Integer.parseInt(tglLahirs.substring(3, 5)) > 13 || Integer.parseInt(tglLahirs.substring(6, tglLahirs.length())) > calendar.get(Calendar.YEAR)) {
                    txTglLahir.setError("Tanggal Lahir Tidak Valid");
                    error = true;
                }
                if (!error) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(PasienBaruActivity.this).create();
                    alertDialog.setMessage("Apakah anda yakin data diri anda sudah benar?");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String NamaPasien = txNamaPasien.getText().toString();
                            String NoHP = txNoHp.getText().toString();
                            String tglLahir = txTglLahir.getText().toString();
                            trans.setDataPasien(NamaPasien,NoHP,utils.FormatStringDate(tglLahir),Sex,GolDar,1);
                            Intent intent = new Intent(PasienBaruActivity.this, ListDataAntrianActivity.class);
                            startActivity(intent);
                        }
                    });
                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spnSex:
                if (i == 0){
                    Sex = "L";
                } else if (i==1){
                    Sex = "P";
                }
                break;
            case R.id.spnGolDarah:
                GolDar = adapterView.getItemAtPosition(i).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
