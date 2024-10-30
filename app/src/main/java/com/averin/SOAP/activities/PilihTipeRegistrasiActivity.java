package com.averin.SOAP.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.averin.SOAP.R;
import com.averin.SOAP.utilities.dialogs.DialogTipeRegistrasi;

public class PilihTipeRegistrasiActivity extends AppCompatActivity {
    Button btnMulaiReg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_tipe_registrasi);
        btnMulaiReg = findViewById(R.id.btnMulaiReg);
        btnMulaiReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTipeRegistrasi dtr = new DialogTipeRegistrasi(PilihTipeRegistrasiActivity.this);
                dtr.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
