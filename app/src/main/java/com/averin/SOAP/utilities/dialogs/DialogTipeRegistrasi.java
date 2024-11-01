package com.averin.SOAP.utilities.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.averin.SOAP.R;
import com.averin.SOAP.activities.ListDataAntrianActivity;
import com.averin.SOAP.activities.ListLihatAntrianActivity;
import com.averin.SOAP.activities.ListRSActivity;
import com.averin.SOAP.activities.PilihPoliActivity;
import com.averin.SOAP.utilities.TransactionData;

public class DialogTipeRegistrasi extends Dialog implements Button.OnClickListener {

    public Dialog dialog;
    public Button btnUmum, btnBPJS, btnClose, btnAntrian;
    public Activity activity;

    TransactionData trans;

    public DialogTipeRegistrasi(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_tipe_registrasi);
        trans = new TransactionData(activity);
        btnBPJS = findViewById(R.id.btnBPJS);
        btnUmum = findViewById(R.id.btnUmum);
        btnAntrian = findViewById(R.id.btnAntrian);
        btnClose = findViewById(R.id.btnClose);

        btnBPJS.setOnClickListener(this);
        btnUmum.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnAntrian.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        trans.clearTransaction();
        String BaseUrlRs = "https://mmc.sirs.co.id/_ws/";
        trans.setDataRS(1, BaseUrlRs.replace("_ws/", ""), BaseUrlRs, BaseUrlRs.replace("_ws/", "_images/"));
        switch (view.getId()) {
            case R.id.btnUmum:
                intent = new Intent(activity.getApplicationContext(), PilihPoliActivity.class);
                activity.startActivity(intent);
                //intent = new Intent(activity, ListRSActivity.class);
                //trans.setTipeReg(1);
                //activity.startActivity(intent);
                break;
            case R.id.btnBPJS:
                intent = new Intent(activity.getApplicationContext(), PilihPoliActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.btnAntrian:
                intent = new Intent(activity.getApplicationContext(), ListLihatAntrianActivity.class);
                activity.startActivity(intent);
                //intent = new Intent(activity.getApplicationContext(), ListRSActivity.class);
                //trans.setTipeReg(2);
                //activity.startActivity(intent);
                break;
            case R.id.btnClose:
                dismiss();
                break;
        }
        dismiss();
    }
}
