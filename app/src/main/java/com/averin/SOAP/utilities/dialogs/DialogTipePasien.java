package com.averin.SOAP.utilities.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.averin.SOAP.R;
import com.averin.SOAP.activities.ListDokterActivity;
import com.averin.SOAP.activities.PasienBaruActivity;
import com.averin.SOAP.utilities.TransactionData;

public class DialogTipePasien extends Dialog implements View.OnClickListener {

    public Dialog dialog;
    public Button btnPasienBaru, btnPasienLama, btnClose;
    public Activity activity;

    TransactionData trans;

    public DialogTipePasien(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_tipe_pasien);
        trans = new TransactionData(activity.getApplicationContext());
        btnPasienBaru = findViewById(R.id.btnPasienBaru);
        btnPasienLama = findViewById(R.id.btnPasienLama);
        btnClose = findViewById(R.id.btnClose);

        btnPasienLama.setOnClickListener(this);
        btnPasienBaru.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPasienBaru:
                //Toast.makeText(activity,"user pilih pasien baru",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, PasienBaruActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.btnPasienLama:
                //Toast.makeText(activity,"user pilih pasien lama", Toast.LENGTH_SHORT).show();
                DialogPasienLama dpl = new DialogPasienLama(activity);
                dpl.show();
                dismiss();
                break;
            case R.id.btnClose:
                dismiss();
                break;
        }
        dismiss();
    }
}
